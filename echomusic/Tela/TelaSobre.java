package Tela;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TelaSobre extends JFrame {

	public TelaSobre() {
		setTitle("Echo Music - Sobre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 220);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("Sobre o Echo Music");
		lblTitulo.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel lblTexto = new JLabel(
				"<html><div style='width:350px;padding:10px;'>Echo Music é um projeto de estudo "
				+"desenvolvido em Java com Swing, criado para simular a organização de "
				+"playlists e músicas.<br><br>Versão: 1.0.0</div></html>");

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaPrincipal().setVisible(true);
			}
		});

		add(lblTitulo, BorderLayout.NORTH);
		add(lblTexto, BorderLayout.CENTER);
		add(btnVoltar, BorderLayout.SOUTH);
	}
}
