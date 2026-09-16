package Tela;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import negocio.Usuario;

public class TelaLogin extends JFrame {

	private JTextField txtUsuario = new JTextField();
	private JPasswordField txtSenha = new JPasswordField();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TelaLogin frame = new TelaLogin();
				frame.setVisible(true);
			}
		});
	}

	public TelaLogin() {
		setTitle("Echo Music - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 200);
		setLocationRelativeTo(null);

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(10, 10, 10, 10));
		painel.setLayout(new GridLayout(3, 2, 5, 5));
		setContentPane(painel);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBackground(Tema.ROXO);
		btnEntrar.setForeground(Color.WHITE);
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entrar();
			}
		});

		JButton btnCadastrar = new JButton("Cadastrar-se");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaCadastro().setVisible(true);
			}
		});

		painel.add(new JLabel("Usuário"));
		painel.add(txtUsuario);
		painel.add(new JLabel("Senha"));
		painel.add(txtSenha);
		painel.add(btnEntrar);
		painel.add(btnCadastrar);
	}

	private void entrar() {
		Usuario obj = new Usuario();
		obj.setUsuario(txtUsuario.getText());
		obj.setSenha(new String(txtSenha.getPassword()));

		try {
			if (obj.logar()) {
				dispose();
				new TelaPrincipal().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
			}
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
