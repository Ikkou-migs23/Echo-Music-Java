package view;

import javax.swing.*;
import java.awt.*;

public class TelaLogin {

    public static void main(String[] args) {
        JFrame janela = new JFrame("Echo Music - Acessar sua Conta");
        janela.setSize(640, 390);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel logo = new JLabel("♫ Echo Music");
        logo.setFont(new Font("Arial", Font.BOLD, 26));
        logo.setForeground(new Color(120, 45, 200));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 8, 5);
        painel.add(logo, gbc);

        JLabel titulo = new JLabel("Acessar sua Conta");
        titulo.setFont(new Font("Arial", Font.BOLD, 15));

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 12, 5);
        painel.add(titulo, gbc);
       
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 1, 0);

        JLabel labelNome = new JLabel("Nome");
        labelNome.setFont(new Font("Arial", Font.PLAIN, 10));
        
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 0, 1, 0);
        painel.add(labelNome, gbc);
        
        JTextField nome = new JTextField("");
        nome.setPreferredSize(new Dimension(273, 22));
        nome.setFont(new Font("Arial", Font.PLAIN, 11));

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 6, 0);
        painel.add(nome, gbc);

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 10));

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 0, 1, 0);
        painel.add(labelSenha, gbc);

        JPanel painelSenha = new JPanel(new BorderLayout(4, 0));
        painelSenha.setBackground(Color.WHITE);

        JPasswordField senha = new JPasswordField();
        senha.setPreferredSize(new Dimension(245, 22));

        JButton visualizar = new JButton("👁");
        visualizar.setPreferredSize(new Dimension(25, 22));
        visualizar.setMargin(new Insets(0, 0, 0, 0));

        painelSenha.add(senha, BorderLayout.CENTER);
        painelSenha.add(visualizar, BorderLayout.EAST);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);
        painel.add(painelSenha, gbc);

        //JCheckBox lembrar = new JCheckBox("Lembrar de mim");
        //lembrar.setFont(new Font("Arial", Font.PLAIN, 10));
        //lembrar.setBackground(Color.WHITE);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 0, 8, 0);
        //painel.add(lembrar, gbc);

        JButton entrar = new JButton("ENTRAR");
        entrar.setPreferredSize(new Dimension(215, 22));
        entrar.setFont(new Font("Arial", Font.BOLD, 9));

        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0);
        painel.add(entrar, gbc);

        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);

        JPanel painelEsqueceu = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 8, 0)
        );
        painelEsqueceu.setBackground(Color.WHITE);

        JLabel esqueceu = new JLabel("Esqueceu a senha?");
        JLabel redefinir = new JLabel(
                "<html><font color='blue'><u>Redefinir aqui</u></font></html>"
        );

        esqueceu.setFont(new Font("Arial", Font.PLAIN, 9));
        redefinir.setFont(new Font("Arial", Font.PLAIN, 9));

        painelEsqueceu.add(esqueceu);
        painelEsqueceu.add(redefinir);

        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 6, 0);
        painel.add(painelEsqueceu, gbc);

        JPanel painelCadastro = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 8, 0)
        );
        painelCadastro.setBackground(Color.WHITE);

        JLabel naoTem = new JLabel("Não tem conta?");
        JLabel cadastrar = new JLabel(
                "<html><font color='blue'><u>Cadastre-se</u></font></html>"
        );

        naoTem.setFont(new Font("Arial", Font.PLAIN, 9));
        cadastrar.setFont(new Font("Arial", Font.PLAIN, 9));

        painelCadastro.add(naoTem);
        painelCadastro.add(cadastrar);

        gbc.gridy = 10;
        gbc.insets = new Insets(0, 0, 5, 0);
        painel.add(painelCadastro, gbc);

        janela.add(painel);
        janela.setVisible(true);
    }
}