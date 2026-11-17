package DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


final class ConfigSupabase {

	private static final String ARQUIVO = "supabase.properties";

	private static Properties propriedades;

	private ConfigSupabase() {
	}

	static Path arquivo() {
		return Paths.get(System.getProperty("user.dir"), ARQUIVO);
	}


	static boolean existe() {
		return Files.isRegularFile(arquivo());
	}

	private static synchronized Properties carregar() throws IOException {
		if (propriedades == null) {
			Path arquivo = arquivo();
			if (!Files.isRegularFile(arquivo)) {
				throw new IOException("Arquivo " + ARQUIVO + " não encontrado em " + arquivo.getParent()
						+ ". Copie o supabase.properties.example para supabase.properties e preencha os dados.");
			}
			Properties p = new Properties();
			try (FileInputStream in = new FileInputStream(arquivo.toFile())) {
				p.load(in);
			}
			propriedades = p;
		}
		return propriedades;
	}


	static String obrigatorio(String chave) throws IOException {
		String valor = carregar().getProperty(chave);
		if (valor == null || valor.isBlank() || valor.contains("COLE_AQUI")) {
			throw new IOException("Preencha \"" + chave + "\" no arquivo " + ARQUIVO + ".");
		}
		return valor.trim();
	}

	static String opcional(String chave, String padrao) throws IOException {
		String valor = carregar().getProperty(chave);
		return (valor == null || valor.isBlank()) ? padrao : valor.trim();
	}
}
