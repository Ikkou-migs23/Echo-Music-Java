package Tela;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import negocio.Usuario;

public class TelaCadastro extends JFrame {

	private JTextField txtUsuario = new JTextField();
	private JPasswordField txtSenha = new JPasswordField();

	public TelaCadastro() {
		setTitle("Echo Music - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 200);
		setLocationRelativeTo(null);

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(10, 10, 10, 10));
		painel.setLayout(new GridLayout(3, 2, 5, 5));
		setContentPane(painel);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBackground(Tema.ROXO);
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaLogin().setVisible(true);
			}
		});

		painel.add(new JLabel("Usuário"));
		painel.add(txtUsuario);
		painel.add(new JLabel("Senha"));
		painel.add(txtSenha);
		painel.add(btnCadastrar);
		painel.add(btnVoltar);
	}

	private void cadastrar() {
		if (txtUsuario.getText().isEmpty() || txtSenha.getPassword().length == 0) {
			JOptionPane.showMessageDialog(this, "Preencha usuário e senha.");
			return;
		}

		Usuario obj = new Usuario();
		obj.setUsuario(txtUsuario.getText());
		obj.setSenha(new String(txtSenha.getPassword()));

		try {
			JOptionPane.showMessageDialog(this, obj.cadastrar());
			dispose();
			new TelaLogin().setVisible(true);
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
