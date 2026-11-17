package Tela;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public final class Logo {

    private static final String CAMINHO = "/recursos/logo.png";

    private static BufferedImage original;
    private static boolean carregada;

    private Logo() {
    }

    public static ImageIcon criarIcone(int larguraMaxima, int alturaMaxima) {
        BufferedImage imagem = carregar();
        if (imagem == null) {
            return null;
        }

        double escala = Math.min(larguraMaxima / (double) imagem.getWidth(),
                alturaMaxima / (double) imagem.getHeight());
        int largura = Math.max(1, (int) Math.round(imagem.getWidth() * escala));
        int altura = Math.max(1, (int) Math.round(imagem.getHeight() * escala));

        BufferedImage atual = imagem;
        while (atual.getWidth() / 2 >= largura && atual.getHeight() / 2 >= altura) {
            atual = desenhar(atual, atual.getWidth() / 2, atual.getHeight() / 2);
        }
        return new ImageIcon(desenhar(atual, largura, altura));
    }

    public static boolean temTexto(String texto) {
        return texto != null && texto.chars().anyMatch(Character::isLetterOrDigit);
    }

    private static BufferedImage desenhar(BufferedImage origem, int largura, int altura) {
        BufferedImage destino = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = destino.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(origem, 0, 0, largura, altura, null);
        g.dispose();
        return destino;
    }

    private static synchronized BufferedImage carregar() {
        if (!carregada) {
            carregada = true;
            try {
                URL url = Logo.class.getResource(CAMINHO);
                if (url != null) {
                    original = ImageIO.read(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return original;
    }
}
