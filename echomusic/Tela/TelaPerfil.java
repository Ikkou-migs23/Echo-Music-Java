package Tela;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaPerfil extends JFrame {

	public TelaPerfil() {
		setTitle("Echo Music - Perfil");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 180);
		setLocationRelativeTo(null);

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(10, 10, 10, 10));
		painel.setLayout(new GridLayout(3, 2, 5, 5));
		setContentPane(painel);

		JTextField txtUsuario = new JTextField();
		JPasswordField txtSenha = new JPasswordField();

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Tema.ROXO);
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(TelaPerfil.this, "Alterações salvas com sucesso!");
			}
		});

		JButton btnSair = new JButton("Sair");
		btnSair.setBackground(Tema.VERMELHO);
		btnSair.setForeground(Color.WHITE);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaLogin().setVisible(true);
			}
		});

		painel.add(new JLabel("Usuário"));
		painel.add(txtUsuario);
		painel.add(new JLabel("Nova Senha"));
		painel.add(txtSenha);
		painel.add(btnSalvar);
		painel.add(btnSair);
	}
}
