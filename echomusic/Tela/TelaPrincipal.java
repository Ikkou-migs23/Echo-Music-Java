package Tela;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Negocio.Playlist;


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
                carregarPlaylists(),
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

    private List<Playlist> carregarPlaylists() {
        try {
            return Playlist.listarPorUsuario(Negocio.Sessao.getIdUsuario());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Não foi possível carregar suas playlists:\n" + ex.getMessage(),
                    "Erro de banco de dados",
                    JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
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
