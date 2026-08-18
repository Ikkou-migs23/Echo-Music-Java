package DAO;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro {

	public static void main(String[] args) {

		        JFrame janela = new JFrame("Echo Music - Acessar sua Conta");
		        janela.setSize(800, 600);
		        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        janela.setLocationRelativeTo(null);

		        JPanel painel = new JPanel();
		        painel.setLayout(new GridLayout(12, 2, 10, 10));
		        painel.setBorder(BorderFactory.createEmptyBorder(30, 180, 30, 180));

		        JLabel logo = new JLabel("♫ Echo Music", SwingConstants.CENTER);
		        logo.setFont(new Font("Arial", Font.BOLD, 26));
		        logo.setForeground(new Color(120, 45, 200));

		        JLabel titulo = new JLabel("Acessar sua Conta", SwingConstants.CENTER);
		        titulo.setFont(new Font("Arial", Font.BOLD, 16));

		        JLabel labelUsuario = new JLabel("E-mail");
		        JTextField campoUsuario = new JTextField();
		        //labelUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		        JLabel labelSenha = new JLabel("Senha");
		        JPasswordField campoSenha = new JPasswordField();

		        JCheckBox lembrar = new JCheckBox("Lembrar de mim");

		        JButton entrar = new JButton("ENTRAR");

		        JLabel ou = new JLabel("ou", SwingConstants.CENTER);

		        JLabel esqueceu = new JLabel("Esqueceu a senha?");
		        JLabel redefinir = new JLabel(
		                "<html><font color='blue'><u>Redefinir aqui (RF10)</u></font></html>"
		        );

		        JLabel naoTem = new JLabel("Não tem conta?");
		        JLabel cadastrar = new JLabel(
		                "<html><font color='blue'><u>Cadastre-se (RF01)</u></font></html>"
		        );

		        painel.add(logo);
		        painel.add(new JLabel(""));

		        painel.add(titulo);
		        painel.add(new JLabel(""));

		        painel.add(labelUsuario);
		        painel.add(campoUsuario);

		        painel.add(labelSenha);
		        painel.add(campoSenha);

		        painel.add(lembrar);
		        painel.add(new JLabel(""));

		        painel.add(entrar);
		        painel.add(new JLabel(""));

		        painel.add(ou);
		        painel.add(new JLabel(""));

		        painel.add(esqueceu);
		        painel.add(redefinir);

		        painel.add(naoTem);
		        painel.add(cadastrar);

		        janela.add(painel);

		        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
		        janela.setVisible(true);
		
	}
	
}