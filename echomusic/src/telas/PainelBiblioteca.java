package telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import negocio.Playlist;

/**
 * Conteúdo principal da "Sua Biblioteca" (barra de busca, cabeçalho + botão
 * criar, filtros e grade de cards). Recebe a lista de playlists e o que
 * fazer quando o usuário abre uma delas ou pede para criar uma nova.
 */
public class PainelBiblioteca extends JPanel {

    private final List<Playlist> playlists;
    private final Consumer<Playlist> aoAbrirPlaylist;
    private final Runnable aoCriarPlaylist;

    public PainelBiblioteca(List<Playlist> playlists,
                             Consumer<Playlist> aoAbrirPlaylist,
                             Runnable aoCriarPlaylist) {
        this.playlists = playlists;
        this.aoAbrirPlaylist = aoAbrirPlaylist;
        this.aoCriarPlaylist = aoCriarPlaylist;

        setLayout(new BorderLayout());
        setBackground(Tema.FUNDO);

        add(new BarraSuperior(), BorderLayout.NORTH);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(Tema.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        conteudo.add(criarCabecalho(), BorderLayout.NORTH);
        conteudo.add(criarLista(), BorderLayout.CENTER);

        add(conteudo, BorderLayout.CENTER);
    }

    private JPanel criarCabecalho() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Tema.FUNDO);

        JLabel titulo = new JLabel("Sua Biblioteca");
        titulo.setFont(Tema.FONTE_TITULO);

        JButton criar = new JButton("Criar Nova Playlist");
        criar.setBackground(Tema.AZUL);
        criar.setForeground(Tema.BRANCO);
        criar.setFocusPainted(false);
        criar.addActionListener(e -> {
            if (aoCriarPlaylist != null) aoCriarPlaylist.run();
        });

        painel.add(titulo, BorderLayout.WEST);
        painel.add(criar, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarLista() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        painel.add(criarFiltros(), BorderLayout.NORTH);
        painel.add(criarCards(), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        filtros.setBackground(Tema.FUNDO);

        filtros.add(new JLabel("Filtro:"));
        filtros.add(new JComboBox<>(new String[]{"Categoria", "Rock", "Pop", "Eletrônica"}));

        filtros.add(Box.createHorizontalStrut(8));
        filtros.add(new JLabel("Ordenar por:"));
        filtros.add(new JComboBox<>(new String[]{"Nome", "Quantidade", "Data"}));

        return filtros;
    }

    private JPanel criarCards() {
        JPanel cards = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cards.setBackground(Tema.FUNDO);

        for (Playlist playlist : playlists) {
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
