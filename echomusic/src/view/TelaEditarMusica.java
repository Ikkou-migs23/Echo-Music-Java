package telas;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TelaEditarMusica extends JFrame {

    private static final Color FUNDO = new Color(249, 250, 252);
    private static final Color AZUL = new Color(54, 117, 205);
    private static final Color TEXTO = new Color(45, 45, 45);
    private static final Color BORDA = new Color(215, 218, 222);

    private JTextField txtNome;
    private JTextField txtArtista;
    private JTextField txtAlbum;
    private JComboBox<String> cbCategoria;

    public TelaEditarMusica() {

        setTitle("Echo Music - Editar Música");
        setSize(900, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // PAINEL PRINCIPAL
        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(FUNDO);

        BarraLateral barraLateral = new BarraLateral("Playlists");

        principal.add(barraLateral, BorderLayout.WEST);
        principal.add(criarAreaPrincipal(), BorderLayout.CENTER);

        setContentPane(principal);
    }

    // ÁREA PRINCIPAL
    private JPanel criarAreaPrincipal() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);

        painel.add(criarTopo(), BorderLayout.NORTH);
        painel.add(criarFormulario(), BorderLayout.CENTER);

        return painel;
    }

    // TOPO
    private JPanel criarTopo() {

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(
            BorderFactory.createMatteBorder(
                0, 0, 1, 0, BORDA
            )
        );

        // Botão sair
        JPanel direita = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 10, 8)
        );
        direita.setBackground(Color.WHITE);

        JButton sair = new JButton("Sair");
        sair.setFont(new Font("Arial", Font.PLAIN, 10));
        sair.setFocusPainted(false);

        direita.add(sair);

        topo.add(direita, BorderLayout.EAST);

        return topo;
    }
    
    // FORMULÁRIO
    private JPanel criarFormulario() {

        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(FUNDO);
        area.setBorder(
            BorderFactory.createEmptyBorder(
                18, 16, 8, 16
            )
        );

        // TÍTULO
        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(
            new BoxLayout(cabecalho, BoxLayout.Y_AXIS)
        );
        cabecalho.setBackground(FUNDO);

        JLabel titulo = new JLabel("Editar Música");
        titulo.setFont(
            new Font("Arial", Font.BOLD, 17)
        );
        titulo.setForeground(TEXTO);

        titulo.setBorder(
            BorderFactory.createEmptyBorder(
                7, 0, 15, 0
            )
        );
        
        cabecalho.add(titulo);

        area.add(cabecalho, BorderLayout.NORTH);

        // CONTEÚDO
        JPanel conteudo = new JPanel(
            new BorderLayout(20, 0)
        );
        conteudo.setBackground(FUNDO);

        conteudo.add(criarCapa(), BorderLayout.WEST);
        conteudo.add(criarCampos(), BorderLayout.CENTER);

        area.add(conteudo, BorderLayout.CENTER);

        // BOTÕES
        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 8, 5)
        );
        botoes.setBackground(FUNDO);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(
            new Font("Arial", Font.PLAIN, 11)
        );
        cancelar.setFocusPainted(false);
        cancelar.setPreferredSize(
            new Dimension(82, 29)
        );

        JButton salvar = new JButton("SALVAR");
        salvar.setFont(
            new Font("Arial", Font.BOLD, 11)
        );
        salvar.setForeground(Color.WHITE);
        salvar.setBackground(AZUL);
        salvar.setFocusPainted(false);
        salvar.setBorderPainted(false);
        salvar.setPreferredSize(
            new Dimension(105, 29)
        );

        cancelar.addActionListener(e -> dispose());

        salvar.addActionListener(e -> salvar());

        botoes.add(cancelar);
        botoes.add(salvar);

        area.add(botoes, BorderLayout.SOUTH);

        return area;
    }

    // CAPA
    private JPanel criarCapa() {

        JPanel painel = new JPanel();
        painel.setBackground(FUNDO);

        painel.setLayout(
            new BoxLayout(painel, BoxLayout.Y_AXIS)
        );

        painel.setPreferredSize(
            new Dimension(190, 240)
        );

        JLabel titulo = new JLabel("Capa");
        titulo.setFont(
            new Font("Arial", Font.BOLD, 11)
        );
        titulo.setForeground(TEXTO);

        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(7));

        JLabel capa = new JLabel(
            "<html><center>"
            + "♪<br><br>"
            + "Capa da música"
            + "</center></html>",
            SwingConstants.CENTER
        );

        capa.setFont(
            new Font("Arial", Font.PLAIN, 11)
        );

        capa.setForeground(
            new Color(110, 110, 110)
        );

        capa.setBackground(
            new Color(245, 246, 248)
        );

        capa.setOpaque(true);

        capa.setBorder(
            new LineBorder(BORDA)
        );

        capa.setPreferredSize(
            new Dimension(170, 170)
        );

        capa.setMinimumSize(
            new Dimension(170, 170)
        );

        capa.setMaximumSize(
            new Dimension(170, 170)
        );

        capa.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(capa);
        painel.add(Box.createVerticalStrut(9));

        JButton selecionar = new JButton(
            "Selecionar Imagem"
        );

        selecionar.setFont(
            new Font("Arial", Font.PLAIN, 10)
        );

        selecionar.setFocusPainted(false);
        selecionar.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        selecionar.addActionListener(e ->
            selecionarImagem()
        );

        painel.add(selecionar);

        return painel;
    }

    // CAMPOS
    private JPanel criarCampos() {

        JPanel painel = new JPanel();
        painel.setBackground(FUNDO);

        painel.setLayout(
            new BoxLayout(painel, BoxLayout.Y_AXIS)
        );

        txtNome = adicionarCampo(
            painel,
            "Nome da Música",
            "Digite o nome..."
        );

        txtArtista = adicionarCampo(
            painel,
            "Artista",
            "Digite o artista..."
        );

        txtAlbum = adicionarCampo(
            painel,
            "Álbum",
            "Digite o álbum..."
        );

        // Categoria
        JPanel categoria = new JPanel(
            new BorderLayout(0, 5)
        );
        categoria.setBackground(FUNDO);

        JLabel labelCategoria = new JLabel(
            "Categoria"
        );

        labelCategoria.setFont(
            new Font("Arial", Font.BOLD, 11)
        );

        labelCategoria.setForeground(TEXTO);

        cbCategoria = new JComboBox<>(
            new String[] {
                "Selecione...",
                "Rock",
                "Pop",
                "Lo-Fi",
                "Hip-Hop",
                "Eletrônica",
                "MPB",
                "Jazz",
                "Sertanejo",
                "Outros"
            }
        );

        cbCategoria.setFont(
            new Font("Arial", Font.PLAIN, 11)
        );

        cbCategoria.setBackground(Color.WHITE);
        cbCategoria.setPreferredSize(
            new Dimension(200, 30)
        );

        categoria.add(
            labelCategoria,
            BorderLayout.NORTH
        );

        categoria.add(
            cbCategoria,
            BorderLayout.CENTER
        );

        categoria.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 55)
        );

        categoria.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        painel.add(categoria);

        return painel;
    }

    private JTextField adicionarCampo(
        JPanel painel,
        String titulo,
        String placeholder
    ) {

        JPanel campo = new JPanel(
            new BorderLayout(0, 5)
        );

        campo.setBackground(FUNDO);

        JLabel label = new JLabel(titulo);

        label.setFont(
            new Font("Arial", Font.BOLD, 11)
        );

        label.setForeground(TEXTO);

        JTextField texto = new JTextField();

        texto.setFont(
            new Font("Arial", Font.PLAIN, 11)
        );

        texto.setText(placeholder);
        texto.setForeground(
            new Color(130, 130, 130)
        );

        texto.setPreferredSize(
            new Dimension(300, 30)
        );

        texto.setBorder(
            BorderFactory.createCompoundBorder(
                new LineBorder(BORDA),
                BorderFactory.createEmptyBorder(
                    4, 7, 4, 7
                )
            )
        );

        campo.add(label, BorderLayout.NORTH);
        campo.add(texto, BorderLayout.CENTER);

        campo.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 55)
        );

        campo.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        painel.add(campo);
        painel.add(
            Box.createVerticalStrut(9)
        );

        return texto;
    }

    // AÇÕES
    private void selecionarImagem() {

        JFileChooser chooser =
            new JFileChooser();

        if (chooser.showOpenDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            JOptionPane.showMessageDialog(
                this,
                "Imagem selecionada:\n"
                + chooser.getSelectedFile()
                        .getName()
            );
        }
    }

    private void salvar() {

        if (txtNome.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Informe o nome da música.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
            this,
            "Música atualizada com sucesso!",
            "Echo Music",
            JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new TelaEditarMusica().setVisible(true);

        });
    }
}
