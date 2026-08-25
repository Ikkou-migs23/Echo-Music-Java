package DAO;

import javax.swing.*;
import java.awt.*;

public class TelaLogin {

    public static void main(String[] args) {

        JFrame janela = new JFrame("Echo Music - Acessar sua Conta");

        janela.setResizable(false);
        janela.setSize(640, 390);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        // Painel principal
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);

        // =========================
        // LOGO
        // =========================

        JLabel logo = new JLabel("Echo Music");
        logo.setFont(new Font("Arial", Font.BOLD, 26));
        logo.setForeground(new Color(120, 45, 200));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 8, 5);

        painel.add(logo, gbc);


        // =========================
        // TÍTULO
        // =========================

        JLabel titulo = new JLabel("Acessar sua Conta");
        titulo.setFont(new Font("Arial", Font.BOLD, 15));

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 12, 5);

        painel.add(titulo, gbc);


        // =========================
        // E-MAIL
        // =========================

        JLabel labelUsuario = new JLabel("E-mail");
        labelUsuario.setFont(new Font("Arial", Font.PLAIN, 10));

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 1, 0);

        painel.add(labelUsuario, gbc);


        JTextField campoUsuario = new JTextField("usuario@email.com");
        campoUsuario.setPreferredSize(new Dimension(273, 22));
        campoUsuario.setFont(new Font("Arial", Font.PLAIN, 11));

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 6, 0);

        painel.add(campoUsuario, gbc);


        // =========================
        // SENHA
        // =========================

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 10));

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 1, 0);

        painel.add(labelSenha, gbc);


        // Campo de senha + botão visualizar
        JPanel painelSenha = new JPanel(new BorderLayout(4, 0));
        painelSenha.setBackground(Color.WHITE);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setPreferredSize(new Dimension(245, 22));

        JButton visualizarSenha = new JButton("👁");
        visualizarSenha.setPreferredSize(new Dimension(25, 22));
        visualizarSenha.setMargin(new Insets(0, 0, 0, 0));

        painelSenha.add(campoSenha, BorderLayout.CENTER);
        painelSenha.add(visualizarSenha, BorderLayout.EAST);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);

        painel.add(painelSenha, gbc);


        // =========================
        // LEMBRAR DE MIM
        // =========================

        JCheckBox lembrar = new JCheckBox("Lembrar de mim");
        lembrar.setFont(new Font("Arial", Font.PLAIN, 10));
        lembrar.setBackground(Color.WHITE);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 8, 0);

        painel.add(lembrar, gbc);


        // =========================
        // BOTÃO ENTRAR
        // =========================

        JButton entrar = new JButton("ENTRAR");
        entrar.setPreferredSize(new Dimension(215, 22));
        entrar.setFont(new Font("Arial", Font.BOLD, 9));

        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0);

        painel.add(entrar, gbc);


        // =========================
        // "OU"
        // =========================

        JPanel painelOu = new JPanel(new BorderLayout(8, 0));
        painelOu.setBackground(Color.WHITE);

        JSeparator linhaEsquerda = new JSeparator();
        JSeparator linhaDireita = new JSeparator();

        JLabel ou = new JLabel("ou");
        ou.setFont(new Font("Arial", Font.PLAIN, 10));

        painelOu.add(linhaEsquerda, BorderLayout.WEST);
        painelOu.add(ou, BorderLayout.CENTER);
        painelOu.add(linhaDireita, BorderLayout.EAST);

        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);

        painel.add(painelOu, gbc);


        // =========================
        // ESQUECEU A SENHA
        // =========================

        JPanel painelEsqueceu = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        painelEsqueceu.setBackground(Color.WHITE);

        JLabel esqueceu = new JLabel("Esqueceu a senha?");

        JLabel redefinir = new JLabel(
            "<html><font color='blue'><u>Redefinir aqui (RF10)</u></font></html>"
        );

        esqueceu.setFont(new Font("Arial", Font.PLAIN, 9));
        redefinir.setFont(new Font("Arial", Font.PLAIN, 9));

        painelEsqueceu.add(esqueceu);
        painelEsqueceu.add(redefinir);

        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 6, 0);

        painel.add(painelEsqueceu, gbc);


        // =========================
        // CADASTRO
        // =========================

        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        painelCadastro.setBackground(Color.WHITE);

        JLabel naoTem = new JLabel("Não tem conta?");

        JLabel cadastrar = new JLabel(
            "<html><font color='blue'><u>Cadastre-se (RF01)</u></font></html>"
        );

        naoTem.setFont(new Font("Arial", Font.PLAIN, 9));
        cadastrar.setFont(new Font("Arial", Font.PLAIN, 9));

        painelCadastro.add(naoTem);
        painelCadastro.add(cadastrar);

        gbc.gridy = 10;
        gbc.insets = new Insets(0, 0, 5, 0);

        painel.add(painelCadastro, gbc);


        // =========================
        // EXIBIR JANELA
        // =========================

        janela.add(painel);
        janela.setVisible(true);
    }
}
