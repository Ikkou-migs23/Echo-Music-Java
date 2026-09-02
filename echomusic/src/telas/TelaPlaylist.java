package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

import negocio.Musica;

/**
 * Tela de uma playlist específica, aberta ao clicar em um CardPlaylist na
 * TelaPrincipal. Mostra: capa, nome, botão play/pause, botão editar, botão
 * voltar, e a lista de músicas (clicáveis para tocar).
 */
public class TelaPlaylist extends JFrame {

    private final String nome;
    private final Color corCapa;
    private final String textoCapa;
    private final List<Musica> musicas;

    private JButton botaoPlayPausePlaylist;
    private boolean tocando = false;

    public TelaPlaylist(String nome, Color corCapa, String textoCapa, List<Musica> musicas) {
        this.nome = nome;
        this.corCapa = corCapa;
        this.textoCapa = textoCapa;
        this.musicas = musicas;

        setTitle("Echo Music - " + nome);
        setSize(1080, 700);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Tema.FUNDO);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO);
        raiz.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Tema.FUNDO);

        BarraLateral barraLateral = new BarraLateral("Playlists");
        barraLateral.aoClicar(this::navegar);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(Tema.FUNDO);
        conteudo.add(criarTopo(), BorderLayout.NORTH);
        conteudo.add(criarListaMusicas(), BorderLayout.CENTER);

        principal.add(barraLateral, BorderLayout.WEST);
        principal.add(conteudo, BorderLayout.CENTER);
        principal.add(new BarraReprodutor(), BorderLayout.SOUTH);
        
        raiz.add(new BarraTitulo(this), BorderLayout.NORTH);
        raiz.add(principal, BorderLayout.CENTER);

        add(raiz);
    }

    // ---------- navegação pelo menu lateral ----------
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

    // ---------- topo: voltar + cabeçalho da playlist ----------
    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Tema.FUNDO);
        topo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        topo.add(criarBotaoVoltar(), BorderLayout.NORTH);
        topo.add(criarCabecalhoPlaylist(), BorderLayout.CENTER);
        return topo;
    }

    private JPanel criarBotaoVoltar() {
        JButton voltar = new JButton("←  Voltar");
        voltar.setFont(Tema.FONTE_TEXTO);
        voltar.setFocusPainted(false);
        voltar.setBackground(Tema.BRANCO);
        voltar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        voltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        voltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true);
        });

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        painel.add(voltar);
        return painel;
    }

    private JPanel criarCabecalhoPlaylist() {
        JPanel cabecalho = new JPanel(new BorderLayout(18, 0));
        cabecalho.setBackground(Tema.FUNDO);

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
        texto.setForeground(Tema.BRANCO);
        texto.setFont(new Font("Arial", Font.BOLD, 16));
        capa.add(texto);
        return capa;
    }

    private JPanel criarInfoENomeComBotoes() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Tema.FUNDO);

        JLabel nomeLabel = new JLabel(nome);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel quantidadeLabel = new JLabel(musicas.size() + " músicas");
        quantidadeLabel.setFont(Tema.FONTE_TEXTO);
        quantidadeLabel.setForeground(Tema.TEXTO_SECUNDARIO);
        quantidadeLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 12, 0));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(Tema.FUNDO);
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
        painel.setBackground(Tema.FUNDO);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        botaoPlayPausePlaylist = new JButton("▶  Tocar");
        botaoPlayPausePlaylist.setBackground(Tema.AZUL);
        botaoPlayPausePlaylist.setForeground(Tema.BRANCO);
        botaoPlayPausePlaylist.setFocusPainted(false);
        botaoPlayPausePlaylist.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        botaoPlayPausePlaylist.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton editar = new JButton("✎  Editar");
        editar.setBackground(Tema.BRANCO);
        editar.setFocusPainted(false);
        editar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA),
                BorderFactory.createEmptyBorder(7, 16, 7, 16)
        ));
        editar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // TODO: abrir tela/diálogo de edição dos dados da playlist (nome, descrição, capa)

        painel.add(botaoPlayPausePlaylist);
        painel.add(editar);
        return painel;
    }

    // ---------- lista de músicas ----------
    private JScrollPane criarListaMusicas() {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(Tema.BRANCO);
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
        linha.setBackground(Tema.BRANCO);
        linha.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Tema.BORDA),
                BorderFactory.createEmptyBorder(9, 8, 9, 8)
        ));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        linha.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel numero = new JLabel(String.valueOf(indice + 1));
        numero.setForeground(Tema.TEXTO_SECUNDARIO);
        numero.setPreferredSize(new Dimension(24, 20));

        JLabel nomeMusica = new JLabel(musica.getNome());
        nomeMusica.setFont(new Font("Arial", Font.PLAIN, 13));

        JLabel duracao = new JLabel(musica.getDuracao());
        duracao.setForeground(Tema.TEXTO_SECUNDARIO);
        duracao.setFont(Tema.FONTE_TEXTO);

        linha.add(numero, BorderLayout.WEST);
        linha.add(nomeMusica, BorderLayout.CENTER);
        linha.add(duracao, BorderLayout.EAST);

        return linha;
    }
}
