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

	// CREATE
	public String cadastrar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setNome(this.nome);

		return objeto.cadastrar();
	}

	// READ (buscar uma playlist por id)
	public static Playlist consultar(int idPlaylist) throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(idPlaylist);
		ResultSet rs = objeto.consultar();
		Playlist playlist = null;

		if (rs.next()) {
			playlist = new Playlist();
			playlist.setIdPlaylist(rs.getInt("idPlaylist"));
			playlist.setNome(rs.getString("nome"));
		}

		return playlist;
	}

	// READ (listar todas)
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

	// UPDATE
	public String alterar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);
		objeto.setNome(this.nome);

		return objeto.alterar();
	}

	// DELETE
	public String deletar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);

		return objeto.deletar();
	}
}