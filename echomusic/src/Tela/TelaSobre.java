package Tela;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class TelaSobre extends JFrame {

    public TelaSobre() {
        setTitle("Echo Music - Sobre");
        setSize(1140, 720);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO);
        raiz.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Tema.FUNDO);

        BarraLateral barraLateral = new BarraLateral("Sobre");
        barraLateral.aoClicar(this::navegar);

        principal.add(barraLateral, BorderLayout.WEST);
        principal.add(criarConteudo(), BorderLayout.CENTER);
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
            }
        }
    }

    private JPanel criarConteudo() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Tema.FUNDO);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(Tema.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        conteudo.add(criarCabecalho(), BorderLayout.NORTH);
        conteudo.add(criarTextoSobre(), BorderLayout.CENTER);

        painel.add(conteudo, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(Tema.FUNDO);

        JLabel titulo = new JLabel("Sobre o Echo Music");
        titulo.setFont(Tema.FONTE_TITULO);
        titulo.setForeground(Tema.TEXTO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        cabecalho.add(titulo, BorderLayout.WEST);
        return cabecalho;
    }

    private JPanel criarTextoSobre() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Tema.FUNDO);

        painel.add(criarParagrafo(
                "Echo Music é um projeto de estudo desenvolvido em Java com Swing, "
                        + "criado para simular a experiência de um programa de organização de playlist e reprodução de música."));
        painel.add(Box.createVerticalStrut(10));
        painel.add(criarParagrafo("Versão: 1.0.0"));

        return painel;
    }

    private JLabel criarParagrafo(String texto) {
        JLabel label = new JLabel("<html><div style='width:600px;'>" + texto + "</div></html>");
        label.setFont(Tema.FONTE_TEXTO);
        label.setForeground(Tema.TEXTO);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaSobre().setVisible(true));
    }
}
