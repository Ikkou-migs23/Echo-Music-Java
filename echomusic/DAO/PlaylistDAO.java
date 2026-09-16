package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {
	private int idPlaylist;
	private String nome;

	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "INSERT INTO playlists (nome) VALUES ('"+this.nome+"');";

		objeto.setSQL(sql);
		objeto.update();

		return "Playlist Cadastrada com Sucesso";
	}

	public ResultSet listar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM playlists;";

		objeto.setSQL(sql);

		return objeto.query();
	}

	public String deletar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "DELETE FROM playlists WHERE idPlaylist = "+this.idPlaylist+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Playlist Removida com Sucesso";
	}
}
