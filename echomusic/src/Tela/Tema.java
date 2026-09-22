package Tela;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


public final class Tema {

    private Tema() {
    }
// --- roxos ---
    public static final Color ROXO = new Color(105, 65, 180);

    public static final Color ROXO_ESCURO = new Color(82, 48, 150);

    public static final Color ROXO_CLARO = new Color(228, 219, 245);

    public static final Color ROXO_MUITO_CLARO = new Color(246, 243, 251);
// --- vermelhos ---
    public static final Color VERMELHO = new Color(224, 62, 76);

    public static final Color VERMELHO_ESCURO = new Color(196, 42, 56);
// --- fundos ---
    public static final Color BRANCO = Color.WHITE;

    public static final Color FUNDO = new Color(248, 247, 250);

    public static final Color FUNDO_HOVER = ROXO_MUITO_CLARO;

    public static final Color FUNDO_SELECIONADO = ROXO_CLARO;
// --- textos ---
    public static final Color TEXTO = new Color(45, 45, 45);
    public static final Color TEXTO_SECUNDARIO = new Color(120, 120, 120);

    public static final Color TEXTO_CLARO = Color.WHITE;
    public static final Color PLACEHOLDER = new Color(160, 163, 168);
// --- bordas ---
    public static final Color BORDA = new Color(226, 221, 233);
// --- fontes ---
    public static final Font FONTE_LOGO = new Font("Arial", Font.BOLD, 16);
    public static final Font FONTE_LOGO_GRANDE = new Font("Arial", Font.BOLD, 30);
    public static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 18);
    public static final Font FONTE_TITULO_GRANDE = new Font("Arial", Font.BOLD, 24);
    public static final Font FONTE_SUBTITULO = new Font("Arial", Font.BOLD, 12);
    public static final Font FONTE_TEXTO = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONTE_TEXTO_PEQUENA = new Font("Arial", Font.PLAIN, 10);
    public static final Font FONTE_CARD_CAPA = new Font("Arial", Font.BOLD, 10);
    public static final Font FONTE_CARD_NOME = new Font("Arial", Font.BOLD, 11);
    public static final Font FONTE_CARD_QUANTIDADE = new Font("Arial", Font.PLAIN, 9);


    public static void aplicarHover(JButton botao, Color corNormal, Color corHover) {
        botao.setOpaque(true);
        botao.setBackground(corNormal);
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(corHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(corNormal);
            }
        });
    }
}
