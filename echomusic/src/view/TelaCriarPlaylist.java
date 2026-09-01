package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaCriarPlaylist extends JFrame {

    private static final Color FUNDO = new Color(249, 250, 252);
    private static final Color BRANCO = Color.WHITE;
    private static final Color AZUL = new Color(54, 117, 205);
    private static final Color TEXTO = new Color(45, 45, 45);
    private static final Color TEXTO_SECUNDARIO = new Color(120, 120, 120);
    private static final Color PLACEHOLDER = new Color(160, 163, 168);
    private static final Color BORDA = new Color(215, 218, 222);

    private JTextField campoNome;
    private JTextArea campoDescricao;
    private JComboBox<String> campoCategoria;
    private JLabel rotuloArquivoCapa;
    private final JFileChooser seletorImagem = new JFileChooser();

    public TelaCriarPlaylist() {
        setTitle("MeuPlayer - Criar Nova Playlist");
        setSize(950, 620);
        setMinimumSize(new Dimension(820, 560));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel corpo = new JPanel(new BorderLayout());
        corpo.add(new BarraLateral("Playlists"), BorderLayout.WEST);
        corpo.add(criarAreaConteudo(), BorderLayout.CENTER);

        add(corpo, BorderLayout.CENTER);
        add(criarRodapePlayer(), BorderLayout.SOUTH);
    }

    private JPanel criarAreaConteudo() {
        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(FUNDO);

        area.add(criarCabecalhoTopo(), BorderLayout.NORTH);

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBackground(FUNDO);
        formulario.setBorder(new EmptyBorder(16, 24, 16, 24));

        formulario.add(criarBreadcrumb());
        formulario.add(criarTitulo());
        formulario.add(criarLinhaPrincipal());
        formulario.add(criarSecaoMusicas());
        formulario.add(Box.createVerticalGlue());
        formulario.add(criarRodapeBotoes());

        JScrollPane scroll = new JScrollPane(formulario);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);

        area.add(scroll, BorderLayout.CENTER);
        return area;
    }

    // ---------- Cabeçalho (busca + sair) ----------

    private JPanel criarCabecalhoTopo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(BRANCO);
        painel.setBorder(new EmptyBorder(10, 16, 10, 16));

        JPanel campoBuscaWrapper = new JPanel(new BorderLayout());
        campoBuscaWrapper.setBackground(FUNDO);
        campoBuscaWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        campoBuscaWrapper.setPreferredSize(new Dimension(300, 32));

        JLabel lupa = new JLabel("\uD83D\uDD0D");
        lupa.setBorder(new EmptyBorder(0, 0, 0, 6));

        JTextField campoBusca = new JTextField("Buscar Música...");
        campoBusca.setBorder(null);
        campoBusca.setBackground(FUNDO);
        campoBusca.setForeground(PLACEHOLDER);

        campoBuscaWrapper.add(lupa, BorderLayout.WEST);
        campoBuscaWrapper.add(campoBusca, BorderLayout.CENTER);

        JPanel alinhador = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        alinhador.setBackground(BRANCO);
        alinhador.add(campoBuscaWrapper);

        JButton botaoSair = new JButton("X");
        botaoSair.setFont(new Font("Arial", Font.PLAIN, 12));
        botaoSair.setForeground(BRANCO);
        botaoSair.setBackground(new Color(200, 80, 80));
        botaoSair.setFocusPainted(false);
        botaoSair.setBorder(new EmptyBorder(7, 14, 7, 14));

        painel.add(alinhador, BorderLayout.CENTER);
        painel.add(botaoSair, BorderLayout.EAST);
        return painel;
    }

    // ---------- Breadcrumb + Título ----------

    private JLabel criarBreadcrumb() {
        JLabel label = new JLabel("");
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(AZUL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 8, 0));
        return label;
    }

    private JLabel criarTitulo() {
        JLabel titulo = new JLabel("Criar Nova Playlist");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(0, 0, 16, 0));
        return titulo;
    }

    // ---------- Linha principal: Capa | Nome/Descrição/Categoria ----------

    private JPanel criarLinhaPrincipal() {
        JPanel linha = new JPanel(new GridBagLayout());
        linha.setBackground(FUNDO);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        linha.add(criarColunaCapa(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        linha.add(criarColunaDados(), gbc);

        return linha;
    }

    private JPanel criarColunaCapa() {
        JPanel coluna = new JPanel();
        coluna.setLayout(new BoxLayout(coluna, BoxLayout.Y_AXIS));
        coluna.setBackground(FUNDO);
        coluna.setBorder(new EmptyBorder(0, 0, 0, 32));

        JLabel rotulo = criarRotulo("");
        rotulo.setBorder(new EmptyBorder(0, 0, 5, 0));
        coluna.add(rotulo);
        coluna.add(criarPainelUploadCapa());
        return coluna;
    }

    private JPanel criarPainelUploadCapa() {
        int lado = 150;

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(BRANCO);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setPreferredSize(new Dimension(lado, lado + 40));
        painel.setMaximumSize(new Dimension(lado, lado + 40));

        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(FUNDO);
        area.setPreferredSize(new Dimension(lado, lado));
        area.setBorder(BorderFactory.createLineBorder(BORDA, 1, true));
        area.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setOpaque(false);

        JLabel icone = new JLabel("\uD83D\uDDBC");
        icone.setFont(new Font("Arial", Font.PLAIN, 28));
        icone.setForeground(PLACEHOLDER);
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        rotuloArquivoCapa = new JLabel("Ícone de Upload");
        rotuloArquivoCapa.setFont(new Font("Arial", Font.PLAIN, 11));
        rotuloArquivoCapa.setForeground(TEXTO_SECUNDARIO);
        rotuloArquivoCapa.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotuloArquivoCapa.setBorder(new EmptyBorder(6, 0, 0, 0));

        centro.add(Box.createVerticalGlue());
        centro.add(icone);
        centro.add(rotuloArquivoCapa);
        centro.add(Box.createVerticalGlue());
        area.add(centro, BorderLayout.CENTER);

        JButton botaoSelecionar = new JButton("Selecionar Imagem");
        botaoSelecionar.setFont(new Font("Arial", Font.PLAIN, 12));
        botaoSelecionar.setFocusPainted(false);
        botaoSelecionar.setBackground(BRANCO);
        botaoSelecionar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1, true),
                new EmptyBorder(6, 12, 6, 12)));

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        painelBotao.setBackground(BRANCO);
        painelBotao.add(botaoSelecionar);

        painel.add(area, BorderLayout.CENTER);
        painel.add(painelBotao, BorderLayout.SOUTH);

        area.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirSeletorImagem();
            }
        });
        botaoSelecionar.addActionListener(e -> abrirSeletorImagem());

        return painel;
    }

    private void abrirSeletorImagem() {
        int resultado = seletorImagem.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            rotuloArquivoCapa.setText(seletorImagem.getSelectedFile().getName());
        }
    }

    private JPanel criarColunaDados() {
        JPanel coluna = new JPanel();
        coluna.setLayout(new BoxLayout(coluna, BoxLayout.Y_AXIS));
        coluna.setBackground(FUNDO);

        campoNome = criarCampoTexto("Digite o nome...");
        coluna.add(criarGrupoCampo("Nome da Playlist", campoNome, 32));

        campoDescricao = new JTextArea(3, 1);
        campoDescricao.setFont(new Font("Arial", Font.PLAIN, 13));
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        campoDescricao.setBorder(new EmptyBorder(8, 10, 8, 10));
        aplicarPlaceholder(campoDescricao, "Digite uma descrição opcional...");

        JScrollPane scrollDescricao = new JScrollPane(campoDescricao);
        scrollDescricao.setBorder(BorderFactory.createLineBorder(BORDA, 1, true));
        coluna.add(criarGrupoCampo("Descrição", scrollDescricao, 90));

        campoCategoria = new JComboBox<>(new String[] {
                "Selecione...", "Rock", "Pop", "Eletrônica", "Lo-Fi", "Treino", "Sertanejo"
        });
        campoCategoria.setFont(new Font("Arial", Font.PLAIN, 13));
        campoCategoria.setBackground(BRANCO);
        campoCategoria.setBorder(BorderFactory.createLineBorder(BORDA, 1, true));
        coluna.add(criarGrupoCampo("Categoria", campoCategoria, 32));

        return coluna;
    }

    // ---------- Seção "Adicionar Músicas" ----------

    private JPanel criarSecaoMusicas() {
        JPanel secao = new JPanel();
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBackground(FUNDO);
        secao.setAlignmentX(Component.LEFT_ALIGNMENT);
        secao.setBorder(new EmptyBorder(6, 0, 16, 0));

        JLabel rotulo = criarRotulo("Adicionar Músicas (Opcional nesta etapa)");
        rotulo.setFont(new Font("Arial", Font.BOLD, 12));
        rotulo.setBorder(new EmptyBorder(0, 0, 8, 0));

        JButton botaoAdicionar = new JButton("+  Buscar e Adicionar");
        botaoAdicionar.setFont(new Font("Arial", Font.PLAIN, 12));
        botaoAdicionar.setForeground(TEXTO);
        botaoAdicionar.setBackground(BRANCO);
        botaoAdicionar.setFocusPainted(false);
        botaoAdicionar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1, true),
                new EmptyBorder(7, 14, 7, 14)));
        botaoAdicionar.setAlignmentX(Component.LEFT_ALIGNMENT);

        secao.add(rotulo);
        secao.add(botaoAdicionar);
        return secao;
    }

    // ---------- Rodapé de botões (Cancelar / Salvar) ----------

    private JPanel criarRodapeBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painel.setBackground(FUNDO);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoCancelar.setBackground(FUNDO);
        botaoCancelar.setForeground(TEXTO);
        botaoCancelar.setFocusPainted(false);
        botaoCancelar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1, true),
                new EmptyBorder(8, 18, 8, 18)));
        botaoCancelar.addActionListener(e -> dispose());

        JButton botaoSalvar = new JButton("SALVAR");
        botaoSalvar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoSalvar.setBackground(AZUL);
        botaoSalvar.setForeground(BRANCO);
        botaoSalvar.setFocusPainted(false);
        botaoSalvar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL, 1, true),
                new EmptyBorder(8, 18, 8, 18)));
        botaoSalvar.addActionListener(e -> salvarPlaylist());

        painel.add(botaoCancelar);
        painel.add(botaoSalvar);
        return painel;
    }

    private void salvarPlaylist() {
        boolean vazio = campoNome.getForeground().equals(PLACEHOLDER) || campoNome.getText().isBlank();

        if (vazio) {
            JOptionPane.showMessageDialog(this,
                    "Informe o nome da playlist antes de salvar.",
                    "Campo obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Playlist \"" + campoNome.getText() + "\" salva com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    // ---------- Rodapé fixo do player ----------

    private JPanel criarRodapePlayer() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(BRANCO);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDA),
                new EmptyBorder(8, 16, 8, 16)));

        JPanel infoMusica = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        infoMusica.setBackground(BRANCO);

        JLabel icone = new JLabel("\u266A");
        icone.setForeground(TEXTO_SECUNDARIO);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(BRANCO);

        JLabel titulo = new JLabel("Nenhuma música selecionada");
        titulo.setFont(new Font("Arial", Font.PLAIN, 11));
        titulo.setForeground(TEXTO);

        JLabel tempo = new JLabel("--:-- / --:--");
        tempo.setFont(new Font("Arial", Font.PLAIN, 10));
        tempo.setForeground(TEXTO_SECUNDARIO);

        textos.add(titulo);
        textos.add(tempo);
        infoMusica.add(icone);
        infoMusica.add(textos);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        controles.setBackground(BRANCO);
        controles.add(criarBotaoControle("\u23EE"));
        controles.add(criarBotaoControle("\u25B6"));
        controles.add(criarBotaoControle("\u23F8"));
        controles.add(criarBotaoControle("\u23ED"));

        JPanel volumeEFila = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        volumeEFila.setBackground(BRANCO);
        JSlider volume = new JSlider(0, 100, 60);
        volume.setPreferredSize(new Dimension(90, 20));
        volume.setBackground(BRANCO);
        volumeEFila.add(new JLabel("\uD83D\uDD09"));
        volumeEFila.add(volume);
        volumeEFila.add(new JLabel("\uD83D\uDD0A"));
        volumeEFila.add(new JLabel("\u2261"));

        painel.add(infoMusica, BorderLayout.WEST);
        painel.add(controles, BorderLayout.CENTER);
        painel.add(volumeEFila, BorderLayout.EAST);
        return painel;
    }

    private JButton criarBotaoControle(String simbolo) {
        JButton botao = new JButton(simbolo);
        botao.setFont(new Font("Arial", Font.PLAIN, 13));
        botao.setFocusPainted(false);
        botao.setBackground(FUNDO);
        botao.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        return botao;
    }

    // ---------- Utilitários de campo (locais, sem depender de outras classes) ----------

    private JLabel criarRotulo(String texto) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setFont(new Font("Arial", Font.PLAIN, 12));
        rotulo.setForeground(TEXTO);
        rotulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return rotulo;
    }

    private JTextField criarCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setBackground(BRANCO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1, true),
                new EmptyBorder(8, 10, 8, 10)));
        aplicarPlaceholder(campo, placeholder);
        return campo;
    }

    private JPanel criarGrupoCampo(String rotuloTexto, JComponent campo, int alturaMaxima) {
        JPanel grupo = new JPanel();
        grupo.setLayout(new BoxLayout(grupo, BoxLayout.Y_AXIS));
        grupo.setBackground(FUNDO);
        grupo.setAlignmentX(Component.LEFT_ALIGNMENT);
        grupo.setBorder(new EmptyBorder(0, 0, 14, 0));

        JLabel rotulo = criarRotulo(rotuloTexto);
        rotulo.setBorder(new EmptyBorder(0, 0, 5, 0));

        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, alturaMaxima));

        grupo.add(rotulo);
        grupo.add(campo);
        return grupo;
    }

    /** Simula um placeholder, já que JTextField/JTextArea não têm suporte nativo no Swing. */
    private void aplicarPlaceholder(javax.swing.text.JTextComponent campo, String texto) {
        campo.setText(texto);
        campo.setForeground(PLACEHOLDER);

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campo.getText().equals(texto) && campo.getForeground().equals(PLACEHOLDER)) {
                    campo.setText("");
                    campo.setForeground(TEXTO);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(texto);
                    campo.setForeground(PLACEHOLDER);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCriarPlaylist().setVisible(true));
    }
}