package view;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.*;
import model.InfoPlaylist;

/**
 * Conteúdo principal da "Sua Biblioteca" (barra de busca, cabeçalho + botão
 * criar, filtros e grade de cards). Recebe a lista de playlists e o que
 * fazer quando o usuário clica em uma delas.
 */
public class PainelBiblioteca extends JPanel {

    private static final Color FUNDO = new Color(247, 248, 250);
    private static final Color AZUL = new Color(54, 117, 205);
    private static final Color BORDA = new Color(220, 223, 228);

    private final List<InfoPlaylist> playlists;
    private final Consumer<InfoPlaylist> aoAbrirPlaylist;

    public PainelBiblioteca(List<InfoPlaylist> playlists, Consumer<InfoPlaylist> aoAbrirPlaylist) {
        this.playlists = playlists;
        this.aoAbrirPlaylist = aoAbrirPlaylist;

        setLayout(new BorderLayout());
        setBackground(FUNDO);

        add(criarBarraSuperior(), BorderLayout.NORTH);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        conteudo.add(criarCabecalho(), BorderLayout.NORTH);
        conteudo.add(criarLista(), BorderLayout.CENTER);

        add(conteudo, BorderLayout.CENTER);
    }

    private JPanel criarBarraSuperior() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barra.setBackground(Color.WHITE);
        barra.setPreferredSize(new Dimension(0, 45));
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA));

        JTextField busca = new JTextField("Buscar Música...");
        busca.setPreferredSize(new Dimension(270, 27));

        barra.add(busca);
        return barra;
    }

    private JPanel criarCabecalho() {
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

    private JPanel criarLista() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        painel.add(criarFiltros(), BorderLayout.NORTH);
        painel.add(criarCards(), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        filtros.setBackground(FUNDO);

        filtros.add(new JLabel("Filtro:"));
        filtros.add(new JComboBox<>(new String[]{"Categoria", "Rock", "Pop", "Eletrônica"}));

        filtros.add(Box.createHorizontalStrut(8));
        filtros.add(new JLabel("Ordenar por:"));
        filtros.add(new JComboBox<>(new String[]{"Nome", "Quantidade", "Data"}));

        return filtros;
    }

    private JPanel criarCards() {
        JPanel cards = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cards.setBackground(FUNDO);

        for (InfoPlaylist playlist : playlists) {
            String quantidadeTexto = playlist.getMusicas().size() + " músicas";
            cards.add(new CardPlaylist(
                    playlist.getNome(),
                    quantidadeTexto,
                    playlist.getCor(),
                    playlist.getTextoCapa(),
                    () -> aoAbrirPlaylist.accept(playlist)
            ));
        }

        return cards;
    }
}