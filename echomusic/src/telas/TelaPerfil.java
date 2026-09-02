package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TelaPerfil {

    private static final Color FUNDO = new Color(247, 248, 250);
    private static final Color AZUL = new Color(54, 117, 205);
    private static final Color TEXTO = new Color(45, 45, 45);
    private static final Color BORDA = new Color(220, 223, 228);

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame janela =
                new JFrame("Echo Music - Perfil");

            janela.setSize(900, 600);
            janela.setResizable(false);
            janela.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
            );

            janela.setLocationRelativeTo(null);

            JPanel principal =
                new JPanel(new BorderLayout());

            principal.setBackground(FUNDO);

            principal.add(
                new BarraLateral("Perfil"),
                BorderLayout.WEST
            );

            principal.add(
                criarPerfil(),
                BorderLayout.CENTER
            );

            principal.add(
                criarPlayer(),
                BorderLayout.SOUTH
            );

            janela.add(principal);

            janela.setVisible(true);
        });
    }

    private static JPanel criarBarraSuperior() {

        JPanel barra =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT,
                    10,
                    8
                )
            );

        barra.setBackground(Color.WHITE);

        barra.setPreferredSize(
            new Dimension(0, 45)
        );

        barra.setBorder(
            BorderFactory.createMatteBorder(
                0, 0, 1, 0, BORDA
            )
        );

        JTextField busca =
            new JTextField();

        busca.setText("Buscar Música...");
        busca.setForeground(
            new Color(130, 130, 130)
        );

        busca.setPreferredSize(
            new Dimension(270, 27)
        );

        barra.add(busca);

        return barra;
    }

    private static JPanel criarPerfil() {

        JPanel painel =
            new JPanel(new BorderLayout());

        painel.setBackground(FUNDO);

        painel.add(
            criarBarraSuperior(),
            BorderLayout.NORTH
        );

        JPanel conteudo =
            new JPanel(new BorderLayout());

        conteudo.setBackground(FUNDO);

        conteudo.setBorder(
            BorderFactory.createEmptyBorder(
                15, 15, 10, 15
            )
        );

        JPanel cabecalho =
            new JPanel(new BorderLayout());

        cabecalho.setBackground(FUNDO);

        JLabel titulo =
            new JLabel("Meu Perfil");

        titulo.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                18
            )
        );

        titulo.setForeground(TEXTO);

        cabecalho.add(
            titulo,
            BorderLayout.WEST
        );

        conteudo.add(
            cabecalho,
            BorderLayout.NORTH
        );

        JPanel areaPerfil =
            new JPanel(
                new BorderLayout()
            );

        areaPerfil.setBackground(FUNDO);

        areaPerfil.setBorder(
            BorderFactory.createEmptyBorder(
                12, 0, 0, 0
            )
        );

        areaPerfil.add(
            criarFoto(),
            BorderLayout.WEST
        );

        areaPerfil.add(
            criarInformacoes(),
            BorderLayout.CENTER
        );

        conteudo.add(
            areaPerfil,
            BorderLayout.CENTER
        );

        painel.add(
            conteudo,
            BorderLayout.CENTER
        );

        return painel;
    }

    private static JPanel criarFoto() {

        JPanel painel =
            new JPanel();

        painel.setBackground(FUNDO);

        painel.setLayout(
            new BoxLayout(
                painel,
                BoxLayout.Y_AXIS
            )
        );

        painel.setPreferredSize(
            new Dimension(190, 250)
        );

        JLabel titulo =
            new JLabel("Foto de perfil");

        titulo.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                11
            )
        );

        titulo.setForeground(TEXTO);

        titulo.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        painel.add(titulo);

        painel.add(
            Box.createVerticalStrut(7)
        );

        JButton foto =
            new JButton("FOTO");

        foto.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                11
            )
        );

        foto.setForeground(
            new Color(110, 110, 110)
        );

        foto.setBackground(
            new Color(245, 246, 248)
        );

        foto.setFocusPainted(false);

        foto.setBorder(
            new LineBorder(BORDA)
        );

        foto.setPreferredSize(
            new Dimension(170, 170)
        );

        foto.setMinimumSize(
            new Dimension(170, 170)
        );

        foto.setMaximumSize(
            new Dimension(170, 170)
        );

        foto.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        foto.addActionListener(e -> {

            JFileChooser seletor =
                new JFileChooser();

            int resultado =
                seletor.showOpenDialog(painel);

            if (
                resultado ==
                JFileChooser.APPROVE_OPTION
            ) {

                ImageIcon imagemOriginal =
                    new ImageIcon(
                        seletor
                            .getSelectedFile()
                            .getAbsolutePath()
                    );

                Image imagem =
                    imagemOriginal.getImage();

                Image imagemRedimensionada =
                    imagem.getScaledInstance(
                        170,
                        170,
                        Image.SCALE_SMOOTH
                    );

                foto.setText("");

                foto.setIcon(
                    new ImageIcon(
                        imagemRedimensionada
                    )
                );
            }
        });

        painel.add(foto);

        painel.add(
            Box.createVerticalStrut(8)
        );

        JLabel ajuda =
            new JLabel(
                "Clique na foto para alterar"
            );

        ajuda.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                10
            )
        );

        ajuda.setForeground(
            new Color(130, 130, 130)
        );

        ajuda.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        painel.add(ajuda);

        return painel;
    }

    private static JPanel criarInformacoes() {

        JPanel painel =
            new JPanel();

        painel.setBackground(FUNDO);

        painel.setLayout(
            new BoxLayout(
                painel,
                BoxLayout.Y_AXIS
            )
        );

        painel.setBorder(
            BorderFactory.createEmptyBorder(
                0, 20, 0, 0
            )
        );

        adicionarCampo(
            painel,
            "Nome",
            "Insira seu nome"
        );

        adicionarCampo(
            painel,
            "E-mail",
            "Insira seu e-mail"
        );

        adicionarSenha(
            painel,
            "Nova senha"
        );

        adicionarSenha(
            painel,
            "Confirmar nova senha"
        );

        JButton salvar =
            new JButton(
                "SALVAR ALTERAÇÕES"
            );

        salvar.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                11
            )
        );

        salvar.setForeground(Color.WHITE);

        salvar.setBackground(AZUL);

        salvar.setFocusPainted(false);

        salvar.setBorderPainted(false);

        salvar.setPreferredSize(
            new Dimension(155, 29)
        );

        salvar.setMaximumSize(
            new Dimension(155, 29)
        );

        salvar.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        salvar.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                painel,
                "Alterações salvas com sucesso!",
                "Echo Music",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        painel.add(
            Box.createVerticalStrut(5)
        );

        painel.add(salvar);

        return painel;
    }

    private static JTextField adicionarCampo(
        JPanel painel,
        String titulo,
        String placeholder
    ) {

        JPanel campo =
            new JPanel(
                new BorderLayout(0, 5)
            );

        campo.setBackground(FUNDO);

        JLabel label =
            new JLabel(titulo);

        label.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                11
            )
        );

        label.setForeground(TEXTO);

        JTextField texto =
            new JTextField(placeholder);

        texto.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                11
            )
        );

        texto.setForeground(
            new Color(130, 130, 130)
        );

        texto.setPreferredSize(
            new Dimension(300, 30)
        );

        texto.setBorder(
            BorderFactory.createCompoundBorder(
                new LineBorder(BORDA),
                BorderFactory.createEmptyBorder(
                    4, 7, 4, 7
                )
            )
        );

        texto.addFocusListener(
            new java.awt.event.FocusAdapter() {

                @Override
                public void focusGained(
                    java.awt.event.FocusEvent e
                ) {

                    if (
                        texto.getText()
                            .equals(placeholder)
                    ) {

                        texto.setText("");

                        texto.setForeground(
                            Color.BLACK
                        );
                    }
                }

                @Override
                public void focusLost(
                    java.awt.event.FocusEvent e
                ) {

                    if (
                        texto.getText()
                            .trim()
                            .isEmpty()
                    ) {

                        texto.setText(
                            placeholder
                        );

                        texto.setForeground(
                            new Color(
                                130,
                                130,
                                130
                            )
                        );
                    }
                }
            }
        );

        campo.add(
            label,
            BorderLayout.NORTH
        );

        campo.add(
            texto,
            BorderLayout.CENTER
        );

        campo.setMaximumSize(
            new Dimension(
                Integer.MAX_VALUE,
                55
            )
        );

        campo.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        painel.add(campo);

        painel.add(
            Box.createVerticalStrut(9)
        );

        return texto;
    }

    private static JPasswordField adicionarSenha(
        JPanel painel,
        String titulo
    ) {

        JPanel campo =
            new JPanel(
                new BorderLayout(0, 5)
            );

        campo.setBackground(FUNDO);

        JLabel label =
            new JLabel(titulo);

        label.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                11
            )
        );

        label.setForeground(TEXTO);

        JPasswordField senha =
            new JPasswordField();

        senha.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                11
            )
        );

        senha.setPreferredSize(
            new Dimension(300, 30)
        );

        senha.setBorder(
            BorderFactory.createCompoundBorder(
                new LineBorder(BORDA),
                BorderFactory.createEmptyBorder(
                    4, 7, 4, 7
                )
            )
        );

        campo.add(
            label,
            BorderLayout.NORTH
        );

        campo.add(
            senha,
            BorderLayout.CENTER
        );

        campo.setMaximumSize(
            new Dimension(
                Integer.MAX_VALUE,
                55
            )
        );

        campo.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        painel.add(campo);

        painel.add(
            Box.createVerticalStrut(9)
        );

        return senha;
    }

    private static JPanel criarPlayer() {

        JPanel player =
            new JPanel(new BorderLayout());

        player.setPreferredSize(
            new Dimension(0, 50)
        );

        player.setBackground(Color.WHITE);

        player.setBorder(
            BorderFactory.createMatteBorder(
                1, 0, 0, 0, BORDA
            )
        );

        JPanel musica =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT,
                    10,
                    8
                )
            );

        musica.setBackground(Color.WHITE);

        JPanel capa =
            new JPanel();

        capa.setPreferredSize(
            new Dimension(34, 34)
        );

        capa.setBackground(
            new Color(235, 235, 235)
        );

        JPanel textos =
            new JPanel();

        textos.setLayout(
            new BoxLayout(
                textos,
                BoxLayout.Y_AXIS
            )
        );

        textos.setBackground(Color.WHITE);

        textos.add(
            new JLabel(
                "Nenhuma música selecionada"
            )
        );

        textos.add(
            new JLabel(
                "--:-- / --:--"
            )
        );

        musica.add(capa);
        musica.add(textos);

        player.add(
            musica,
            BorderLayout.WEST
        );

        player.add(
            criarControles(),
            BorderLayout.CENTER
        );

        player.add(
            criarVolume(),
            BorderLayout.EAST
        );

        return player;
    }

    private static JPanel criarControles() {

        JPanel controles =
            new JPanel(
                new FlowLayout(
                    FlowLayout.CENTER,
                    6,
                    13
                )
            );

        controles.setBackground(Color.WHITE);

        for (
            String texto :
            new String[]{"|<", ">", "||", ">|"}
        ) {

            JButton botao =
                new JButton(texto);

            botao.setPreferredSize(
                new Dimension(32, 23)
            );

            botao.setFocusPainted(false);

            botao.setMargin(
                new Insets(0, 0, 0, 0)
            );

            controles.add(botao);
        }

        return controles;
    }

    private static JPanel criarVolume() {

        JPanel volume =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT,
                    8,
                    13
                )
            );

        volume.setBackground(Color.WHITE);

        volume.add(
            new JLabel("Volume")
        );

        JSlider slider =
            new JSlider(
                0,
                100,
                55
            );

        slider.setPreferredSize(
            new Dimension(100, 18)
        );

        volume.add(slider);

        JButton lista =
            new JButton("=");

        lista.setPreferredSize(
            new Dimension(25, 23)
        );

        lista.setFocusPainted(false);

        volume.add(lista);

        return volume;
    }
}

