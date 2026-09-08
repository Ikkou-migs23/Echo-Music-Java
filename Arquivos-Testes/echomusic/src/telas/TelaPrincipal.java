package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import negocio.Musica;
import negocio.Playlist;

/**
 * Tela "Sua Biblioteca" — tela inicial do sistema após o login.
 * Monta a janela a partir dos componentes reutilizáveis (BarraLateral,
 * PainelBiblioteca, BarraReprodutor), sem duplicar a construção da UI.
 */
public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Echo Music - Sua Biblioteca");
        setSize(1140, 720);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO);
        raiz.setBorder(javax.swing.BorderFactory.createLineBorder(Tema.BORDA));

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Tema.FUNDO);

        BarraLateral barraLateral = new BarraLateral("Playlists");
        barraLateral.aoClicar(this::navegar);

        PainelBiblioteca biblioteca = new PainelBiblioteca(
                criarPlaylistsExemplo(),
                this::abrirPlaylist,
                this::abrirCriarPlaylist
        );

        principal.add(barraLateral, BorderLayout.WEST);
        principal.add(biblioteca, BorderLayout.CENTER);
        principal.add(new BarraReprodutor(), BorderLayout.SOUTH);

        raiz.add(new BarraTitulo(this), BorderLayout.NORTH);
        raiz.add(principal, BorderLayout.CENTER);

        add(raiz);
    }

    private void navegar(String tela) {
        switch (tela) {
            case "Home", "Playlists" -> {
                // já estamos na Sua Biblioteca, não faz nada
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

    private void abrirPlaylist(Playlist playlist) {
        dispose();
        new TelaPlaylist(
                playlist.getNome(),
                playlist.getCor(),
                playlist.getTextoCapa(),
                playlist.getMusicas()
        ).setVisible(true);
    }

    private void abrirCriarPlaylist() {
        dispose();
        new TelaCriarPlaylist().setVisible(true);
    }

    private static List<Playlist> criarPlaylistsExemplo() {
        return Arrays.asList(
                new Playlist("Rock 90s", new Color(65, 35, 35), "ROCK\n90s", Arrays.asList(
                        new Musica("Smells Like Teen Spirit", "5:01"),
                        new Musica("Livin' on a Prayer", "4:09"),
                        new Musica("Under the Bridge", "4:24"),
                        new Musica("Wonderwall", "4:18")
                )),
                new Playlist("Treino Pesado", new Color(55, 55, 55), "TREINO", Arrays.asList(
                        new Musica("Till I Collapse", "4:57"),
                        new Musica("Eye of the Tiger", "4:04"),
                        new Musica("Stronger", "5:11")
                )),
                new Playlist("Estudo Lo-Fi", new Color(40, 50, 65), "LO-FI", Arrays.asList(
                        new Musica("Rainy Afternoon", "2:41"),
                        new Musica("Coffee & Focus", "3:02"),
                        new Musica("Late Night Pages", "2:55"),
                        new Musica("Quiet Library", "3:14")
                )),
                new Playlist("Minha Vibe", new Color(70, 25, 75), "VIBE", Arrays.asList(
                        new Musica("Sunset Drive", "3:22"),
                        new Musica("Palm Trees", "3:45")
                ))
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
