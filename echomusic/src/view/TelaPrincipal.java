package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal {

    private static final Color FUNDO = new Color(247, 248, 250);
    private static final Color AZUL = new Color(54, 117, 205);
    private static final Color BORDA = new Color(220, 223, 228);

    public static void main(String[] args) {
        JFrame janela = new JFrame("Echo Music - Sua Biblioteca");
        janela.setSize(900, 600);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(FUNDO);
        principal.add(new BarraLateral("Playlists"), BorderLayout.WEST);
        principal.add(criarConteudo(), BorderLayout.CENTER);
        principal.add(criarPlayer(), BorderLayout.SOUTH);

        janela.add(principal);
        janela.setVisible(true);
    }

    private static JPanel criarConteudo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);
        painel.add(criarBarraSuperior(), BorderLayout.NORTH);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        conteudo.add(criarCabecalho(), BorderLayout.NORTH);
        conteudo.add(criarLista(), BorderLayout.CENTER);

        painel.add(conteudo);
        return painel;
    }

    private static JPanel criarBarraSuperior() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barra.setBackground(Color.WHITE);
        barra.setPreferredSize(new Dimension(0, 45));
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA));

        JTextField busca = new JTextField("Buscar Música...");
        busca.setPreferredSize(new Dimension(270, 27));

        barra.add(busca);
        return barra;
    }

    private static JPanel criarCabecalho() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);

        JLabel titulo = new JLabel("Sua Biblioteca");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton criar = new JButton("Criar Nova Playlist");
        criar.setBackground(AZUL);
        criar.setForeground(Color.WHITE);
        criar.setFocusPainted(false);

        painel.add(titulo, BorderLayout.WEST);
        painel.add(criar, BorderLayout.EAST);

        return painel;
    }

    private static JPanel criarLista() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        painel.add(criarFiltros(), BorderLayout.NORTH);
        painel.add(criarCards(), BorderLayout.CENTER);

        return painel;
    }

    private static JPanel criarFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        filtros.setBackground(FUNDO);

        filtros.add(new JLabel("Filtro:"));
        filtros.add(new JComboBox<>(
                new String[]{"Categoria", "Rock", "Pop", "Eletrônica"}
        ));

        filtros.add(Box.createHorizontalStrut(8));
        filtros.add(new JLabel("Ordenar por:"));
        filtros.add(new JComboBox<>(
                new String[]{"Nome", "Quantidade", "Data"}
        ));

        return filtros;
    }

    private static JPanel criarCards() {
        JPanel cards = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cards.setBackground(FUNDO);

        cards.add(criarPlaylist(
                "Rock 90s", "50 músicas",
                new Color(65, 35, 35), "ROCK\n90s"
        ));

        cards.add(criarPlaylist(
                "Treino Pesado", "32 músicas",
                new Color(55, 55, 55), "TREINO"
        ));

        cards.add(criarPlaylist(
                "Estudo Lo-Fi", "105 músicas",
                new Color(40, 50, 65), "LO-FI"
        ));

        cards.add(criarPlaylist(
                "Minha Vibe", "12 músicas",
                new Color(70, 25, 75), "VIBE"
        ));

        return cards;
    }

    private static JPanel criarPlaylist(
            String nome, String quantidade,
            Color cor, String textoCapa) {

        JPanel card = new JPanel(new BorderLayout(8, 0));
        card.setPreferredSize(new Dimension(245, 85));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA),
                BorderFactory.createEmptyBorder(7, 7, 7, 7)
        ));

        JPanel capa = new JPanel(new GridBagLayout());
        capa.setPreferredSize(new Dimension(65, 65));
        capa.setBackground(cor);

        JLabel texto = new JLabel(
                "<html><center>" +
                textoCapa.replace("\n", "<br>") +
                "</center></html>"
        );
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Arial", Font.BOLD, 10));
        capa.add(texto);

        JPanel informacoes = new JPanel();
        informacoes.setLayout(new BoxLayout(informacoes, BoxLayout.Y_AXIS));
        informacoes.setBackground(Color.WHITE);

        JLabel nomeLabel = new JLabel(nome);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel quantidadeLabel = new JLabel(quantidade);
        quantidadeLabel.setFont(new Font("Arial", Font.PLAIN, 9));
        quantidadeLabel.setForeground(Color.GRAY);

        informacoes.add(Box.createVerticalGlue());
        informacoes.add(nomeLabel);
        informacoes.add(Box.createVerticalStrut(3));
        informacoes.add(quantidadeLabel);
        informacoes.add(Box.createVerticalGlue());

        JButton menu = new JButton("...");
        menu.setPreferredSize(new Dimension(25, 25));
        menu.setFocusPainted(false);
        menu.setMargin(new Insets(0, 0, 0, 0));

        card.add(capa, BorderLayout.WEST);
        card.add(informacoes, BorderLayout.CENTER);
        card.add(menu, BorderLayout.EAST);

        return card;
    }

    private static JPanel criarPlayer() {
        JPanel player = new JPanel(new BorderLayout());
        player.setPreferredSize(new Dimension(0, 50));
        player.setBackground(Color.WHITE);
        player.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDA));

        JPanel musica = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        musica.setBackground(Color.WHITE);

        JPanel capa = new JPanel();
        capa.setPreferredSize(new Dimension(34, 34));
        capa.setBackground(new Color(235, 235, 235));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(Color.WHITE);

        textos.add(new JLabel("Nenhuma música selecionada"));
        textos.add(new JLabel("--:-- / --:--"));

        musica.add(capa);
        musica.add(textos);

        player.add(musica, BorderLayout.WEST);
        player.add(criarControles(), BorderLayout.CENTER);
        player.add(criarVolume(), BorderLayout.EAST);

        return player;
    }

    private static JPanel criarControles() {
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 13));
        controles.setBackground(Color.WHITE);

        for (String texto : new String[]{"|<", ">", "||", ">|"}) {
            JButton botao = new JButton(texto);
            botao.setPreferredSize(new Dimension(32, 23));
            botao.setFocusPainted(false);
            botao.setMargin(new Insets(0, 0, 0, 0));
            controles.add(botao);
        }

        return controles;
    }

    private static JPanel criarVolume() {
        JPanel volume = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 13));
        volume.setBackground(Color.WHITE);

        volume.add(new JLabel("Volume"));

        JSlider slider = new JSlider(0, 100, 55);
        slider.setPreferredSize(new Dimension(100, 18));
        volume.add(slider);

        JButton lista = new JButton("=");
        lista.setPreferredSize(new Dimension(25, 23));
        lista.setFocusPainted(false);
        volume.add(lista);

        return volume;
    }
}
