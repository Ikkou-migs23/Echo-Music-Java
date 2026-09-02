package telas;

import java.awt.Color;
import java.awt.Font;

/**
 * Paleta de cores e fontes padrão do Echo Music.
 *
 * Centraliza os valores que antes estavam duplicados (e levemente
 * divergentes) em cada tela, usando como referência o design da
 * {@link TelaPrincipal}. Qualquer ajuste visual futuro passa a ser
 * feito em um único lugar.
 */
public final class Tema {

    private Tema() {
        // classe utilitária: não deve ser instanciada
    }

    public static final Color FUNDO = new Color(247, 248, 250);
    public static final Color BRANCO = Color.WHITE;
    public static final Color AZUL = new Color(54, 117, 205);
    public static final Color AZUL_CLARO = new Color(218, 232, 248);
    public static final Color TEXTO = new Color(45, 45, 45);
    public static final Color TEXTO_SECUNDARIO = new Color(120, 120, 120);
    public static final Color PLACEHOLDER = new Color(160, 163, 168);
    public static final Color BORDA = new Color(220, 223, 228);
    public static final Color FUNDO_HOVER = new Color(242, 244, 248);
    public static final Color FUNDO_SELECIONADO = new Color(222, 233, 250);

    // Usadas pelo botão de fechar personalizado (BarraTitulo)
    public static final Color VERMELHO = new Color(224, 62, 76);
    public static final Color VERMELHO_HOVER = new Color(196, 42, 56);

    public static final Font FONTE_LOGO = new Font("Arial", Font.BOLD, 16);
    public static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 18);
    public static final Font FONTE_SUBTITULO = new Font("Arial", Font.BOLD, 12);
    public static final Font FONTE_TEXTO = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONTE_TEXTO_PEQUENA = new Font("Arial", Font.PLAIN, 10);
}
