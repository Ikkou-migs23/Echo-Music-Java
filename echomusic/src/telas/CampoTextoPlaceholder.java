package telas;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;

/**
 * {@link JTextField} com suporte a placeholder de verdade: o texto de
 * apoio é apenas desenhado por cima do campo quando ele está vazio,
 * nunca faz parte do conteúdo digitável, e some automaticamente assim
 * que o usuário começa a digitar (comportamento padrão de placeholder).
 */
public class CampoTextoPlaceholder extends JTextField {

    private final String placeholder;

    public CampoTextoPlaceholder(String placeholder) {
        super();
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
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(placeholder, x, y);
        g2.dispose();
    }
}
