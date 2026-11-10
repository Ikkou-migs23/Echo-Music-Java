package Tela;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Negocio.Playlist;


public class PainelBiblioteca extends JPanel {

    private static final int COLUNAS_GRADE = 3;
    private static final int ESPACAMENTO_GRADE = 18;

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
        titulo.setForeground(Tema.TEXTO);

        JButton criar = new JButton("Criar Playlist");
        criar.setFont(Tema.FONTE_SUBTITULO);
        criar.setForeground(Tema.BRANCO);
        criar.setFocusPainted(false);
        criar.setBorderPainted(false);
        criar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        criar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tema.aplicarHover(criar, Tema.ROXO, Tema.ROXO_ESCURO);
        criar.addActionListener(e -> {
            if (aoCriarPlaylist != null) aoCriarPlaylist.run();
        });

        painel.add(titulo, BorderLayout.WEST);
        painel.add(criar, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarLista() {
        JPanel painel = new JPanel(new BorderLayout(0, 14));
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        painel.add(criarFiltros(), BorderLayout.NORTH);
        painel.add(criarCards(), BorderLayout.CENTER);

        return painel;
    }
// --- filtros ---
    private JPanel criarFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filtros.setBackground(Tema.FUNDO);

        filtros.add(criarRotuloFiltro("Filtro:"));
        filtros.add(criarComboBox(new String[]{"Categoria", "Rock", "Pop", "Eletrônica"}));

        filtros.add(Box.createHorizontalStrut(10));
        filtros.add(criarRotuloFiltro("Ordenar por:"));
        filtros.add(criarComboBox(new String[]{"Nome", "Quantidade", "Data"}));

        return filtros;
    }

    private JLabel criarRotuloFiltro(String texto) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setFont(Tema.FONTE_TEXTO_PEQUENA);
        rotulo.setForeground(Tema.TEXTO_SECUNDARIO);
        return rotulo;
    }


    private JComboBox<String> criarComboBox(String[] itens) {
        JComboBox<String> combo = new JComboBox<>(itens);
        combo.setFont(Tema.FONTE_TEXTO_PEQUENA);
        combo.setBackground(Tema.BRANCO);
        combo.setForeground(Tema.TEXTO);
        combo.setFocusable(false);
        combo.setPreferredSize(new Dimension(132, 28));
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA, 1, true),
                BorderFactory.createEmptyBorder(2, 8, 2, 4)
        ));
        return combo;
    }
// --- grade ---
    private JPanel criarCards() {
        JPanel cards = new JPanel(new GridLayout(0, COLUNAS_GRADE, ESPACAMENTO_GRADE, ESPACAMENTO_GRADE));
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
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Tema.FUNDO);
        wrapper.add(cards, BorderLayout.NORTH);
        return wrapper;
    }
}
