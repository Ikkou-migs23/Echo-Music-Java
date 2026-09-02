package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import model.Musica;

/**
 * Tela de uma playlist específica (JFrame próprio, aberta ao clicar num
 * CardPlaylist na TelaPrincipal). Mostra: capa, nome, botão play/pause,
 * botão editar, botão voltar, e a lista de músicas (clicáveis para tocar).
 */
public class TelaPlaylist extends JFrame {

    private static final Color FUNDO = new Color(247, 248, 250);
    private static final Color AZUL = new Color(54, 117, 205);
    private static final Color BORDA = new Color(220, 223, 228);
    private static final Color FUNDO_HOVER = new Color(238, 241, 246);
    private static final Color FUNDO_TOCANDO = new Color(222, 233, 250);

    private final String nome;
    private final Color corCapa;
    private final String textoCapa;
    private final List<Musica> musicas;

    private JButton botaoPlayPausePlaylist;
    private boolean tocando = false;

    private JLabel labelFaixaAtual;
    private JPanel linhaSelecionada;
    private int indiceSelecionado = -1;

    public TelaPlaylist(String nome, Color corCapa, String textoCapa, List<Musica> musicas) {
        this.nome = nome;
        this.corCapa = corCapa;
        this.textoCapa = textoCapa;
        this.musicas = musicas;

        setTitle("Echo Music - " + nome);
        setSize(900, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FUNDO);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(FUNDO);

        BarraLateral barraLateral = new BarraLateral("Playlists");
        barraLateral.aoClicar(this::aoClicarMenu);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(FUNDO);
        conteudo.add(criarTopo(), BorderLayout.NORTH);
        conteudo.add(criarListaMusicas(), BorderLayout.CENTER);

        principal.add(barraLateral, BorderLayout.WEST);
        principal.add(conteudo, BorderLayout.CENTER);
        principal.add(criarBarraFaixaAtual(), BorderLayout.SOUTH);

        add(principal);
    }

    // navegação pelo menu lateral (Home, Playlists, Perfil, Sobre)
    private void aoClicarMenu(String tela) {
        switch (tela) {
            case "Home":
            case "Playlists":
                dispose();
                TelaPrincipal.abrir();
                break;
            case "Perfil":
                // TODO: dispose(); new TelaPerfil().setVisible(true);
                break;
            case "Sobre":
                // TODO: dispose(); new TelaSobre().setVisible(true);
                break;
        }
    }

    // ---------- topo: voltar + cabeçalho da playlist ----------
    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(FUNDO);
        topo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        topo.add(criarBotaoVoltar(), BorderLayout.NORTH);
        topo.add(criarCabecalhoPlaylist(), BorderLayout.CENTER);
        return topo;
    }

    private JPanel criarBotaoVoltar() {
        JButton voltar = new JButton("←  Voltar");
        voltar.setFont(new Font("Arial", Font.PLAIN, 12));
        voltar.setFocusPainted(false);
        voltar.setBackground(Color.WHITE);
        voltar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        voltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        voltar.addActionListener(e -> {
            dispose();
            TelaPrincipal.abrir();
        });

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        painel.add(voltar);
        return painel;
    }

    private JPanel criarCabecalhoPlaylist() {
        JPanel cabecalho = new JPanel(new BorderLayout(18, 0));
        cabecalho.setBackground(FUNDO);

        cabecalho.add(criarCapaGrande(), BorderLayout.WEST);
        cabecalho.add(criarInfoENomeComBotoes(), BorderLayout.CENTER);
        return cabecalho;
    }

    private JPanel criarCapaGrande() {
        JPanel capa = new JPanel(new GridBagLayout());
        capa.setPreferredSize(new Dimension(120, 120));
        capa.setBackground(corCapa);

        JLabel texto = new JLabel(
                "<html><center>" + textoCapa.replace("\n", "<br>") + "</center></html>"
        );
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Arial", Font.BOLD, 16));
        capa.add(texto);
        return capa;
    }

    private JPanel criarInfoENomeComBotoes() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);

        JLabel nomeLabel = new JLabel(nome);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel quantidadeLabel = new JLabel(musicas.size() + " músicas");
        quantidadeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        quantidadeLabel.setForeground(Color.GRAY);
        quantidadeLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 12, 0));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(FUNDO);
        nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quantidadeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textos.add(nomeLabel);
        textos.add(quantidadeLabel);
        textos.add(criarBotoesAcao());

        painel.add(textos, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarBotoesAcao() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painel.setBackground(FUNDO);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botaoPlayPausePlaylist = new JButton("▶  Tocar");
        botaoPlayPausePlaylist.setBackground(AZUL);
        botaoPlayPausePlaylist.setForeground(Color.WHITE);
        botaoPlayPausePlaylist.setFocusPainted(false);
        botaoPlayPausePlaylist.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        botaoPlayPausePlaylist.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoPlayPausePlaylist.addActionListener(e -> alternarPlayPause());

        JButton editar = new JButton("✎  Editar");
        editar.setBackground(Color.WHITE);
        editar.setFocusPainted(false);
        editar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA),
                BorderFactory.createEmptyBorder(7, 16, 7, 16)
        ));
        editar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // TODO: abrir tela/diálogo de edição da playlist

        painel.add(botaoPlayPausePlaylist);
        painel.add(editar);
        return painel;
    }

    private void alternarPlayPause() {
        tocando = !tocando;
        botaoPlayPausePlaylist.setText(tocando ? "❚❚  Pausar" : "▶  Tocar");
        if (tocando && indiceSelecionado == -1 && !musicas.isEmpty()) {
            selecionarMusica(0, criarLinha(0, musicas.get(0)));
        }
        atualizarLabelFaixaAtual();
    }

    // ---------- lista de músicas ----------
    private JScrollPane criarListaMusicas() {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(Color.WHITE);
        lista.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        for (int i = 0; i < musicas.size(); i++) {
            lista.add(criarLinha(i, musicas.get(i)));
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    private JPanel criarLinha(int indice, Musica musica) {
        JPanel linha = new JPanel(new BorderLayout(10, 0));
        linha.setBackground(Color.WHITE);
        linha.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA),
                BorderFactory.createEmptyBorder(9, 8, 9, 8)
        ));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        linha.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel numero = new JLabel(String.valueOf(indice + 1));
        numero.setForeground(Color.GRAY);
        numero.setPreferredSize(new Dimension(24, 20));

        JLabel nomeMusica = new JLabel(musica.getNome());
        nomeMusica.setFont(new Font("Arial", Font.PLAIN, 13));

        JLabel duracao = new JLabel(musica.getDuracao());
        duracao.setForeground(Color.GRAY);
        duracao.setFont(new Font("Arial", Font.PLAIN, 12));

        linha.add(numero, BorderLayout.WEST);
        linha.add(nomeMusica, BorderLayout.CENTER);
        linha.add(duracao, BorderLayout.EAST);

        linha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarMusica(indice, linha);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (indice != indiceSelecionado) linha.setBackground(FUNDO_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (indice != indiceSelecionado) linha.setBackground(Color.WHITE);
            }
        });

        return linha;
    }

    private void selecionarMusica(int indice, JPanel linha) {
        if (linhaSelecionada != null) linhaSelecionada.setBackground(Color.WHITE);
        indiceSelecionado = indice;
        linhaSelecionada = linha;
        linha.setBackground(FUNDO_TOCANDO);

        tocando = true;
        botaoPlayPausePlaylist.setText("❚❚  Pausar");
        atualizarLabelFaixaAtual();
    }

    // ---------- barra inferior com a faixa atual ----------
    private JPanel criarBarraFaixaAtual() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setPreferredSize(new Dimension(0, 50));
        barra.setBackground(Color.WHITE);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDA));

        JPanel musicaPainel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        musicaPainel.setBackground(Color.WHITE);

        JPanel capa = new JPanel();
        capa.setPreferredSize(new Dimension(34, 34));
        capa.setBackground(corCapa);

        labelFaixaAtual = new JLabel("Nenhuma música selecionada");
        labelFaixaAtual.setFont(new Font("Arial", Font.PLAIN, 12));

        musicaPainel.add(capa);
        musicaPainel.add(labelFaixaAtual);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 13));
        controles.setBackground(Color.WHITE);
        for (String texto : new String[]{"|<", ">", "||", ">|"}) {
            JButton botao = new JButton(texto);
            botao.setPreferredSize(new Dimension(32, 23));
            botao.setFocusPainted(false);
            botao.setMargin(new Insets(0, 0, 0, 0));
            controles.add(botao);
        }

        barra.add(musicaPainel, BorderLayout.WEST);
        barra.add(controles, BorderLayout.CENTER);
        return barra;
    }

    private void atualizarLabelFaixaAtual() {
        if (indiceSelecionado == -1) {
            labelFaixaAtual.setText("Nenhuma música selecionada");
        } else {
            String nomeMusica = musicas.get(indiceSelecionado).getNome();
            labelFaixaAtual.setText((tocando ? "▶ " : "❚❚ ") + nomeMusica);
        }
    }
}