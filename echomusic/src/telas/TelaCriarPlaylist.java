package telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Tela de criação de uma nova playlist (nome, descrição, categoria, capa e
 * músicas). Reaproveita BarraLateral, BarraSuperior e BarraReprodutor para
 * seguir o mesmo layout das demais telas internas.
 */
public class TelaCriarPlaylist extends JFrame {

    private CampoTextoPlaceholder campoNome;
    private AreaTextoPlaceholder campoDescricao;
    private JComboBox<String> campoCategoria;
    private SeletorImagem seletorCapa;

    public TelaCriarPlaylist() {
        setTitle("Echo Music - Criar Nova Playlist");
        setSize(1080, 720);
        setMinimumSize(new Dimension(940, 640));
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO);
        raiz.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        BarraLateral barraLateral = new BarraLateral("Playlists");
        barraLateral.aoClicar(this::navegar);

        JPanel corpo = new JPanel(new BorderLayout());
        corpo.add(barraLateral, BorderLayout.WEST);
        corpo.add(criarAreaConteudo(), BorderLayout.CENTER);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Tema.FUNDO);
        principal.add(corpo, BorderLayout.CENTER);
        principal.add(new BarraReprodutor(), BorderLayout.SOUTH);

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

    private JPanel criarAreaConteudo() {
        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(Tema.FUNDO);

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBackground(Tema.FUNDO);
        formulario.setBorder(new EmptyBorder(20, 28, 20, 28));

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

    // ---------- Título ----------

    private JLabel criarTitulo() {
        JLabel titulo = new JLabel("Criar Nova Playlist");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Tema.TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(0, 0, 18, 0));
        return titulo;
    }

    // ---------- Linha principal: Capa | Nome/Descrição/Categoria ----------

    private JPanel criarLinhaPrincipal() {
        JPanel linha = new JPanel(new GridBagLayout());
        linha.setBackground(Tema.FUNDO);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));

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
        coluna.setBackground(Tema.FUNDO);
        coluna.setBorder(new EmptyBorder(0, 0, 0, 36));

        seletorCapa = new SeletorImagem("CAPA", 180);
        seletorCapa.setAlignmentX(Component.LEFT_ALIGNMENT);
        coluna.add(seletorCapa);

        return coluna;
    }

    private JPanel criarColunaDados() {
        JPanel coluna = new JPanel();
        coluna.setLayout(new BoxLayout(coluna, BoxLayout.Y_AXIS));
        coluna.setBackground(Tema.FUNDO);

        campoNome = criarCampoTexto("Digite o nome...");
        coluna.add(criarGrupoCampo("Nome da Playlist", campoNome, 34));

        campoDescricao = new AreaTextoPlaceholder("Digite uma descrição opcional...", 3, 1);
        campoDescricao.setFont(Tema.FONTE_TEXTO);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        campoDescricao.setBorder(new EmptyBorder(8, 10, 8, 10));

        JScrollPane scrollDescricao = new JScrollPane(campoDescricao);
        scrollDescricao.setBorder(BorderFactory.createLineBorder(Tema.BORDA, 1, true));
        coluna.add(criarGrupoCampo("Descrição", scrollDescricao, 96));

        campoCategoria = new JComboBox<>(new String[]{
                "Selecione...", "Rock", "Pop", "Eletrônica", "Lo-Fi", "Treino", "Sertanejo"
        });
        campoCategoria.setFont(Tema.FONTE_TEXTO);
        campoCategoria.setBackground(Tema.BRANCO);
        campoCategoria.setBorder(BorderFactory.createLineBorder(Tema.BORDA, 1, true));
        coluna.add(criarGrupoCampo("Categoria", campoCategoria, 34));

        return coluna;
    }

    // ---------- Seção "Adicionar Músicas" ----------

    private JPanel criarSecaoMusicas() {
        JPanel secao = new JPanel();
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBackground(Tema.FUNDO);
        secao.setAlignmentX(Component.LEFT_ALIGNMENT);
        secao.setBorder(new EmptyBorder(8, 0, 18, 0));

        JLabel rotulo = criarRotulo("Adicionar Músicas (Opcional nesta etapa)");
        rotulo.setFont(Tema.FONTE_SUBTITULO);
        rotulo.setBorder(new EmptyBorder(0, 0, 8, 0));

        JButton botaoAdicionar = new JButton("+  Buscar e Adicionar");
        botaoAdicionar.setFont(Tema.FONTE_TEXTO);
        botaoAdicionar.setForeground(Tema.TEXTO);
        botaoAdicionar.setBackground(Tema.BRANCO);
        botaoAdicionar.setFocusPainted(false);
        botaoAdicionar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA, 1, true),
                new EmptyBorder(7, 14, 7, 14)));
        botaoAdicionar.setAlignmentX(Component.LEFT_ALIGNMENT);
        // TODO: abrir busca de músicas assim que houver um catálogo/serviço de músicas

        secao.add(rotulo);
        secao.add(botaoAdicionar);
        return secao;
    }

    // ---------- Rodapé de botões (Cancelar / Salvar) ----------

    private JPanel criarRodapeBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painel.setBackground(Tema.FUNDO);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setFont(Tema.FONTE_SUBTITULO);
        botaoCancelar.setBackground(Tema.FUNDO);
        botaoCancelar.setForeground(Tema.TEXTO);
        botaoCancelar.setFocusPainted(false);
        botaoCancelar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA, 1, true),
                new EmptyBorder(8, 18, 8, 18)));
        botaoCancelar.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true);
        });

        JButton botaoSalvar = new JButton("SALVAR");
        botaoSalvar.setFont(Tema.FONTE_SUBTITULO);
        botaoSalvar.setBackground(Tema.AZUL);
        botaoSalvar.setForeground(Tema.BRANCO);
        botaoSalvar.setFocusPainted(false);
        botaoSalvar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.AZUL, 1, true),
                new EmptyBorder(8, 18, 8, 18)));
        botaoSalvar.addActionListener(e -> salvarPlaylist());

        painel.add(botaoCancelar);
        painel.add(botaoSalvar);
        return painel;
    }

    private void salvarPlaylist() {
        if (campoNome.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Informe o nome da playlist antes de salvar.",
                    "Campo obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO: persistir a nova playlist assim que houver um serviço de playlists
        JOptionPane.showMessageDialog(this,
                "Playlist \"" + campoNome.getText() + "\" salva com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new TelaPrincipal().setVisible(true);
    }

    // ---------- Utilitários de campo ----------

    private JLabel criarRotulo(String texto) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setFont(Tema.FONTE_TEXTO);
        rotulo.setForeground(Tema.TEXTO);
        rotulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return rotulo;
    }

    private CampoTextoPlaceholder criarCampoTexto(String placeholder) {
        CampoTextoPlaceholder campo = new CampoTextoPlaceholder(placeholder);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setBackground(Tema.BRANCO);
        campo.setForeground(Tema.TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA, 1, true),
                new EmptyBorder(8, 10, 8, 10)));
        return campo;
    }

    private JPanel criarGrupoCampo(String rotuloTexto, JComponent campo, int alturaMaxima) {
        JPanel grupo = new JPanel();
        grupo.setLayout(new BoxLayout(grupo, BoxLayout.Y_AXIS));
        grupo.setBackground(Tema.FUNDO);
        grupo.setAlignmentX(Component.LEFT_ALIGNMENT);
        grupo.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel rotulo = criarRotulo(rotuloTexto);
        rotulo.setBorder(new EmptyBorder(0, 0, 5, 0));

        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, alturaMaxima));

        grupo.add(rotulo);
        grupo.add(campo);
        return grupo;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCriarPlaylist().setVisible(true));
    }
}
