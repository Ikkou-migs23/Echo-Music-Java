package Tela;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class BarraTitulo extends JPanel {

    private static final int ALTURA = 42;
    private static final int LADO_BOTAO = 31;

    public BarraTitulo(JFrame janela) {
        setLayout(new BorderLayout());
        setBackground(Tema.FUNDO);
        setPreferredSize(new Dimension(0, ALTURA));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Tema.BORDA));

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));
        direita.setBackground(Tema.FUNDO);
        direita.add(criarBotaoFechar(janela));
        add(direita, BorderLayout.EAST);

        habilitarArraste(janela);
    }

    private JButton criarBotaoFechar(JFrame janela) {
        JButton fechar = new JButton("X");
        fechar.setFont(Tema.FONTE_SUBTITULO);
        fechar.setBackground(Tema.FUNDO);
        fechar.setForeground(Tema.ROXO);
        fechar.setPreferredSize(new Dimension(LADO_BOTAO + 20, LADO_BOTAO));
        fechar.setHorizontalAlignment(SwingConstants.CENTER);
        fechar.setFocusPainted(false);
        fechar.setBorderPainted(false);
        fechar.setOpaque(true);
        fechar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        fechar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	fechar.setForeground(Tema.VERMELHO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	fechar.setForeground(Tema.ROXO);
            }
        });
        fechar.addActionListener(e ->
                janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING)));

        return fechar;
    }

    private void habilitarArraste(JFrame janela) {
        final Point[] origem = new Point[1];

        MouseAdapter pressionar = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origem[0] = e.getPoint();
            }
        };

        MouseMotionAdapter arrastar = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (origem[0] == null) return;
                Point telaAtual = janela.getLocation();
                janela.setLocation(
                        telaAtual.x + e.getX() - origem[0].x,
                        telaAtual.y + e.getY() - origem[0].y
                );
            }
        };

        addMouseListener(pressionar);
        addMouseMotionListener(arrastar);
    }
}
