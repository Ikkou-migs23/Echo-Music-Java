package Tela;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import negocio.Musica;
import negocio.Playlist;

public class TelaPlaylist extends JFrame {

	private Playlist playlist;
	private JPanel painelMusicas = new JPanel();
	private JTextField txtNomeMusica = new JTextField();
	private JTextField txtDuracao = new JTextField();

	public TelaPlaylist(Playlist playlist) {
		this.playlist = playlist;

		setTitle("Echo Music - " + playlist.getNome());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 550);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaPrincipal().setVisible(true);
			}
		});

		JButton btnExcluir = new JButton("Excluir Playlist");
		btnExcluir.setBackground(Tema.VERMELHO);
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirPlaylist();
			}
		});

		topo.add(new JLabel(playlist.getNome()));
		topo.add(btnVoltar);
		topo.add(btnExcluir);

		painelMusicas.setLayout(new BoxLayout(painelMusicas, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(painelMusicas);

		JPanel painelForm = new JPanel(new GridLayout(3, 2, 5, 5));
		painelForm.setBorder(new EmptyBorder(10, 10, 10, 10));

		JButton btnAdicionar = new JButton("Adicionar Música");
		btnAdicionar.setBackground(Tema.ROXO);
		btnAdicionar.setForeground(Color.WHITE);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarMusica();
			}
		});

		painelForm.add(new JLabel("Nome da Música"));
		painelForm.add(txtNomeMusica);
		painelForm.add(new JLabel("Duração"));
		painelForm.add(txtDuracao);
		painelForm.add(btnAdicionar);

		add(topo, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(painelForm, BorderLayout.SOUTH);

		carregarMusicas();
	}

	private void carregarMusicas() {
		painelMusicas.removeAll();

		try {
			List<Musica> lista = Musica.listarPorPlaylist(playlist.getIdPlaylist());

			for (final Musica musica : lista) {
				JPanel linha = new JPanel(new BorderLayout());
				linha.setBorder(new EmptyBorder(5, 10, 5, 10));

				JButton btnRemover = new JButton("Remover");
				btnRemover.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						removerMusica(musica);
					}
				});

				linha.add(new JLabel(musica.getNome() + " - " + musica.getDuracao()), BorderLayout.CENTER);
				linha.add(btnRemover, BorderLayout.EAST);
				painelMusicas.add(linha);
			}
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		painelMusicas.revalidate();
		painelMusicas.repaint();
	}

	private void adicionarMusica() {
		if (txtNomeMusica.getText().isEmpty() || txtDuracao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Preencha nome e duração.");
			return;
		}

		Musica obj = new Musica();
		obj.setNome(txtNomeMusica.getText());
		obj.setDuracao(txtDuracao.getText());
		obj.setIdPlaylist(playlist.getIdPlaylist());

		try {
			obj.cadastrar();
			txtNomeMusica.setText("");
			txtDuracao.setText("");
			carregarMusicas();
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void removerMusica(Musica musica) {
		try {
			musica.deletar();
			carregarMusicas();
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private void excluirPlaylist() {
		int confirmar = JOptionPane.showConfirmDialog(this,
				"Deseja excluir a playlist \"" + playlist.getNome() + "\"?");

		if (confirmar == JOptionPane.YES_OPTION) {
			try {
				playlist.deletar();
				dispose();
				new TelaPrincipal().setVisible(true);
			} catch (SQLException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}
