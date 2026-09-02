package view;

import java.awt.*;
import javax.swing.*;

/**
 * Barra inferior do player (música atual, controles e volume).
 * Extraído dos métodos criarPlayer(), criarControles() e criarVolume()
 * da TelaPrincipal original.
 */
public class BarraReprodutor extends JPanel {

    private static final Color BORDA = new Color(220, 223, 228);

    public BarraReprodutor() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 50));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDA));

        add(criarMusica(), BorderLayout.WEST);
        add(criarControles(), BorderLayout.CENTER);
        add(criarVolume(), BorderLayout.EAST);
    }

    private JPanel criarMusica() {
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
        return musica;
    }

    private JPanel criarControles() {
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

    private JPanel criarVolume() {
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