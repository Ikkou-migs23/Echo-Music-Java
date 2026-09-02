package view;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class BarraLateral extends JPanel {

    private static final Color FUNDO = new Color(249, 250, 252);
    private static final Color AZUL = new Color(54, 117, 205);
    private static final Color AZUL_CLARO = new Color(218, 232, 248);
    private static final Color TEXTO = new Color(45, 45, 45);
    private static final Color BORDA = new Color(215, 218, 222);

    private JPanel painelMenu;
    private Consumer<String> aoClicarBotao;

    public BarraLateral(String telaSelecionada) {
        setPreferredSize(new Dimension(150, 0));
        setBackground(FUNDO);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDA));
        setLayout(new BorderLayout());

        add(criarLogo(), BorderLayout.NORTH);

        painelMenu = criarMenu();
        add(painelMenu, BorderLayout.CENTER);

        selecionarTela(telaSelecionada);
    }

    /**
     * Define o que acontece quando o usuário clica em um item do menu
     * (Home, Playlists, Perfil, Sobre). Cada tela que usa a BarraLateral
     * registra aqui como quer navegar.
     *
     * Ex.: barraLateral.aoClicar(tela -> { dispose(); ... });
     */
    public void aoClicar(Consumer<String> ouvinte) {
        this.aoClicarBotao = ouvinte;
    }

    private JPanel criarLogo() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        painel.setBackground(FUNDO);

        JLabel logo = new JLabel("♫ Echo Music");
        logo.setFont(new Font("Arial", Font.BOLD, 16));
        logo.setForeground(new Color(105, 65, 180));

        painel.add(logo);
        return painel;
    }

    private JPanel criarMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(FUNDO);

        JLabel titulo = new JLabel("MENU");
        titulo.setFont(new Font("Arial", Font.PLAIN, 10));
        titulo.setForeground(new Color(120, 120, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 10, 7, 0));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        menu.add(titulo);
        menu.add(criarBotao("Home"));
        menu.add(criarBotao("Playlists"));
        menu.add(criarBotao("Perfil"));
        menu.add(criarBotao("Sobre"));

        return menu;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);

        botao.setFont(new Font("Arial", Font.PLAIN, 12));
        botao.setForeground(TEXTO);
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setFocusPainted(false);
        botao.setBackground(FUNDO);
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
            if (componente instanceof JButton) {
                JButton botao = (JButton) componente;

                if (botao.getText().equalsIgnoreCase(tela)) {
                    botao.setBackground(AZUL_CLARO);
                    botao.setForeground(AZUL);
                    botao.setFont(new Font("Arial", Font.BOLD, 12));
                }
            }
        }
    }
}