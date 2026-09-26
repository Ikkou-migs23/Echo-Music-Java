package Tela;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class BarraSuperior extends JPanel {

    public BarraSuperior() {
        this(null);
    }

    public BarraSuperior(JComponent conteudoDireita) {
        setLayout(new BorderLayout());
        setBackground(Tema.BRANCO);
        setPreferredSize(new Dimension(0, 45));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Tema.BORDA));

        add(criarCampoBusca(), BorderLayout.WEST);
        if (conteudoDireita != null) {
            add(conteudoDireita, BorderLayout.EAST);
        }
    }

    private JPanel criarCampoBusca() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        painel.setBackground(Tema.BRANCO);

        CampoTextoPlaceholder busca = new CampoTextoPlaceholder("Buscar Música...");
        busca.setPreferredSize(new Dimension(270, 27));
        busca.setForeground(Tema.TEXTO);

        painel.add(busca);
        return painel;
    }
}
