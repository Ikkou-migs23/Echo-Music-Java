package Tela;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class TelaCadastro extends JFrame {

    private JTextField campoNome;
    private JPasswordField campoSenha;

    public TelaCadastro() {
        setTitle("Echo Music - Criar sua Conta");
        setSize(720, 470);
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.BRANCO);
        raiz.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Tema.BRANCO);

        GridBagConstraints gbc = new GridBagConstraints();

        adicionarLogo(painel, gbc);
        adicionarTitulo(painel, gbc);
        campoNome = adicionarCampoNome(painel, gbc);
        campoSenha = adicionarCampoSenha(painel, gbc);
        adicionarBotaoCadastrar(painel, gbc);

        raiz.add(new BarraTitulo(this), BorderLayout.NORTH);
        raiz.add(painel, BorderLayout.CENTER);

        add(raiz);
    }

    private void adicionarLogo(JPanel painel, GridBagConstraints gbc) {
        JLabel logo = new JLabel("♫ Echo Music");
        logo.setFont(Tema.FONTE_LOGO_GRANDE);
        logo.setForeground(Tema.ROXO);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(25, 5, 10, 5);
        painel.add(logo, gbc);
    }

    private void adicionarTitulo(JPanel painel, GridBagConstraints gbc) {
        JLabel titulo = new JLabel("Criar sua conta");
        titulo.setFont(new Font("Arial", Font.BOLD, 17));

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 16, 5);
        painel.add(titulo, gbc);
    }

    private JTextField adicionarCampoNome(JPanel painel, GridBagConstraints gbc) {
        JLabel rotulo = new JLabel("Nome");
        rotulo.setFont(new Font("Arial", Font.PLAIN, 11));

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 2, 0);
        painel.add(rotulo, gbc);

        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(320, 28));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);
        painel.add(campo, gbc);

        return campo;
    }

    private JPasswordField adicionarCampoSenha(JPanel painel, GridBagConstraints gbc) {
        JLabel rotulo = new JLabel("Senha");
        rotulo.setFont(new Font("Arial", Font.PLAIN, 11));

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 0, 2, 0);
        painel.add(rotulo, gbc);

        JPanel painelSenha = new JPanel(new BorderLayout(4, 0));
        painelSenha.setBackground(Tema.BRANCO);

        JPasswordField senha = new JPasswordField();
        senha.setPreferredSize(new Dimension(288, 28));

        JButton visualizar = new JButton("👁");
        visualizar.setPreferredSize(new Dimension(28, 28));
        visualizar.setMargin(new Insets(0, 0, 0, 0));
        visualizar.addActionListener(e -> {
            boolean oculta = senha.getEchoChar() != 0;
            senha.setEchoChar(oculta ? (char) 0 : '•');
        });

        painelSenha.add(senha, BorderLayout.CENTER);
        painelSenha.add(visualizar, BorderLayout.EAST);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 6, 0);
        painel.add(painelSenha, gbc);

        return senha;
    }

    private void adicionarBotaoCadastrar(JPanel painel, GridBagConstraints gbc) {
        JButton cadastrar = new JButton("Cadastrar");
        cadastrar.setPreferredSize(new Dimension(250, 30));
        cadastrar.setFont(new Font("Arial", Font.BOLD, 11));
        cadastrar.setForeground(Tema.BRANCO);
        cadastrar.setFocusPainted(false);
        cadastrar.setBorderPainted(false);
        cadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tema.aplicarHover(cadastrar, Tema.ROXO, Tema.ROXO_ESCURO);
        cadastrar.addActionListener(e -> cadastrar());

        gbc.gridy = 7;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 14, 0);
        painel.add(cadastrar, gbc);
    }

    private void cadastrar() {
        if (campoNome.getText().isBlank() || campoSenha.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Preencha nome e senha para criar sua conta.",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Conta criada com sucesso! Faça login para continuar.",
                "Echo Music",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new TelaLogin().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastro().setVisible(true));
    }
}
