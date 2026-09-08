package telas;

import java.awt.Cursor;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.ArmazenamentoAudio;

/**
 * Botão para selecionar um arquivo de áudio no computador do usuário e
 * importá-lo para a pasta local da biblioteca do Echo Music, seguindo o
 * mesmo padrão visual do {@link SeletorImagem}. Ao escolher um arquivo,
 * ele já é copiado (via {@link ArmazenamentoAudio}) e o callback
 * {@link #aoImportar} é disparado com o arquivo já no novo local.
 */
public class SeletorAudio extends JButton {

    private File arquivoImportado;
    private Consumer<File> aoImportar;

    public SeletorAudio(String textoPlaceholder) {
        super(textoPlaceholder);

        setFont(Tema.FONTE_SUBTITULO);
        setForeground(Tema.TEXTO_SECUNDARIO);
        setBackground(Tema.FUNDO_HOVER);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Tema.BORDA));
        setPreferredSize(new Dimension(260, 40));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addActionListener(e -> selecionarArquivo());
    }

    /**
     * Define o que acontece quando um arquivo é importado com sucesso.
     * Recebe o arquivo já copiado para a pasta da biblioteca — é o
     * {@code getName()} desse arquivo que deve ser gravado no banco.
     */
    public void aoImportar(Consumer<File> ouvinte) {
        this.aoImportar = ouvinte;
    }

    private void selecionarArquivo() {
        JFileChooser seletor = new JFileChooser();
        seletor.setFileFilter(new FileNameExtensionFilter(
                "Arquivos de áudio", "mp3", "wav", "m4a", "flac", "ogg", "aac"));

        if (seletor.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File original = seletor.getSelectedFile();

        try {
            arquivoImportado = ArmazenamentoAudio.importar(original);
            setText(original.getName());
            setToolTipText("Salvo em: " + arquivoImportado.getAbsolutePath());

            if (aoImportar != null) {
                aoImportar.accept(arquivoImportado);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível importar o arquivo: " + ex.getMessage(),
                    "Erro ao importar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public File getArquivoImportado() {
        return arquivoImportado;
    }
}