package telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Menu lateral (Home, Playlists, Perfil, Sobre), presente em todas as telas
 * internas do sistema. Cada tela define, via {@link #aoClicar}, para onde
 * navegar quando um item é selecionado.
 */
public class BarraLateral extends JPanel {

    private static final String[] ITENS_MENU = {"Playlists", "Perfil", "Sobre"};

    private final JPanel painelMenu;
    private Consumer<String> aoClicarBotao;

    public BarraLateral(String telaSelecionada) {
        setPreferredSize(new Dimension(150, 0));
        setBackground(Tema.FUNDO);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Tema.BORDA));
        setLayout(new BorderLayout());

        add(criarLogo(), BorderLayout.NORTH);

        painelMenu = criarMenu();
        add(painelMenu, BorderLayout.CENTER);

        selecionarTela(telaSelecionada);
    }

    /**
     * Define o que acontece quando o usuário clica em um item do menu.
     * Ex.: barraLateral.aoClicar(tela -> { dispose(); ... });
     */
    public void aoClicar(Consumer<String> ouvinte) {
        this.aoClicarBotao = ouvinte;
    }

    private JPanel criarLogo() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        painel.setBackground(Tema.FUNDO);

        JLabel logo = new JLabel("♫ Echo Music");
        logo.setFont(Tema.FONTE_LOGO);
        logo.setForeground(new java.awt.Color(105, 65, 180));

        painel.add(logo);
        return painel;
    }

    private JPanel criarMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(Tema.FUNDO);

        JLabel titulo = new JLabel("MENU");
        titulo.setFont(Tema.FONTE_TEXTO_PEQUENA);
        titulo.setForeground(Tema.TEXTO_SECUNDARIO);
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 10, 7, 0));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(titulo);

        for (String item : ITENS_MENU) {
            menu.add(criarBotao(item));
        }

        return menu;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);

        botao.setFont(Tema.FONTE_TEXTO);
        botao.setForeground(Tema.TEXTO);
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setFocusPainted(false);
        botao.setBackground(Tema.FUNDO);
        botao.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 3));
        botao.setMaximumSize(new Dimension(150, 36));
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addActionListener(e -> {
            if (aoClicarBotao != null) aoClicarBotao.accept(texto);
        });

        return botao;
    }

    private void selecionarTela(String tela) {
        if (tela == null) return;

        for (Component componente : painelMenu.getComponents()) {
            if (componente instanceof JButton botao && botao.getText().equalsIgnoreCase(tela)) {
                botao.setBackground(Tema.AZUL_CLARO);
                botao.setForeground(Tema.AZUL);
                botao.setFont(Tema.FONTE_SUBTITULO);
            }
        }
    }
}
