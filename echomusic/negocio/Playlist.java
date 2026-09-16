package negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.PlaylistDAO;

public class Playlist {
	private int idPlaylist;
	private String nome;

	public int getIdPlaylist() {
		return idPlaylist;
	}
	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String cadastrar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setNome(this.nome);

		return objeto.cadastrar();
	}

	public String deletar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);

		return objeto.deletar();
	}

	public static List<Playlist> listar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		ResultSet rs = objeto.listar();
		List<Playlist> lista = new ArrayList<Playlist>();

		while (rs.next()) {
			Playlist playlist = new Playlist();
			playlist.setIdPlaylist(rs.getInt("idPlaylist"));
			playlist.setNome(rs.getString("nome"));
			lista.add(playlist);
		}

		return lista;
	}
}
