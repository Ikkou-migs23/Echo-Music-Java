package telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 * Tela de edição de uma música (nome, artista, álbum, categoria e capa).
 * Reaproveita BarraLateral, BarraSuperior e BarraReprodutor para seguir o
 * mesmo layout das demais telas internas.
 */
public class TelaEditarMusica extends JFrame {

    private CampoTextoPlaceholder txtNome;
    private CampoTextoPlaceholder txtArtista;
    private CampoTextoPlaceholder txtAlbum;
    private JComboBox<String> cbCategoria;

    public TelaEditarMusica() {
        setTitle("Echo Music - Editar Música");
        setSize(1060, 700);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO);
        raiz.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        BarraLateral barraLateral = new BarraLateral("Playlists");
        barraLateral.aoClicar(this::navegar);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Tema.FUNDO);
        principal.add(barraLateral, BorderLayout.WEST);
        principal.add(criarAreaPrincipal(), BorderLayout.CENTER);

        raiz.add(new BarraTitulo(this), BorderLayout.NORTH);
        raiz.add(principal, BorderLayout.CENTER);

        add(raiz);
    }

    private void navegar(String tela) {
        switch (tela) {
            case "Home", "Playlists" -> {
                dispose();
                new TelaPrincipal().setVisible(true);
            }
            case "Perfil" -> {
                dispose();
                new TelaPerfil().setVisible(true);
            }
            case "Sobre" -> {
                // TODO: dispose(); new TelaSobre().setVisible(true);
            }
        }
    }

    private JPanel criarAreaPrincipal() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Tema.FUNDO);
        painel.add(new BarraSuperior(), BorderLayout.NORTH);
        painel.add(criarFormulario(), BorderLayout.CENTER);
        painel.add(new BarraReprodutor(), BorderLayout.SOUTH);
        return painel;
    }

    // ---------- formulário ----------

    private JPanel criarFormulario() {
        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(Tema.FUNDO);
        area.setBorder(BorderFactory.createEmptyBorder(22, 20, 10, 20));

        area.add(criarCabecalho(), BorderLayout.NORTH);

        JPanel conteudo = new JPanel(new BorderLayout(26, 0));
        conteudo.setBackground(Tema.FUNDO);
        conteudo.add(criarCapa(), BorderLayout.WEST);
        conteudo.add(criarCampos(), BorderLayout.CENTER);
        area.add(conteudo, BorderLayout.CENTER);

        area.add(criarBotoes(), BorderLayout.SOUTH);
        return area;
    }

    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        cabecalho.setBackground(Tema.FUNDO);

        JLabel titulo = new JLabel("Editar Música");
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        titulo.setForeground(Tema.TEXTO);
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 0, 18, 0));

        cabecalho.add(titulo);
        return cabecalho;
    }

    private JPanel criarBotoes() {
        JPanel botoes = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 5));
        botoes.setBackground(Tema.FUNDO);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(Tema.FONTE_TEXTO);
        cancelar.setFocusPainted(false);
        cancelar.setPreferredSize(new Dimension(100, 32));
        cancelar.addActionListener(e -> dispose());

        JButton salvar = new JButton("SALVAR");
        salvar.setFont(Tema.FONTE_SUBTITULO);
        salvar.setForeground(Tema.BRANCO);
        salvar.setBackground(Tema.AZUL);
        salvar.setFocusPainted(false);
        salvar.setBorderPainted(false);
        salvar.setPreferredSize(new Dimension(115, 32));
        salvar.addActionListener(e -> salvar());

        botoes.add(cancelar);
        botoes.add(salvar);
        return botoes;
    }

    // ---------- capa ----------

    private JPanel criarCapa() {
        JPanel painel = new JPanel();
        painel.setBackground(Tema.FUNDO);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setPreferredSize(new Dimension(210, 260));

        JLabel titulo = new JLabel("Capa");
        titulo.setFont(Tema.FONTE_SUBTITULO);
        titulo.setForeground(Tema.TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        SeletorImagem seletorCapa = new SeletorImagem("CAPA", 190);
        seletorCapa.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(seletorCapa);
        return painel;
    }

    // ---------- campos ----------

    private JPanel criarCampos() {
        JPanel painel = new JPanel();
        painel.setBackground(Tema.FUNDO);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        txtNome = adicionarCampo(painel, "Nome da Música", "Digite o nome...");
        txtArtista = adicionarCampo(painel, "Artista", "Digite o artista...");
        txtAlbum = adicionarCampo(painel, "Álbum", "Digite o álbum...");
        painel.add(criarCampoCategoria());

        return painel;
    }

    private JPanel criarCampoCategoria() {
        JPanel categoria = new JPanel(new BorderLayout(0, 5));
        categoria.setBackground(Tema.FUNDO);
        categoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        categoria.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelCategoria = new JLabel("Categoria");
        labelCategoria.setFont(Tema.FONTE_SUBTITULO);
        labelCategoria.setForeground(Tema.TEXTO);

        cbCategoria = new JComboBox<>(new String[]{
                "Selecione...", "Rock", "Pop", "Lo-Fi", "Hip-Hop",
                "Eletrônica", "MPB", "Jazz", "Sertanejo", "Outros"
        });
        cbCategoria.setFont(Tema.FONTE_TEXTO_PEQUENA);
        cbCategoria.setBackground(Tema.BRANCO);
        cbCategoria.setPreferredSize(new Dimension(220, 32));

        categoria.add(labelCategoria, BorderLayout.NORTH);
        categoria.add(cbCategoria, BorderLayout.CENTER);
        return categoria;
    }

    private CampoTextoPlaceholder adicionarCampo(JPanel painel, String titulo, String placeholder) {
        JPanel campo = new JPanel(new BorderLayout(0, 5));
        campo.setBackground(Tema.FUNDO);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(titulo);
        label.setFont(Tema.FONTE_SUBTITULO);
        label.setForeground(Tema.TEXTO);

        CampoTextoPlaceholder texto = new CampoTextoPlaceholder(placeholder);
        texto.setFont(Tema.FONTE_TEXTO_PEQUENA);
        texto.setForeground(Tema.TEXTO);
        texto.setPreferredSize(new Dimension(320, 32));
        texto.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Tema.BORDA),
                BorderFactory.createEmptyBorder(4, 7, 4, 7)));

        campo.add(label, BorderLayout.NORTH);
        campo.add(texto, BorderLayout.CENTER);

        painel.add(campo);
        painel.add(Box.createVerticalStrut(10));
        return texto;
    }

    // ---------- ações ----------

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Informe o nome da música.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO: persistir as alterações assim que houver um serviço de músicas
        JOptionPane.showMessageDialog(this,
                "Música atualizada com sucesso!",
                "Echo Music",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaEditarMusica().setVisible(true));
    }
}
