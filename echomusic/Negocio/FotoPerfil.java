package Negocio;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public final class FotoPerfil {

	public static final int LADO = 256;

	private FotoPerfil() {
	}

	public static Path pasta() {
		return Paths.get(System.getProperty("user.dir")).resolve("perfil").normalize();
	}

	public static Path arquivoLocal(int idUsuario, String chaveNuvem) {
		if (chaveNuvem == null || chaveNuvem.isBlank()) {
			return pasta().resolve(idUsuario + "-local.jpg");
		}
		Path arquivo = Paths.get(System.getProperty("user.dir")).resolve(chaveNuvem).normalize();
		return arquivo.startsWith(pasta()) ? arquivo : null;
	}


	public static Path prepararImagem(File original) throws IOException {
		BufferedImage imagem = ImageIO.read(original);
		if (imagem == null) {
			throw new IOException("O arquivo escolhido não é uma imagem válida (use PNG, JPG, GIF ou BMP).");
		}

		int lado = Math.min(imagem.getWidth(), imagem.getHeight());
		int x = (imagem.getWidth() - lado) / 2;
		int y = (imagem.getHeight() - lado) / 2;
		Image reduzida = imagem.getSubimage(x, y, lado, lado)
				.getScaledInstance(LADO, LADO, Image.SCALE_AREA_AVERAGING);


		BufferedImage saida = new BufferedImage(LADO, LADO, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = saida.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, LADO, LADO);
		g.drawImage(reduzida, 0, 0, null);
		g.dispose();

		Path temporario = Files.createTempFile("echomusic-foto-", ".jpg");
		ImageWriter escritor = ImageIO.getImageWritersByFormatName("jpg").next();
		ImageWriteParam parametros = escritor.getDefaultWriteParam();
		parametros.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		parametros.setCompressionQuality(0.85f);
		try (ImageOutputStream saidaArquivo = ImageIO.createImageOutputStream(temporario.toFile())) {
			escritor.setOutput(saidaArquivo);
			escritor.write(null, new IIOImage(saida, null, null), parametros);
		} finally {
			escritor.dispose();
		}
		return temporario;
	}
}
