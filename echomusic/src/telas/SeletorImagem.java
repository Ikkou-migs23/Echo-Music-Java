package telas;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * Área quadrada para seleção de imagem (foto de perfil, capa de playlist,
 * capa de música...), seguindo o mesmo padrão visual criado originalmente
 * na TelaPerfil: um quadrado com um texto centralizado indicando para que
 * serve a imagem. Ao clicar, abre o seletor de arquivos do sistema e, ao
 * escolher uma imagem, ela passa a ser exibida no próprio quadrado.
 */
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

        arquivoSelecionado = seletor.getSelectedFile();

        ImageIcon original = new ImageIcon(arquivoSelecionado.getAbsolutePath());
        Image redimensionada = original.getImage().getScaledInstance(lado, lado, Image.SCALE_SMOOTH);

        setText("");
        setIcon(new ImageIcon(redimensionada));
    }

    public File getArquivoSelecionado() {
        return arquivoSelecionado;
    }
}
