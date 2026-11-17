package DAO;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;


public class SupabaseStorage {

	private static final HttpClient HTTP = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(20))
			.build();

	private SupabaseStorage() {
	}

	public static boolean configurado() {
		return ConfigSupabase.existe();
	}

	private static String base() throws IOException {
		String url = ConfigSupabase.obrigatorio("supabase.url");
		if (url.endsWith("/")) url = url.substring(0, url.length() - 1);
		return url + "/storage/v1/object";
	}

	private static String bucket() throws IOException {
		return ConfigSupabase.obrigatorio("supabase.bucket");
	}

	private static HttpRequest.Builder requisicao(String url) throws IOException {
		String chave = ConfigSupabase.obrigatorio("supabase.key");
		HttpRequest.Builder b = HttpRequest.newBuilder(URI.create(url));
		b.header("apikey", chave);
		if (!chave.startsWith("sb_")) {
			b.header("Authorization", "Bearer " + chave);
		}
		return b;
	}

	public static String tipoConteudo(String nomeArquivo) {
		String nome = nomeArquivo == null ? "" : nomeArquivo.toLowerCase();
		if (nome.endsWith(".mp3")) return "audio/mpeg";
		if (nome.endsWith(".wav")) return "audio/wav";
		if (nome.endsWith(".jpg") || nome.endsWith(".jpeg")) return "image/jpeg";
		if (nome.endsWith(".png")) return "image/png";
		return "application/octet-stream";
	}

	private static String codificar(String caminho) {
		StringBuilder sb = new StringBuilder();
		for (String parte : caminho.split("/")) {
			if (sb.length() > 0) sb.append('/');
			sb.append(URLEncoder.encode(parte, StandardCharsets.UTF_8).replace("+", "%20"));
		}
		return sb.toString();
	}

	private static String urlObjeto(String caminhoNuvem) throws IOException {
		return base() + "/" + bucket() + "/" + codificar(caminhoNuvem);
	}

	private static void checar(int status, String corpo, String acao) throws IOException {
		if (status >= 200 && status < 300) return;

		String detalhe = corpo == null ? "" : corpo.trim();
		if (detalhe.length() > 300) detalhe = detalhe.substring(0, 300) + "...";
		String lower = detalhe.toLowerCase();

		String dica;
		if (status == 413 || lower.contains("payload too large") || lower.contains("exceeded the maximum allowed size")) {
			dica = "O arquivo é maior que o limite permitido (50 MB no plano gratuito). Comprima o arquivo ou aumente o limite do bucket.";
		} else if (status == 401 || lower.contains("invalid jwt") || lower.contains("invalid api key")) {
			dica = "A chave do arquivo supabase.properties foi recusada. Use a publishable key (Settings > API Keys).";
		} else if (status == 403 || lower.contains("row-level security") || lower.contains("unauthorized")) {
			dica = "O Supabase recusou o acesso ao bucket. Rode o script atualizar_postgres.sql (policies) no SQL Editor.";
		} else if (lower.contains("bucket not found")) {
			dica = "Bucket não encontrado. Confira o nome em supabase.bucket e se ele foi criado no painel (Storage).";
		} else if (status == 404 || lower.contains("object not found")) {
			dica = "Arquivo não encontrado no Storage.";
		} else {
			dica = "Erro inesperado do Storage.";
		}
		throw new IOException("Não foi possível " + acao + " (HTTP " + status + "). " + dica
				+ (detalhe.isEmpty() ? "" : "\nResposta: " + detalhe));
	}

	private static <T> HttpResponse<T> enviarRequisicao(HttpRequest req, HttpResponse.BodyHandler<T> tratador)
			throws IOException {
		try {
			return HTTP.send(req, tratador);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException("Operação interrompida.", e);
		} catch (IOException e) {
			throw new IOException("Sem conexão com o Supabase Storage: " + e.getMessage(), e);
		}
	}

	public static void enviar(Path arquivo, String caminhoNuvem) throws IOException {
		enviar(arquivo, caminhoNuvem, "audio/mpeg");
	}

	public static void enviar(Path arquivo, String caminhoNuvem, String tipoConteudo) throws IOException {
		HttpRequest req = requisicao(urlObjeto(caminhoNuvem))
				.timeout(Duration.ofMinutes(5))
				.header("Content-Type", tipoConteudo)
				.header("x-upsert", "false")
				.POST(HttpRequest.BodyPublishers.ofFile(arquivo))
				.build();

		HttpResponse<String> resposta = enviarRequisicao(req, HttpResponse.BodyHandlers.ofString());
		checar(resposta.statusCode(), resposta.body(), "enviar o arquivo para a nuvem");
	}

	public static void baixar(String caminhoNuvem, Path destino) throws IOException {
		Files.createDirectories(destino.getParent());
		Path parcial = destino.resolveSibling(destino.getFileName() + ".part");

		HttpRequest req = requisicao(urlObjeto(caminhoNuvem))
				.timeout(Duration.ofMinutes(5))
				.GET()
				.build();

		try {
			HttpResponse<Path> resposta = enviarRequisicao(req, HttpResponse.BodyHandlers.ofFile(parcial));
			if (resposta.statusCode() < 200 || resposta.statusCode() >= 300) {
				String corpo = Files.isRegularFile(parcial) ? Files.readString(parcial) : "";
				checar(resposta.statusCode(), corpo, "baixar a música da nuvem");
			}
			Files.move(parcial, destino, StandardCopyOption.REPLACE_EXISTING);
		} finally {
			Files.deleteIfExists(parcial);
		}
	}

	public static void apagar(String... caminhosNuvem) throws IOException {
		if (caminhosNuvem == null || caminhosNuvem.length == 0) return;

		StringBuilder json = new StringBuilder("{\"prefixes\":[");
		for (int i = 0; i < caminhosNuvem.length; i++) {
			if (i > 0) json.append(',');
			json.append('"').append(caminhosNuvem[i].replace("\\", "\\\\").replace("\"", "\\\"")).append('"');
		}
		json.append("]}");

		HttpRequest req = requisicao(base() + "/" + bucket())
				.timeout(Duration.ofSeconds(60))
				.header("Content-Type", "application/json")
				.method("DELETE", HttpRequest.BodyPublishers.ofString(json.toString()))
				.build();

		HttpResponse<String> resposta = enviarRequisicao(req, HttpResponse.BodyHandlers.ofString());
		checar(resposta.statusCode(), resposta.body(), "apagar a música da nuvem");
	}
}
