package Tela;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Negocio.Playlist;
import Negocio.RepositorioPlaylists;


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
                RepositorioPlaylists.listar(),
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
        new TelaPlaylist(playlist).setVisible(true);
    }

    private void abrirCriarPlaylist() {
        dispose();
        new TelaCriarPlaylist().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
