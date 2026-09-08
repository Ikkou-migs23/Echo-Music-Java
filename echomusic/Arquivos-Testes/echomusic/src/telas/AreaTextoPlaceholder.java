package telas;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextArea;

/**
 * {@link JTextArea} com placeholder real (não editável), desenhado por
 * cima da área apenas quando ela está vazia. Usada em campos de
 * descrição de várias linhas.
 */
public class AreaTextoPlaceholder extends JTextArea {

    private final String placeholder;

    public AreaTextoPlaceholder(String placeholder, int linhas, int colunas) {
        super(linhas, colunas);
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!getText().isEmpty() || placeholder == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Tema.PLACEHOLDER);
        g2.setFont(getFont());

        FontMetrics fm = g2.getFontMetrics();
        int x = getInsets().left;
        int y = getInsets().top + fm.getAscent();
        g2.drawString(placeholder, x, y);
        g2.dispose();
    }
}
