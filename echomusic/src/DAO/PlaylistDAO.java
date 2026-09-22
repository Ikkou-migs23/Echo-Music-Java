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

	// CREATE
	public String cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "INSERT INTO playlists (nome) VALUES ('"+this.nome+"');";

		objeto.setSQL(sql);
		objeto.update();

		return "Playlist Cadastrada com Sucesso";
	}

	// READ (buscar uma playlist por id)
	public ResultSet consultar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM playlists WHERE idPlaylist = "+this.idPlaylist+";";

		objeto.setSQL(sql);

		return objeto.query();
	}

	// READ (listar todas)
	public ResultSet listar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM playlists;";

		objeto.setSQL(sql);

		return objeto.query();
	}

	// UPDATE
	public String alterar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "UPDATE playlists SET nome = '"+this.nome+"' "
					+"WHERE idPlaylist = "+this.idPlaylist+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Playlist Alterada com Sucesso";
	}

	// DELETE
	public String deletar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "DELETE FROM playlists WHERE idPlaylist = "+this.idPlaylist+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Playlist Removida com Sucesso";
	}
}