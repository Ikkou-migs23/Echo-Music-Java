package telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 * Paleta de cores e fontes padrão do Echo Music.
 *
 * A identidade visual do sistema é construída sobre tons de roxo (ações
 * principais/confirmação), vermelho (ações destrutivas/saída) e branco
 * (apoio). Todas as cores usadas nas telas devem vir daqui — evite
 * declarar valores de {@link Color} diretamente nos componentes, para que
 * qualquer ajuste de tema futuro precise ser feito em um único lugar.
 */
public final class Tema {

    private Tema() {
        // classe utilitária: não deve ser instanciada
    }

    // ---------- Roxos (cor principal da identidade visual) ----------
    /** Roxo principal — logo, destaques e ações de confirmação/criação. */
    public static final Color ROXO = new Color(105, 65, 180);
    /** Tom mais escuro, usado em hover/pressed de botões roxos. */
    public static final Color ROXO_ESCURO = new Color(82, 48, 150);
    /** Roxo claro — fundo de itens selecionados/destacados. */
    public static final Color ROXO_CLARO = new Color(228, 219, 245);
    /** Roxo quase branco — fundo suave, hover leve de itens de lista. */
    public static final Color ROXO_MUITO_CLARO = new Color(246, 243, 251);

    // ---------- Vermelhos (ações destrutivas / saída) ----------
    /** Vermelho principal — deletar, sair, fechar. */
    public static final Color VERMELHO = new Color(224, 62, 76);
    /** Tom mais escuro, usado em hover/pressed de botões vermelhos. */
    public static final Color VERMELHO_ESCURO = new Color(196, 42, 56);

    // ---------- Brancos e fundos ----------
    public static final Color BRANCO = Color.WHITE;
    /** Fundo geral das telas (branco com leve nuance arroxeada). */
    public static final Color FUNDO = new Color(248, 247, 250);
    /** Hover leve em cards/itens de lista sobre fundo claro. */
    public static final Color FUNDO_HOVER = ROXO_MUITO_CLARO;
    /** Fundo de destaque/seleção (ex.: item ativo do menu lateral). */
    public static final Color FUNDO_SELECIONADO = ROXO_CLARO;

    // ---------- Textos ----------
    public static final Color TEXTO = new Color(45, 45, 45);
    public static final Color TEXTO_SECUNDARIO = new Color(120, 120, 120);
    /** Texto sobre fundos escuros/coloridos (ex.: dentro de botões). */
    public static final Color TEXTO_CLARO = Color.WHITE;
    public static final Color PLACEHOLDER = new Color(160, 163, 168);

    // ---------- Bordas ----------
    public static final Color BORDA = new Color(226, 221, 233);

    // ---------- Fontes ----------
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

    /**
     * Aplica um efeito de hover simples a um botão, trocando o fundo entre
     * {@code corNormal} e {@code corHover} conforme o mouse entra/sai.
     * Centraliza aqui para evitar repetir o mesmo MouseAdapter em cada tela.
     */
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
