package telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

import negocio.ServicoReprodutor;

/**
 * Protótipo isolado, sem depender de banco de dados nem das demais telas do
 * sistema, só para validar o fluxo de ponta a ponta:
 *
 * <pre>
 * usuário escolhe um arquivo -&gt; é copiado para ~/EchoMusic/Musicas
 *      -&gt; aparece na lista -&gt; dá pra tocar/pausar
 * </pre>
 *
 * Reprodução funciona nativamente para WAV (via {@code javax.sound.sampled}).
 * Para MP3/M4A/FLAC, troque {@link ServicoReprodutor} pela versão com
 * JavaFX Media — a interface pública já foi pensada pra isso, então nenhuma
 * tela precisa mudar quando essa troca acontecer.
 */
public class TelaPrototipoUpload extends JFrame {

    private final DefaultListModel<File> modeloLista = new DefaultListModel<>();
    private final JList<File> lista = new JList<>(modeloLista);
    private JButton botaoPlayPause;
    private boolean tocando = false;

    public TelaPrototipoUpload() {
        setTitle("Echo Music - Protótipo de Upload e Reprodução");
        setSize(520, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FUNDO);

        add(criarTopo(), BorderLayout.NORTH);
        add(criarLista(), BorderLayout.CENTER);
        add(criarControles(), BorderLayout.SOUTH);
    }

    private JPanel criarTopo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 10, 16));

        JLabel titulo = new JLabel("Importar música para a biblioteca local");
        titulo.setFont(Tema.FONTE_TITULO);
        titulo.setForeground(Tema.TEXTO);

        JLabel caminho = new JLabel(
                "Pasta: " + dao.ArmazenamentoAudio.pastaBiblioteca());
        caminho.setFont(Tema.FONTE_TEXTO_PEQUENA);
        caminho.setForeground(Tema.TEXTO_SECUNDARIO);
        caminho.setBorder(BorderFactory.createEmptyBorder(4, 0, 10, 0));

        SeletorAudio seletor = new SeletorAudio("Escolher arquivo de áudio...");
        seletor.aoImportar(arquivo -> {
            modeloLista.addElement(arquivo);
            lista.setSelectedIndex(modeloLista.size() - 1);
        });

        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(new javax.swing.BoxLayout(cabecalho, javax.swing.BoxLayout.Y_AXIS));
        cabecalho.setBackground(Tema.FUNDO);
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        caminho.setAlignmentX(LEFT_ALIGNMENT);
        cabecalho.add(titulo);
        cabecalho.add(caminho);
        painel.add(cabecalho, BorderLayout.NORTH);

        JPanel linhaSeletor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        linhaSeletor.setBackground(Tema.FUNDO);
        linhaSeletor.add(seletor);
        painel.add(linhaSeletor, BorderLayout.SOUTH);

        return painel;
    }

    private JScrollPane criarLista() {
        lista.setCellRenderer((jlist, arquivo, index, selecionado, foco) -> {
            JLabel label = new JLabel("\u266A  " + arquivo.getName());
            label.setFont(Tema.FONTE_TEXTO);
            label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            label.setOpaque(true);
            label.setBackground(selecionado ? Tema.FUNDO_SELECIONADO : Tema.BRANCO);
            return label;
        });
        lista.setBackground(Tema.BRANCO);
        lista.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private JPanel criarControles() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 14));
        painel.setBackground(Tema.FUNDO);

        botaoPlayPause = new JButton("\u25B6  Tocar selecionada");
        botaoPlayPause.setBackground(Tema.AZUL);
        botaoPlayPause.setForeground(Tema.BRANCO);
        botaoPlayPause.setFocusPainted(false);
        botaoPlayPause.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        botaoPlayPause.addActionListener(e -> alternarPlayPause());

        painel.add(botaoPlayPause);
        return painel;
    }

    private void alternarPlayPause() {
        File selecionado = lista.getSelectedValue();
        if (selecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma música na lista primeiro.");
            return;
        }

        try {
            if (!tocando) {
                ServicoReprodutor.obter().tocar(selecionado);
                botaoPlayPause.setText("\u23F8  Pausar");
            } else {
                ServicoReprodutor.obter().pausar();
                botaoPlayPause.setText("\u25B6  Tocar selecionada");
            }
            tocando = !tocando;
        } catch (UnsupportedAudioFileException ex) {
            JOptionPane.showMessageDialog(this,
                    "Este protótipo só reproduz WAV nativamente (javax.sound.sampled).\n"
                            + "Para MP3/M4A/FLAC, troque o ServicoReprodutor pela versão\n"
                            + "com JavaFX Media, como conversamos anteriormente.",
                    "Formato não suportado para reprodução",
                    JOptionPane.WARNING_MESSAGE);
            tocando = false;
            botaoPlayPause.setText("\u25B6  Tocar selecionada");
        } catch (LineUnavailableException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao tocar o arquivo: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrototipoUpload().setVisible(true));
    }
}