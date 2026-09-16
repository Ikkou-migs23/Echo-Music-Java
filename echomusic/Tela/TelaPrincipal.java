package Tela;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import negocio.Playlist;

public class TelaPrincipal extends JFrame {

	private JPanel painelLista = new JPanel();

	public TelaPrincipal() {
		setTitle("Echo Music - Suas Playlists");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton btnCriar = new JButton("Criar Playlist");
		btnCriar.setBackground(Tema.ROXO);
		btnCriar.setForeground(Color.WHITE);
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaCriarPlaylist().setVisible(true);
			}
		});

		JButton btnPerfil = new JButton("Perfil");
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaPerfil().setVisible(true);
			}
		});

		JButton btnSobre = new JButton("Sobre");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaSobre().setVisible(true);
			}
		});

		topo.add(btnCriar);
		topo.add(btnPerfil);
		topo.add(btnSobre);

		painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(painelLista);

		add(topo, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

		carregarPlaylists();
	}

	private void carregarPlaylists() {
		painelLista.removeAll();

		try {
			List<Playlist> lista = Playlist.listar();

			for (final Playlist playlist : lista) {
				JPanel linha = new JPanel(new BorderLayout());
				linha.setBorder(new EmptyBorder(5, 10, 5, 10));

				JButton btnAbrir = new JButton("Abrir");
				btnAbrir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						new TelaPlaylist(playlist).setVisible(true);
					}
				});

				linha.add(new JLabel(playlist.getNome()), BorderLayout.CENTER);
				linha.add(btnAbrir, BorderLayout.EAST);
				painelLista.add(linha);
			}
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		painelLista.revalidate();
		painelLista.repaint();
	}
}
