package DAO;

import javax.swing.*;
import java.awt.*;

public class BarraLateral extends JPanel {

    private JButton home;
    private JButton playlists;
    private JButton perfil;
    private JButton info;

    public BarraLateral(String telaSelecionada) {

        // =====================================================
        // CORES
        // =====================================================

        Color fundo = new Color(249, 250, 252);
        Color azul = new Color(54, 117, 205);
        Color azulClaro = new Color(218, 232, 248);
        Color texto = new Color(45, 45, 45);
        Color cinza = new Color(120, 120, 120);
        Color borda = new Color(215, 218, 222);

        // =====================================================
        // CONFIGURAÇÃO DA BARRA
        // =====================================================

        setPreferredSize(
                new Dimension(
                        150,
                        0
                )
        );

        setBackground(fundo);

        setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        0,
                        1,
                        borda
                )
        );

        setLayout(
                new BorderLayout()
        );

        // =====================================================
        // LOGO
        // =====================================================

        JPanel painelLogo = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        8,
                        7
                )
        );

        painelLogo.setBackground(fundo);

        // Ícone
        JLabel iconeLogo = new JLabel(
                new IconeMusica(
                        new Color(105, 65, 180)
                )
        );

        JLabel logo = new JLabel(
                "Echo Music"
        );

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        logo.setForeground(
                new Color(
                        70,
                        55,
                        130
                )
        );

        painelLogo.add(iconeLogo);
        painelLogo.add(logo);

        add(
                painelLogo,
                BorderLayout.NORTH
        );

        // =====================================================
        // MENU
        // =====================================================

        JPanel menu = new JPanel();

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );

        menu.setBackground(fundo);

        // =====================================================
        // TÍTULO MENU
        // =====================================================

        JLabel tituloMenu = new JLabel(
                "MENU"
        );

        tituloMenu.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        8
                )
        );

        tituloMenu.setForeground(
                cinza
        );

        tituloMenu.setBorder(
                BorderFactory.createEmptyBorder(
                        2,
                        8,
                        5,
                        0
                )
        );

        tituloMenu.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        menu.add(tituloMenu);

        // =====================================================
        // HOME
        // =====================================================

        home = criarBotao(
                "Home",
                new IconeHome()
        );

        menu.add(home);

        // =====================================================
        // PLAYLISTS
        // =====================================================

        playlists = criarBotao(
                "Playlists",
                new IconePlaylist()
        );

        menu.add(playlists);

        // =====================================================
        // PERFIL
        // =====================================================

        perfil = criarBotao(
                "Perfil",
                new IconePerfil()
        );

        menu.add(perfil);

        // =====================================================
        // INFO
        // =====================================================

        info = criarBotao(
                "Info",
                new IconeInfo()
        );

        menu.add(info);

        add(
                menu,
                BorderLayout.CENTER
        );

        // =====================================================
        // TELA SELECIONADA
        // =====================================================

        selecionarTela(
                telaSelecionada,
                azul,
                azulClaro,
                fundo,
                texto
        );
    }

    // =========================================================
    // CRIAR BOTÃO
    // =========================================================

    private JButton criarBotao(
            String texto,
            Icon icone
    ) {

        JButton botao = new JButton(
                texto,
                icone
        );

        botao.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        botao.setForeground(
                new Color(
                        45,
                        45,
                        45
                )
        );

        botao.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        botao.setIconTextGap(7);

        botao.setFocusPainted(
                false
        );

        botao.setContentAreaFilled(
                true
        );

        botao.setOpaque(
                true
        );

        botao.setBackground(
                new Color(
                        249,
                        250,
                        252
                )
        );

        botao.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        7,
                        0,
                        3
                )
        );

        botao.setMaximumSize(
                new Dimension(
                        150,
                        100
                )
        );

        botao.setPreferredSize(
                new Dimension(
                        120,
                        25
                )
        );

        botao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return botao;
    }

    // =========================================================
    // SELECIONAR TELA
    // =========================================================

    private void selecionarTela(
            String tela,
            Color azul,
            Color azulClaro,
            Color fundo,
            Color texto
    ) {

        // Home

        if (tela.equalsIgnoreCase("Home")) {

            home.setBackground(
                    azulClaro
            );

            home.setForeground(
                    azul
            );

            home.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            9
                    )
            );
        }

        // Playlists

        if (tela.equalsIgnoreCase("Playlists")) {

            playlists.setBackground(
                    azulClaro
            );

            playlists.setForeground(
                    azul
            );

            playlists.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            9
                    )
            );
        }

        // Perfil

        if (tela.equalsIgnoreCase("Perfil")) {

            perfil.setBackground(
                    azulClaro
            );

            perfil.setForeground(
                    azul
            );

            perfil.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            9
                    )
            );
        }

        // Info

        if (tela.equalsIgnoreCase("Info")) {

            info.setBackground(
                    azulClaro
            );

            info.setForeground(
                    azul
            );

            info.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            9
                    )
            );
        }
    }

    // =========================================================
    // ÍCONE DA LOGO
    // =========================================================

    private class IconeMusica implements Icon {

        private Color cor;

        public IconeMusica(Color cor) {

            this.cor = cor;
        }

        public int getIconWidth() {

            return 16;
        }

        public int getIconHeight() {

            return 16;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(cor);

            g2.setStroke(
                    new BasicStroke(
                            2
                    )
            );

            // Haste da nota
            g2.drawLine(
                    x + 10,
                    y + 2,
                    x + 10,
                    y + 11
            );

            // Parte superior
            g2.drawLine(
                    x + 10,
                    y + 2,
                    x + 14,
                    y + 1
            );

            // Nota
            g2.fillOval(
                    x + 4,
                    y + 9,
                    7,
                    5
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ÍCONE HOME
    // =========================================================

    private class IconeHome implements Icon {

        public int getIconWidth() {

            return 14;
        }

        public int getIconHeight() {

            return 14;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(
                    new Color(
                            55,
                            75,
                            95
                    )
            );

            int[] pontosX = {
                    x + 1,
                    x + 7,
                    x + 13
            };

            int[] pontosY = {
                    y + 6,
                    y + 1,
                    y + 6
            };

            g2.fillPolygon(
                    pontosX,
                    pontosY,
                    3
            );

            g2.fillRect(
                    x + 3,
                    y + 5,
                    8,
                    7
            );

            g2.setColor(
                    new Color(
                            249,
                            250,
                            252
                    )
            );

            g2.fillRect(
                    x + 6,
                    y + 8,
                    2,
                    4
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ÍCONE PLAYLIST
    // =========================================================

    private class IconePlaylist implements Icon {

        public int getIconWidth() {

            return 14;
        }

        public int getIconHeight() {

            return 14;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(
                    new Color(
                            55,
                            75,
                            95
                    )
            );

            g2.setStroke(
                    new BasicStroke(
                            1.5f
                    )
            );

            // Linhas da playlist

            g2.drawLine(
                    x + 4,
                    y + 3,
                    x + 13,
                    y + 3
            );

            g2.drawLine(
                    x + 4,
                    y + 7,
                    x + 13,
                    y + 7
            );

            g2.drawLine(
                    x + 4,
                    y + 11,
                    x + 10,
                    y + 11
            );

            // Pequeno marcador

            g2.fillRect(
                    x + 1,
                    y + 2,
                    2,
                    2
            );

            g2.fillRect(
                    x + 1,
                    y + 6,
                    2,
                    2
            );

            g2.fillRect(
                    x + 1,
                    y + 10,
                    2,
                    2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ÍCONE PERFIL
    // =========================================================

    private class IconePerfil implements Icon {

        public int getIconWidth() {

            return 14;
        }

        public int getIconHeight() {

            return 14;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(
                    new Color(
                            55,
                            75,
                            95
                    )
            );

            // Cabeça

            g2.fillOval(
                    x + 4,
                    y + 1,
                    6,
                    6
            );

            // Corpo

            g2.fillOval(
                    x + 2,
                    y + 7,
                    10,
                    6
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ÍCONE INFO
    // =========================================================

    private class IconeInfo implements Icon {

        public int getIconWidth() {

            return 14;
        }

        public int getIconHeight() {

            return 14;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(
                    new Color(
                            55,
                            75,
                            95
                    )
            );

            g2.fillOval(
                    x + 1,
                    y + 1,
                    12,
                    12
            );

            g2.setColor(
                    Color.WHITE
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            9
                    )
            );

            g2.drawString(
                    "i",
                    x + 6,
                    y + 10
            );

            g2.dispose();
        }
    }
}
