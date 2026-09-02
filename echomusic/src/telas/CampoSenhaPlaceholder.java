package telas;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;

/**
 * {@link JPasswordField} com placeholder real (não editável), desenhado
 * por cima do campo apenas quando ele está vazio.
 */
public class CampoSenhaPlaceholder extends JPasswordField {

    private final String placeholder;

    public CampoSenhaPlaceholder(String placeholder) {
        super();
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getPassword().length != 0 || placeholder == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Tema.PLACEHOLDER);
        g2.setFont(getFont());

        FontMetrics fm = g2.getFontMetrics();
        int x = getInsets().left;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(placeholder, x, y);
        g2.dispose();
    }
}
