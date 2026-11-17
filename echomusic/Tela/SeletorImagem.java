package Tela;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


public class SeletorImagem extends JButton {

    private final int lado;
    private File arquivoSelecionado;

    public SeletorImagem(String textoPlaceholder, int lado) {
        super(textoPlaceholder);
        this.lado = lado;

        setFont(Tema.FONTE_SUBTITULO);
        setForeground(Tema.TEXTO_SECUNDARIO);
        setBackground(Tema.FUNDO_HOVER);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFocusPainted(false);
        setBorder(new LineBorder(Tema.BORDA));
        setPreferredSize(new Dimension(lado, lado));
        setMinimumSize(new Dimension(lado, lado));
        setMaximumSize(new Dimension(lado, lado));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addActionListener(e -> selecionarImagem());
    }

    private void selecionarImagem() {
        JFileChooser seletor = new JFileChooser();
        if (seletor.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File escolhido = seletor.getSelectedFile();
        if (!exibir(escolhido)) {
            JOptionPane.showMessageDialog(this,
                    "O arquivo escolhido não é uma imagem válida (use PNG, JPG, GIF ou BMP).",
                    "Imagem inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        arquivoSelecionado = escolhido;
    }

    public void mostrarImagem(File arquivo) {
        exibir(arquivo);
    }

    public void limparSelecao() {
        arquivoSelecionado = null;
    }

    private boolean exibir(File arquivo) {
        try {
            BufferedImage imagem = ImageIO.read(arquivo);
            if (imagem == null) return false;

            int menor = Math.min(imagem.getWidth(), imagem.getHeight());
            BufferedImage quadrada = imagem.getSubimage(
                    (imagem.getWidth() - menor) / 2, (imagem.getHeight() - menor) / 2, menor, menor);
            Image redimensionada = quadrada.getScaledInstance(lado, lado, Image.SCALE_SMOOTH);

            setText("");
            setIcon(new ImageIcon(redimensionada));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public File getArquivoSelecionado() {
        return arquivoSelecionado;
    }
}
