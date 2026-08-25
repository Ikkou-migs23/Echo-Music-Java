package DAO;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal {

    // =========================================================
    // CORES
    // =========================================================

    private static final Color FUNDO =
            new Color(
                    247,
                    248,
                    250
            );

    private static final Color BRANCO =
            Color.WHITE;

    private static final Color AZUL =
            new Color(
                    54,
                    117,
                    205
            );

    private static final Color TEXTO =
            new Color(
                    40,
                    40,
                    40
            );

    private static final Color CINZA =
            new Color(
                    105,
                    105,
                    105
            );

    private static final Color BORDA =
            new Color(
                    220,
                    223,
                    228
            );

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception e) {

                e.printStackTrace();
            }

            // =================================================
            // JANELA
            // =================================================

            JFrame janela =
                    new JFrame(
                            "Echo Music - Sua Biblioteca"
                    );

            janela.setSize(
                    900,
                    600
            );

            janela.setResizable(
                    false
            );

            janela.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            janela.setLocationRelativeTo(
                    null
            );

            // =================================================
            // PAINEL PRINCIPAL
            // =================================================

            JPanel principal =
                    new JPanel(
                            new BorderLayout()
                    );

            principal.setBackground(
                    FUNDO
            );

            // =================================================
            // BARRA LATERAL
            // =================================================

            BarraLateral barraLateral =
                    new BarraLateral(
                            "Playlists"
                    );

            principal.add(
                    barraLateral,
                    BorderLayout.WEST
            );

            // =================================================
            // ÁREA CENTRAL
            // =================================================

            JPanel centro =
                    new JPanel(
                            new BorderLayout()
                    );

            centro.setBackground(
                    FUNDO
            );

            // =================================================
            // BARRA SUPERIOR
            // =================================================

            JPanel barraSuperior =
                    criarBarraSuperior();

            centro.add(
                    barraSuperior,
                    BorderLayout.NORTH
            );

            // =================================================
            // CONTEÚDO
            // =================================================

            JPanel conteudo =
                    criarConteudo();

            centro.add(
                    conteudo,
                    BorderLayout.CENTER
            );

            principal.add(
                    centro,
                    BorderLayout.CENTER
            );

            // =================================================
            // PLAYER
            // =================================================

            JPanel player =
                    criarPlayer();

            principal.add(
                    player,
                    BorderLayout.SOUTH
            );

            // =================================================
            // EXIBIR
            // =================================================

            janela.add(
                    principal
            );

            janela.setVisible(
                    true
            );
        });
    }

    // =========================================================
    // BARRA SUPERIOR
    // =========================================================

    private static JPanel criarBarraSuperior() {

        JPanel barra =
                new JPanel(
                        new BorderLayout()
                );

        barra.setBackground(
                BRANCO
        );

        barra.setPreferredSize(
                new Dimension(
                        0,
                        45
                )
        );

        barra.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        BORDA
                )
        );

        // =====================================================
        // PESQUISA
        // =====================================================

        JPanel pesquisa =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                8
                        )
                );

        pesquisa.setBackground(
                BRANCO
        );

        JTextField busca =
                new JTextField(
                        "Buscar Música..."
                );

        busca.setPreferredSize(
                new Dimension(
                        270,
                        27
                )
        );

        busca.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        busca.setForeground(
                new Color(
                        130,
                        130,
                        130
                )
        );

        busca.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        213,
                                        218
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                2,
                                7,
                                2,
                                7
                        )
                )
        );

        pesquisa.add(
                busca
        );

        barra.add(
                pesquisa,
                BorderLayout.WEST
        );

        return barra;
    }

    // =========================================================
    // CONTEÚDO
    // =========================================================

    private static JPanel criarConteudo() {

        JPanel painel =
                new JPanel(
                        new BorderLayout()
                );

        painel.setBackground(
                FUNDO
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        12,
                        8,
                        12
                )
        );

        // =====================================================
        // CABEÇALHO
        // =====================================================

        JPanel cabecalho =
                new JPanel(
                        new BorderLayout()
                );

        cabecalho.setBackground(
                FUNDO
        );

        // =====================================================
        // TEXTOS
        // =====================================================

        JPanel textos =
                new JPanel();

        textos.setLayout(
                new BoxLayout(
                        textos,
                        BoxLayout.Y_AXIS
                )
        );

        textos.setBackground(
                FUNDO
        );

        JLabel caminho =
                new JLabel(
                        "Home  >  Minhas Playlists"
                );

        caminho.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        8
                )
        );

        caminho.setForeground(
                AZUL
        );

        JLabel titulo =
                new JLabel(
                        "Sua Biblioteca"
                );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        titulo.setForeground(
                TEXTO
        );

        textos.add(
                caminho
        );

        textos.add(
                Box.createVerticalStrut(
                        4
                )
        );

        textos.add(
                titulo
        );

        cabecalho.add(
                textos,
                BorderLayout.WEST
        );

        // =====================================================
        // BOTÃO CRIAR NOVA
        // =====================================================

        JButton criar =
                new JButton(
                        "Criar Nova"
                );

        criar.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        criar.setForeground(
                Color.WHITE
        );

        criar.setBackground(
                AZUL
        );

        criar.setFocusPainted(
                false
        );

        criar.setBorder(
                BorderFactory.createEmptyBorder(
                        7,
                        11,
                        7,
                        11
                )
        );

        cabecalho.add(
                criar,
                BorderLayout.EAST
        );

        painel.add(
                cabecalho,
                BorderLayout.NORTH
        );

        // =====================================================
        // ÁREA PRINCIPAL
        // =====================================================

        JPanel area =
                new JPanel();

        area.setLayout(
                new BoxLayout(
                        area,
                        BoxLayout.Y_AXIS
                )
        );

        area.setBackground(
                FUNDO
        );

        area.add(
                Box.createVerticalStrut(
                        8
                )
        );

        // =====================================================
        // FILTROS
        // =====================================================

        JPanel filtros =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                6,
                                2
                        )
                );

        filtros.setBackground(
                FUNDO
        );

        JLabel filtroLabel =
                new JLabel(
                        "Filtro:"
                );

        filtroLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        JComboBox<String> filtro =
                new JComboBox<>();

        filtro.addItem(
                "Categoria"
        );

        filtro.addItem(
                "Rock"
        );

        filtro.addItem(
                "Pop"
        );

        filtro.addItem(
                "Eletrônica"
        );

        filtro.setPreferredSize(
                new Dimension(
                        120,
                        24
                )
        );

        filtro.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        JLabel ordenarLabel =
                new JLabel(
                        "Ordenar por:"
                );

        ordenarLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        JComboBox<String> ordenar =
                new JComboBox<>();

        ordenar.addItem(
                "Nome"
        );

        ordenar.addItem(
                "Quantidade"
        );

        ordenar.addItem(
                "Data"
        );

        ordenar.setPreferredSize(
                new Dimension(
                        120,
                        24
                )
        );

        ordenar.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        filtros.add(
                filtroLabel
        );

        filtros.add(
                filtro
        );

        filtros.add(
                Box.createHorizontalStrut(
                        8
                )
        );

        filtros.add(
                ordenarLabel
        );

        filtros.add(
                ordenar
        );

        area.add(
                filtros
        );

        area.add(
                Box.createVerticalStrut(
                        7
                )
        );

        // =====================================================
        // CARDS
        // =====================================================

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                7,
                                7
                        )
                );

        cards.setBackground(
                FUNDO
        );

        cards.add(
                criarPlaylist(
                        "Rock 90s",
                        "50 músicas",
                        new Color(
                                65,
                                35,
                                35
                        ),
                        "ROCK\n90s"
                )
        );

        cards.add(
                criarPlaylist(
                        "Treino Pesado",
                        "32 músicas",
                        new Color(
                                55,
                                55,
                                55
                        ),
                        "TREINO"
                )
        );

        cards.add(
                criarPlaylist(
                        "Estudo Lo-Fi",
                        "105 músicas",
                        new Color(
                                40,
                                50,
                                65
                        ),
                        "LO-FI"
                )
        );

        cards.add(
                criarPlaylist(
                        "Minha Vibe",
                        "12 músicas",
                        new Color(
                                70,
                                25,
                                75
                        ),
                        "VIBE"
                )
        );

        // Espaços vazios

        JPanel vazio1 =
                new JPanel();

        vazio1.setBackground(
                FUNDO
        );

        JPanel vazio2 =
                new JPanel();

        vazio2.setBackground(
                FUNDO
        );

        cards.add(
                vazio1
        );

        cards.add(
                vazio2
        );

        area.add(
                cards
        );

        area.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painel.add(
                area,
                BorderLayout.CENTER
        );

        return painel;
    }

    // =========================================================
    // CARD PLAYLIST
    // =========================================================

    private static JPanel criarPlaylist(
            String nome,
            String quantidade,
            Color cor,
            String textoCapa
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                7,
                                0
                        )
                );

        card.setBackground(
                BRANCO
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDA
                        ),
                        BorderFactory.createEmptyBorder(
                                6,
                                6,
                                6,
                                6
                        )
                )
        );

        // =====================================================
        // CAPA
        // =====================================================

        JPanel capa =
                new JPanel(
                        new GridBagLayout()
                );

        capa.setPreferredSize(
                new Dimension(
                        65,
                        65
                )
        );

        capa.setBackground(
                cor
        );

        JLabel texto =
                new JLabel(
                        "<html><center>" +
                        textoCapa.replace(
                                "\n",
                                "<br>"
                        ) +
                        "</center></html>"
                );

        texto.setForeground(
                Color.WHITE
        );

        texto.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        capa.add(
                texto
        );

        card.add(
                capa,
                BorderLayout.WEST
        );

        // =====================================================
        // INFORMAÇÕES
        // =====================================================

        JPanel informacoes =
                new JPanel();

        informacoes.setLayout(
                new BoxLayout(
                        informacoes,
                        BoxLayout.Y_AXIS
                )
        );

        informacoes.setBackground(
                BRANCO
        );

        JLabel nomeLabel =
                new JLabel(
                        nome
                );

        nomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        nomeLabel.setForeground(
                TEXTO
        );

        JLabel quantidadeLabel =
                new JLabel(
                        quantidade
                );

        quantidadeLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        8
                )
        );

        quantidadeLabel.setForeground(
                CINZA
        );

        informacoes.add(
                Box.createVerticalStrut(
                        12
                )
        );

        informacoes.add(
                nomeLabel
        );

        informacoes.add(
                Box.createVerticalStrut(
                        4
                )
        );

        informacoes.add(
                quantidadeLabel
        );

        card.add(
                informacoes,
                BorderLayout.CENTER
        );

        // =====================================================
        // MENU DO CARD
        // =====================================================

        JButton menu =
                new JButton(
                        "..."
                );

        menu.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        menu.setPreferredSize(
                new Dimension(
                        23,
                        23
                )
        );

        menu.setFocusPainted(
                false
        );

        menu.setBackground(
                BRANCO
        );

        menu.setMargin(
                new Insets(
                        0,
                        0,
                        0,
                        0
                )
        );

        menu.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                210,
                                210,
                                210
                        )
                )
        );

        card.add(
                menu,
                BorderLayout.EAST
        );

        return card;
    }

    // =========================================================
    // PLAYER
    // =========================================================

    private static JPanel criarPlayer() {

        JPanel player =
                new JPanel(
                        new BorderLayout()
                );

        player.setPreferredSize(
                new Dimension(
                        0,
                        48
                )
        );

        player.setBackground(
                BRANCO
        );

        player.setBorder(
                BorderFactory.createMatteBorder(
                        1,
                        0,
                        0,
                        0,
                        BORDA
                )
        );

        // =====================================================
        // MÚSICA
        // =====================================================

        JPanel musica =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                7,
                                5
                        )
                );

        musica.setBackground(
                BRANCO
        );

        // =====================================================
        // CAPA
        // =====================================================

        JPanel capa =
                new JPanel(
                        new GridBagLayout()
                );

        capa.setPreferredSize(
                new Dimension(
                        34,
                        34
                )
        );

        capa.setBackground(
                new Color(
                        240,
                        241,
                        243
                )
        );

        capa.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                205,
                                207,
                                210
                        )
                )
        );

        JLabel nota =
                new JLabel(
                        new IconeNota()
                );

        capa.add(
                nota
        );

        musica.add(
                capa
        );

        // =====================================================
        // INFORMAÇÕES DA MÚSICA
        // =====================================================

        JPanel textos =
                new JPanel();

        textos.setLayout(
                new BoxLayout(
                        textos,
                        BoxLayout.Y_AXIS
                )
        );

        textos.setBackground(
                BRANCO
        );

        JLabel musicaSelecionada =
                new JLabel(
                        "Nenhuma música selecionada"
                );

        musicaSelecionada.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        8
                )
        );

        musicaSelecionada.setForeground(
                new Color(
                        55,
                        55,
                        55
                )
        );

        JLabel tempo =
                new JLabel(
                        "--:-- / --:--"
                );

        tempo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        7
                )
        );

        tempo.setForeground(
                new Color(
                        120,
                        120,
                        120
                )
        );

        textos.add(
                musicaSelecionada
        );

        textos.add(
                tempo
        );

        musica.add(
                textos
        );

        player.add(
                musica,
                BorderLayout.WEST
        );

        // =====================================================
        // CONTROLES
        // =====================================================

        JPanel controles =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                6,
                                11
                        )
                );

        controles.setBackground(
                BRANCO
        );

        JButton anterior =
                criarBotaoPlayer(
                        "|<"
                );

        JButton play =
                criarBotaoPlayer(
                        ">"
                );

        JButton pause =
                criarBotaoPlayer(
                        "||"
                );

        JButton proximo =
                criarBotaoPlayer(
                        ">|"
                );

        controles.add(
                anterior
        );

        controles.add(
                play
        );

        controles.add(
                pause
        );

        controles.add(
                proximo
        );

        player.add(
                controles,
                BorderLayout.CENTER
        );

        // =====================================================
        // VOLUME
        // =====================================================

        JPanel volume =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                7,
                                13
                        )
                );

        volume.setBackground(
                BRANCO
        );

        JLabel volumeBaixo =
                new JLabel(
                        new IconeVolumeBaixo()
                );

        JSlider slider =
                new JSlider(
                        0,
                        100,
                        55
                );

        slider.setPreferredSize(
                new Dimension(
                        95,
                        18
                )
        );

        slider.setBackground(
                BRANCO
        );

        slider.setFocusable(
                false
        );

        JLabel volumeAlto =
                new JLabel(
                        new IconeVolumeAlto()
                );

        JButton lista =
                new JButton(
                        "="
                );

        lista.setPreferredSize(
                new Dimension(
                        25,
                        23
                )
        );

        lista.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        lista.setFocusPainted(
                false
        );

        lista.setBackground(
                new Color(
                        245,
                        246,
                        248
                )
        );

        lista.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                205,
                                207,
                                210
                        )
                )
        );

        volume.add(
                volumeBaixo
        );

        volume.add(
                slider
        );

        volume.add(
                volumeAlto
        );

        volume.add(
                lista
        );

        player.add(
                volume,
                BorderLayout.EAST
        );

        return player;
    }

    // =========================================================
    // BOTÃO DO PLAYER
    // =========================================================

    private static JButton criarBotaoPlayer(
            String texto
    ) {

        JButton botao =
                new JButton(
                        texto
                );

        botao.setPreferredSize(
                new Dimension(
                        32,
                        23
                )
        );

        botao.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        botao.setForeground(
                new Color(
                        55,
                        55,
                        55
                )
        );

        botao.setBackground(
                new Color(
                        245,
                        246,
                        248
                )
        );

        botao.setFocusPainted(
                false
        );

        botao.setMargin(
                new Insets(
                        0,
                        0,
                        0,
                        0
                )
        );

        botao.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                205,
                                207,
                                210
                        )
                )
        );

        return botao;
    }

    // =========================================================
    // ÍCONE DE NOTA MUSICAL
    // =========================================================

    private static class IconeNota
            implements Icon {

        public int getIconWidth() {

            return 20;
        }

        public int getIconHeight() {

            return 20;
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
                            165,
                            165,
                            165
                    )
            );

            g2.setStroke(
                    new BasicStroke(
                            1.5f
                    )
            );

            g2.drawLine(
                    x + 12,
                    y + 2,
                    x + 12,
                    y + 14
            );

            g2.drawLine(
                    x + 12,
                    y + 2,
                    x + 17,
                    y + 1
            );

            g2.fillOval(
                    x + 6,
                    y + 12,
                    7,
                    5
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ÍCONE VOLUME BAIXO
    // =========================================================

    private static class IconeVolumeBaixo
            implements Icon {

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
                            70,
                            70,
                            70
                    )
            );

            int[] pontosX = {
                    x + 1,
                    x + 5,
                    x + 5,
                    x + 1
            };

            int[] pontosY = {
                    y + 5,
                    y + 5,
                    y + 10,
                    y + 10
            };

            g2.fillPolygon(
                    pontosX,
                    pontosY,
                    4
            );

            g2.fillRect(
                    x + 5,
                    y + 4,
                    3,
                    7
            );

            g2.drawArc(
                    x + 5,
                    y + 3,
                    8,
                    9,
                    -60,
                    120
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ÍCONE VOLUME ALTO
    // =========================================================

    private static class IconeVolumeAlto
            implements Icon {

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
                            70,
                            70,
                            70
                    )
            );

            g2.fillRect(
                    x + 1,
                    y + 5,
                    4,
                    5
            );

            int[] pontosX = {
                    x + 5,
                    x + 9,
                    x + 9,
                    x + 5
            };

            int[] pontosY = {
                    y + 5,
                    y + 2,
                    y + 12,
                    y + 9
            };

            g2.fillPolygon(
                    pontosX,
                    pontosY,
                    4
            );

            g2.drawArc(
                    x + 5,
                    y + 1,
                    9,
                    13,
                    -55,
                    110
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ÍCONE INFO
    // =========================================================

    private static class IconeInfo
            implements Icon {

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
                    AZUL
            );

            g2.drawOval(
                    x + 1,
                    y + 1,
                    12,
                    12
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
