package Tela;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

final class ImagemCapa {

    private ImagemCapa() {
    }

    static ImageIcon carregarQuadrado(File arquivo, int lado) {
        try {
            BufferedImage imagem = ImageIO.read(arquivo);
            if (imagem == null) return null;

            int menor = Math.min(imagem.getWidth(), imagem.getHeight());
            BufferedImage quadrada = imagem.getSubimage(
                    (imagem.getWidth() - menor) / 2, (imagem.getHeight() - menor) / 2, menor, menor);
            Image reduzida = quadrada.getScaledInstance(lado, lado, Image.SCALE_SMOOTH);
            return new ImageIcon(reduzida);
        } catch (IOException e) {
            return null;
        }
    }
}
