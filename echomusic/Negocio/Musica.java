package Negocio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import DAO.MusicaDAO;
import DAO.SupabaseStorage;

public class Musica {
	private static int proximoId = 1;

	private static final Object TRAVA_DOWNLOAD = new Object();

	private int idMusica;
	private String nome;
	private String duracao;
	private int idPlaylist;
	private String caminho;
	private String caminhoNuvem;

	public Musica() {
	}

	public Musica(String nome) {
		this.idMusica = proximoId++;
		this.nome = nome;
		this.duracao = "--:--";
	}

	public Musica(String nome, String caminho) {
		this(nome);
		this.caminho = caminho;
	}

	public int getIdMusica() {
		return idMusica;
	}
	public void setIdMusica(int idMusica) {
		this.idMusica = idMusica;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public int getIdPlaylist() {
		return idPlaylist;
	}
	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public String getCaminhoNuvem() {
		return caminhoNuvem;
	}
	public void setCaminhoNuvem(String caminhoNuvem) {
		this.caminhoNuvem = caminhoNuvem;
	}

	private static Musica deLinha(ResultSet rs) throws SQLException {
		Musica musica = new Musica();
		musica.setIdMusica(rs.getInt("idMusica"));
		musica.setNome(rs.getString("nome"));
		musica.setDuracao(rs.getString("duracao"));
		musica.setCaminho(rs.getString("caminho"));
		musica.setCaminhoNuvem(rs.getString("caminhoNuvem"));
		musica.setIdPlaylist(rs.getInt("idPlaylist"));
		return musica;
	}

	private static Path raiz() {
		return Paths.get(System.getProperty("user.dir"));
	}

	private static Path pastaMusicas() {
		return raiz().resolve("musicas").normalize();
	}

	private Path arquivoCopiado() {
		if (caminho == null || caminho.isBlank()) return null;
		Path arquivo = raiz().resolve(caminho).normalize();
		return arquivo.startsWith(pastaMusicas()) ? arquivo : null;
	}

	private Path arquivoBaixado() {
		if (caminhoNuvem == null || caminhoNuvem.isBlank()) return null;
		Path arquivo = pastaMusicas().resolve("nuvem").resolve(caminhoNuvem).normalize();
		return arquivo.startsWith(pastaMusicas()) ? arquivo : null;
	}

	public boolean disponivelNesteComputador() {
		Path copia = arquivoCopiado();
		Path baixado = arquivoBaixado();
		return (copia != null && Files.isRegularFile(copia))
				|| (baixado != null && Files.isRegularFile(baixado))
				|| (caminhoNuvem == null && localizarPorNome() != null);
	}


	public Path arquivoLocal() throws IOException {
		Path copia = arquivoCopiado();
		if (copia != null && Files.isRegularFile(copia)) return copia;

		Path baixado = arquivoBaixado();
		if (baixado != null) {
			synchronized (TRAVA_DOWNLOAD) {
				if (!Files.isRegularFile(baixado)) {
					SupabaseStorage.baixar(caminhoNuvem, baixado);
				}
			}
			return baixado;
		}

		Path porNome = localizarPorNome();
		if (porNome != null) return porNome;

		throw new FileNotFoundException("O arquivo da música \"" + nome
				+ "\" não está neste computador e não foi enviado para a nuvem.");
	}

	private Path localizarPorNome() {
		if (nome == null || nome.isBlank()) return null;
		File[] arquivos = pastaMusicas().toFile().listFiles();
		if (arquivos == null) return null;
		for (File f : arquivos) {
			String n = f.getName();
			if (f.isFile() && (n.equalsIgnoreCase(nome + ".mp3") || n.equalsIgnoreCase(nome + ".wav"))) {
				return f.toPath();
			}
		}
		return null;
	}

	public static void baixarEmSegundoPlano(List<Musica> musicas) {
		List<Musica> faltando = new ArrayList<Musica>();
		for (Musica musica : musicas) {
			if (musica.caminhoNuvem != null && !musica.caminhoNuvem.isBlank() && !musica.disponivelNesteComputador()) {
				faltando.add(musica);
			}
		}
		if (faltando.isEmpty() || !SupabaseStorage.configurado()) return;

		Thread tarefa = new Thread(() -> {
			for (Musica musica : faltando) {
				try {
					musica.arquivoLocal();
				} catch (IOException e) {
					System.err.println("Não foi possível baixar \"" + musica.nome + "\": " + e.getMessage());
				}
			}
		}, "baixar-musicas");
		tarefa.setDaemon(true);
		tarefa.start();
	}

	public static void apagarArquivos(List<Musica> musicas) {
		List<String> chaves = new ArrayList<String>();
		for (Musica musica : musicas) {
			if (musica.caminhoNuvem != null && !musica.caminhoNuvem.isBlank()) {
				chaves.add(musica.caminhoNuvem);
			}
			Path antigo = (musica.caminho == null || musica.caminho.isBlank()) && musica.caminhoNuvem == null
					? musica.localizarPorNome() : null;
			for (Path arquivo : new Path[] { musica.arquivoCopiado(), musica.arquivoBaixado(), antigo }) {
				try {
					if (arquivo != null) Files.deleteIfExists(arquivo);
				} catch (IOException e) {
					System.err.println("Não foi possível apagar " + arquivo + ": " + e.getMessage());
				}
			}
		}

		if (!chaves.isEmpty() && SupabaseStorage.configurado()) {
			try {
				SupabaseStorage.apagar(chaves.toArray(new String[0]));
			} catch (IOException e) {
				System.err.println("Os arquivos continuam na nuvem: " + e.getMessage());
			}
		}
	}

	void desfazerCadastro() {
		try {
			MusicaDAO objeto = new MusicaDAO();
			objeto.setIdMusica(this.idMusica);
			objeto.deletar();
		} catch (Exception e) {
			System.err.println("Não foi possível desfazer o cadastro da música: " + e.getMessage());
		}
		apagarSomenteDaNuvem();
	}

	private void apagarSomenteDaNuvem() {
		if (caminhoNuvem == null || caminhoNuvem.isBlank()) return;
		try {
			SupabaseStorage.apagar(caminhoNuvem);
		} catch (IOException e) {
			System.err.println("O arquivo continua na nuvem: " + e.getMessage());
		}
		caminhoNuvem = null;
	}

	public String cadastrar() throws SQLException, ClassNotFoundException, IOException {
		Path copia = arquivoCopiado();

		if (caminhoNuvem == null && copia != null) {
			if (!Files.isRegularFile(copia)) {
				throw new FileNotFoundException("Arquivo da música não encontrado: " + caminho);
			}
			if (SupabaseStorage.configurado()) {
				int idUsuario = Sessao.getIdUsuario();
				String nomeArquivo = copia.getFileName().toString();
				int ponto = nomeArquivo.lastIndexOf('.');
				String extensao = ponto >= 0 ? nomeArquivo.substring(ponto).toLowerCase() : ".mp3";
				String chave = (idUsuario > 0 ? idUsuario : 0) + "/" + UUID.randomUUID() + extensao;
				SupabaseStorage.enviar(copia, chave, SupabaseStorage.tipoConteudo(nomeArquivo));
				this.caminhoNuvem = chave;
			} else {
				System.err.println("supabase.properties não encontrado: \"" + nome + "\" fica só neste computador.");
			}
		}

		try {
			MusicaDAO objeto = new MusicaDAO();
			objeto.setNome(this.nome);
			objeto.setDuracao(this.duracao);
			objeto.setCaminho(this.caminho);
			objeto.setCaminhoNuvem(this.caminhoNuvem);
			objeto.setIdPlaylist(this.idPlaylist);

			return objeto.cadastrar();
		} catch (SQLException | ClassNotFoundException | RuntimeException e) {
			apagarSomenteDaNuvem();
			throw e;
		}
	}

	public static Musica consultar(int idMusica) throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		objeto.setIdMusica(idMusica);
		ResultSet rs = objeto.consultar();

		return rs.next() ? deLinha(rs) : null;
	}

	public static List<Musica> listar() throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		ResultSet rs = objeto.listar();
		List<Musica> lista = new ArrayList<Musica>();

		while (rs.next()) {
			lista.add(deLinha(rs));
		}

		return lista;
	}

	public static List<Musica> listarPorPlaylist(int idPlaylist) throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		objeto.setIdPlaylist(idPlaylist);
		ResultSet rs = objeto.listarPorPlaylist();
		List<Musica> lista = new ArrayList<Musica>();

		while (rs.next()) {
			lista.add(deLinha(rs));
		}

		return lista;
	}

	public String alterar() throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		objeto.setIdMusica(this.idMusica);
		objeto.setNome(this.nome);
		objeto.setDuracao(this.duracao);
		objeto.setCaminho(this.caminho);
		objeto.setCaminhoNuvem(this.caminhoNuvem);
		objeto.setIdPlaylist(this.idPlaylist);

		return objeto.alterar();
	}

	public String deletar() throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		objeto.setIdMusica(this.idMusica);

		String resposta = objeto.deletar();
		apagarArquivos(List.of(this));

		return resposta;
	}
}
