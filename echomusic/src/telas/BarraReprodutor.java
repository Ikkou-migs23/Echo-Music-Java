package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Barra inferior do player (música atual, controles e volume).
 * Reaproveitada em todas as telas internas para manter o mesmo player
 * fixo na parte de baixo da janela.
 */
public class BarraReprodutor extends JPanel {

    private static final String[] CONTROLES = {"⏮", "▶", "||", "⏭"};

    public BarraReprodutor() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 50));
        setBackground(Tema.BRANCO);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Tema.BORDA));

        add(criarMusica(), BorderLayout.WEST);
        add(criarControles(), BorderLayout.CENTER);
        add(criarVolume(), BorderLayout.EAST);
    }

    private JPanel criarMusica() {
        JPanel musica = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        musica.setBackground(Tema.BRANCO);

        JPanel capa = new JPanel();
        capa.setPreferredSize(new Dimension(34, 34));
        capa.setBackground(new java.awt.Color(235, 235, 235));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(Tema.BRANCO);
        textos.add(new JLabel("Nenhuma música selecionada"));
        textos.add(new JLabel("--:-- / --:--"));

        musica.add(capa);
        musica.add(textos);
        return musica;
    }

    private JPanel criarControles() {
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 13));
        controles.setBackground(Tema.BRANCO);

        for (String texto : CONTROLES) {
            JButton botao = new JButton(texto);
            botao.setPreferredSize(new Dimension(32, 23));
            botao.setFocusPainted(false);
            botao.setMargin(new Insets(0, 0, 0, 0));
            botao.setBackground(Tema.ROXO);
            botao.setForeground(Tema.BRANCO);
            controles.add(botao);
        }

        return controles;
    }

    private JPanel criarVolume() {
        JPanel volume = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 13));
        volume.setBackground(Tema.BRANCO);

        volume.add(new JLabel("Volume"));

        JSlider slider = new JSlider(0, 100, 55);
        slider.setBackground(Tema.BRANCO);
        slider.setPreferredSize(new Dimension(100, 18));
        volume.add(slider);

        return volume;
    }
}
