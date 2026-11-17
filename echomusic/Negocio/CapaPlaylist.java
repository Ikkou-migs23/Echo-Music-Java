package Negocio;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class CapaPlaylist {

	private CapaPlaylist() {
	}

	public static Path pasta() {
		return Paths.get(System.getProperty("user.dir")).resolve("capas").normalize();
	}

	public static Path arquivoLocal(String chaveNuvem) {
		if (chaveNuvem == null || chaveNuvem.isBlank()) return null;
		Path arquivo = Paths.get(System.getProperty("user.dir")).resolve(chaveNuvem).normalize();
		return arquivo.startsWith(pasta()) ? arquivo : null;
	}
}
