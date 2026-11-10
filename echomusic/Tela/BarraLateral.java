package Tela;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class BarraLateral extends JPanel {

    private static final String[] ITENS_MENU = {"Playlists", "Perfil", "Sobre"};

    private final JPanel painelMenu;
    private final String telaSelecionada;
    private Consumer<String> aoClicarBotao;

    public BarraLateral(String telaSelecionada) {
        this.telaSelecionada = telaSelecionada;

        setPreferredSize(new Dimension(150, 0));
        setBackground(Tema.FUNDO);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Tema.BORDA));
        setLayout(new BorderLayout());

        add(criarLogo(), BorderLayout.NORTH);

        painelMenu = criarMenu();
        add(painelMenu, BorderLayout.CENTER);
    }


    public void aoClicar(Consumer<String> ouvinte) {
        this.aoClicarBotao = ouvinte;
    }

    private JPanel criarLogo() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        painel.setBackground(Tema.FUNDO);

        JLabel logo = new JLabel("♫ Echo Music");
        logo.setFont(Tema.FONTE_LOGO);
        logo.setForeground(Tema.ROXO);

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
        boolean selecionado = texto.equalsIgnoreCase(telaSelecionada);

        JButton botao = new JButton(texto);
        botao.setFont(selecionado ? Tema.FONTE_SUBTITULO : Tema.FONTE_TEXTO);
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 3));
        botao.setMaximumSize(new Dimension(150, 36));
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (selecionado) {
            botao.setBackground(Tema.FUNDO_SELECIONADO);
            botao.setForeground(Tema.ROXO_ESCURO);
        } else {
            botao.setBackground(Tema.FUNDO);
            botao.setForeground(Tema.TEXTO);

            botao.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    botao.setBackground(Tema.ROXO_MUITO_CLARO);
                    botao.setForeground(Tema.ROXO_ESCURO);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    botao.setBackground(Tema.FUNDO);
                    botao.setForeground(Tema.TEXTO);
                }
            });
        }

        botao.addActionListener(e -> {
            if (aoClicarBotao != null) aoClicarBotao.accept(texto);
        });

        return botao;
    }
}
