package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.MusicaDAO;
import negocio.Musica;
import negocio.Playlist;
import negocio.RepositorioPlaylists;

/**
 * Tela de criação de uma nova playlist (nome, descrição, categoria, capa e
 * músicas). Reaproveita BarraLateral, BarraSuperior e BarraReprodutor para
 * seguir o mesmo layout das demais telas internas.
 *
 * As músicas são adicionadas selecionando arquivos .mp3 reais do
 * computador: o {@link MusicaDAO} copia cada arquivo para a biblioteca
 * local (para a playlist não depender do arquivo original continuar no
 * mesmo lugar) e devolve a Musica já com nome e duração lidos de verdade.
 * A playlist só é enviada para o {@link RepositorioPlaylists} (ainda em
 * memória, sem banco de dados) no momento de salvar.
 */
public class TelaCriarPlaylist extends JFrame {

    private CampoTextoPlaceholder campoNome;
    private AreaTextoPlaceholder campoDescricao;
    private JComboBox<String> campoCategoria;
    private SeletorImagem seletorCapa;

    private final List<Musica> musicasAdicionadas = new ArrayList<>();
    private JPanel painelListaMusicas;

    public TelaCriarPlaylist() {
        setTitle("Echo Music - Criar Nova Playlist");
        setSize(1140, 720);
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
                dispose(); 
                new TelaSobre().setVisible(true);
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
        titulo.setFont(Tema.FONTE_TITULO_GRANDE);
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

        JLabel rotulo = criarRotulo("Adicionar Músicas");
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
        botaoAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoAdicionar.addActionListener(e -> adicionarMusica());

        JButton botaoAbrirPasta = new JButton("Abrir pasta da biblioteca");
        botaoAbrirPasta.setFont(Tema.FONTE_TEXTO_PEQUENA);
        botaoAbrirPasta.setFocusPainted(false);
        botaoAbrirPasta.setBorderPainted(false);
        botaoAbrirPasta.setContentAreaFilled(false);
        botaoAbrirPasta.setForeground(Tema.TEXTO_SECUNDARIO);
        botaoAbrirPasta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoAbrirPasta.addActionListener(e -> abrirPastaBiblioteca());

        JPanel linhaBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        linhaBotoes.setBackground(Tema.FUNDO);
        linhaBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaBotoes.add(botaoAdicionar);
        linhaBotoes.add(botaoAbrirPasta);

        painelListaMusicas = new JPanel();
        painelListaMusicas.setLayout(new BoxLayout(painelListaMusicas, BoxLayout.Y_AXIS));
        painelListaMusicas.setBackground(Tema.FUNDO);
        painelListaMusicas.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelListaMusicas.setBorder(new EmptyBorder(10, 0, 0, 0));

        secao.add(rotulo);
        secao.add(linhaBotoes);
        secao.add(painelListaMusicas);
        return secao;
    }

    /** Abre a pasta da biblioteca local no explorador de arquivos do
     * sistema operacional, só pra confirmar visualmente que os áudios
     * estão sendo salvos no lugar certo. */
    private void abrirPastaBiblioteca() {
        try {
            new MusicaDAO().abrirPastaNoExplorador();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível abrir a pasta: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre o seletor de arquivos do sistema (permitindo escolher vários de
     * uma vez) filtrado para .mp3. Cada arquivo escolhido é copiado para a
     * biblioteca local pelo {@link MusicaDAO}, que devolve a Musica já
     * pronta (nome do arquivo + duração real, nada digitado manualmente).
     */
    private void adicionarMusica() {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Selecionar Música (.mp3)");
        seletor.setMultiSelectionEnabled(true);
        seletor.setFileFilter(new FileNameExtensionFilter("Arquivos MP3", "mp3"));

        if (seletor.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File[] arquivosEscolhidos = seletor.getSelectedFiles();
        if (arquivosEscolhidos.length == 0) return;

        MusicaDAO musicaDAO = new MusicaDAO();
        for (File arquivo : arquivosEscolhidos) {
            try {
                musicasAdicionadas.add(musicaDAO.salvar(arquivo));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Não foi possível importar \"" + arquivo.getName() + "\": " + ex.getMessage(),
                        "Erro ao importar",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        atualizarListaMusicas();
    }

    private void atualizarListaMusicas() {
        painelListaMusicas.removeAll();

        for (int i = 0; i < musicasAdicionadas.size(); i++) {
            Musica musica = musicasAdicionadas.get(i);
            int indice = i;

            JPanel linha = new JPanel(new BorderLayout(8, 0));
            linha.setBackground(Tema.BRANCO);
            linha.setAlignmentX(Component.LEFT_ALIGNMENT);
            linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
            linha.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Tema.BORDA, 1, true),
                    new EmptyBorder(4, 10, 4, 10)));

            JLabel texto = new JLabel(musica.getNome() + "  •  " + musica.getDuracao());
            texto.setFont(Tema.FONTE_TEXTO);

            JButton remover = new JButton("Remover");
            remover.setFont(Tema.FONTE_TEXTO_PEQUENA);
            remover.setFocusPainted(false);
            remover.setBorderPainted(false);
            remover.setContentAreaFilled(false);
            remover.setForeground(Tema.VERMELHO);
            remover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            remover.addActionListener(e -> {
                musicasAdicionadas.remove(indice);
                atualizarListaMusicas();
            });

            linha.add(texto, BorderLayout.CENTER);
            linha.add(remover, BorderLayout.EAST);
            painelListaMusicas.add(linha);
            painelListaMusicas.add(Box.createVerticalStrut(6));
        }

        painelListaMusicas.revalidate();
        painelListaMusicas.repaint();
    }

    // ---------- Rodapé de botões (Cancelar / Salvar) ----------

    private JPanel criarRodapeBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painel.setBackground(Tema.FUNDO);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setFont(Tema.FONTE_SUBTITULO);
        botaoCancelar.setBackground(Tema.VERMELHO);
        botaoCancelar.setForeground(Tema.BRANCO);
        botaoCancelar.setFocusPainted(false);
        botaoCancelar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.FUNDO, 1, true),
                new EmptyBorder(8, 18, 8, 18)));
        botaoCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tema.aplicarHover(botaoCancelar, Tema.VERMELHO, Tema.VERMELHO_ESCURO);
        botaoCancelar.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true);
        });

        JButton botaoSalvar = new JButton("SALVAR");
        botaoSalvar.setFont(Tema.FONTE_SUBTITULO);
        botaoSalvar.setForeground(Tema.BRANCO);
        botaoSalvar.setFocusPainted(false);
        botaoSalvar.setBorder(new EmptyBorder(8, 18, 8, 18));
        botaoSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tema.aplicarHover(botaoSalvar, Tema.ROXO, Tema.ROXO_ESCURO);
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

        String nome = campoNome.getText().trim();
        String textoCapa = nome.length() <= 4 ? nome.toUpperCase() : nome.substring(0, 4).toUpperCase();

        int hash = Math.abs(nome.hashCode());
        Color corCapa = new Color(70 + hash % 120, 40 + (hash / 7) % 90, 110 + (hash / 13) % 110);

        RepositorioPlaylists.adicionar(
                new Playlist(nome, corCapa, textoCapa, new ArrayList<>(musicasAdicionadas)));

        JOptionPane.showMessageDialog(this,
                "Playlist \"" + nome + "\" salva com sucesso!",
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
