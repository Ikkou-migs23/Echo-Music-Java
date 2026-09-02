package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Tela de login. Ao autenticar com sucesso, abre a TelaPrincipal.
 * O link "Cadastre-se" leva à TelaCadastro.
 */
public class TelaLogin extends JFrame {

    private static final Color ROXO_LOGO = new Color(120, 45, 200);

    public TelaLogin() {
        setTitle("Echo Music - Acessar sua Conta");
        setSize(720, 470);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.BRANCO);
        raiz.setBorder(BorderFactory.createLineBorder(Tema.BORDA));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Tema.BRANCO);

        GridBagConstraints gbc = new GridBagConstraints();

        adicionarLogo(painel, gbc);
        adicionarTitulo(painel, gbc, "Acessar sua Conta");

        JTextField nome = adicionarCampoTexto(painel, gbc, "Nome");
        JPasswordField senha = adicionarCampoSenha(painel, gbc);
        JButton entrar = adicionarBotaoPrincipal(painel, gbc, "ENTRAR");
        entrar.addActionListener(e -> autenticar(nome, senha));

        adicionarLinkEsqueceuSenha(painel, gbc);
        adicionarLinkCadastro(painel, gbc);

        raiz.add(new BarraTitulo(this), BorderLayout.NORTH);
        raiz.add(painel, BorderLayout.CENTER);

        add(raiz);
    }

    private void autenticar(JTextField nome, JPasswordField senha) {
        // TODO: validar credenciais reais assim que houver um serviço de autenticação
        dispose();
        new TelaPrincipal().setVisible(true);
    }

    private void adicionarLogo(JPanel painel, GridBagConstraints gbc) {
        JLabel logo = new JLabel("♫ Echo Music");
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setForeground(ROXO_LOGO);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 5, 10, 5);
        painel.add(logo, gbc);
    }

    private void adicionarTitulo(JPanel painel, GridBagConstraints gbc, String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("Arial", Font.BOLD, 17));

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 16, 5);
        painel.add(titulo, gbc);
    }

    private JTextField adicionarCampoTexto(JPanel painel, GridBagConstraints gbc, String rotuloTexto) {
        JLabel rotulo = new JLabel(rotuloTexto);
        rotulo.setFont(new Font("Arial", Font.PLAIN, 11));

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
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
        visualizar.addActionListener(e -> alternarVisibilidadeSenha(senha, visualizar));

        painelSenha.add(senha, BorderLayout.CENTER);
        painelSenha.add(visualizar, BorderLayout.EAST);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 6, 0);
        painel.add(painelSenha, gbc);

        return senha;
    }

    private void alternarVisibilidadeSenha(JPasswordField senha, JButton botao) {
        boolean oculta = senha.getEchoChar() != 0;
        senha.setEchoChar(oculta ? (char) 0 : '•');
    }

    private JButton adicionarBotaoPrincipal(JPanel painel, GridBagConstraints gbc, String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(250, 30));
        botao.setFont(new Font("Arial", Font.BOLD, 11));
        botao.setBackground(Tema.AZUL);
        botao.setForeground(Tema.BRANCO);
        botao.setFocusPainted(false);

        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 12, 0);
        painel.add(botao, gbc);

        return botao;
    }

    private void adicionarLinkEsqueceuSenha(JPanel painel, GridBagConstraints gbc) {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        linha.setBackground(Tema.BRANCO);

        JLabel esqueceu = new JLabel("Esqueceu a senha?");
        esqueceu.setFont(new Font("Arial", Font.PLAIN, 10));

        JLabel redefinir = criarLinkAzul("Redefinir aqui");
        // TODO: abrir tela de redefinição de senha quando ela existir

        linha.add(esqueceu);
        linha.add(redefinir);

        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 8, 0);
        painel.add(linha, gbc);
    }

    private void adicionarLinkCadastro(JPanel painel, GridBagConstraints gbc) {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        linha.setBackground(Tema.BRANCO);

        JLabel naoTem = new JLabel("Não tem conta?");
        naoTem.setFont(new Font("Arial", Font.PLAIN, 10));

        JLabel cadastrar = criarLinkAzul("Cadastre-se");
        cadastrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaCadastro().setVisible(true);
            }
        });

        linha.add(naoTem);
        linha.add(cadastrar);

        gbc.gridy = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        painel.add(linha, gbc);
    }

    private JLabel criarLinkAzul(String texto) {
        JLabel link = new JLabel("<html><font color='blue'><u>" + texto + "</u></font></html>");
        link.setFont(new Font("Arial", Font.PLAIN, 10));
        link.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return link;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
