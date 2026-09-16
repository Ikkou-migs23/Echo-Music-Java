package Tela;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import negocio.Playlist;

public class TelaCriarPlaylist extends JFrame {

	private JTextField txtNome = new JTextField();

	public TelaCriarPlaylist() {
		setTitle("Echo Music - Nova Playlist");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 150);
		setLocationRelativeTo(null);

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(10, 10, 10, 10));
		painel.setLayout(new GridLayout(2, 2, 5, 5));
		setContentPane(painel);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Tema.ROXO);
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaPrincipal().setVisible(true);
			}
		});

		painel.add(new JLabel("Nome da Playlist"));
		painel.add(txtNome);
		painel.add(btnSalvar);
		painel.add(btnCancelar);
	}

	private void salvar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Informe o nome da playlist.");
			return;
		}

		Playlist obj = new Playlist();
		obj.setNome(txtNome.getText());

		try {
			JOptionPane.showMessageDialog(this, obj.cadastrar());
			dispose();
			new TelaPrincipal().setVisible(true);
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
