package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicaDAO {
	private int idMusica;
	private String nome;
	private String duracao;
	private int idPlaylist;

	public void setIdMusica(int idMusica) {
		this.idMusica = idMusica;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}

	// CREATE
	public String cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "INSERT INTO musicas (nome, duracao, idPlaylist) "
					+"VALUES ('"+this.nome+"','"+this.duracao+"',"+this.idPlaylist+");";

		objeto.setSQL(sql);
		objeto.update();

		return "Musica Cadastrada com Sucesso";
	}

	// READ (buscar uma musica por id)
	public ResultSet consultar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM musicas WHERE idMusica = "+this.idMusica+";";

		objeto.setSQL(sql);

		return objeto.query();
	}

	// READ (listar todas)
	public ResultSet listar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM musicas;";

		objeto.setSQL(sql);

		return objeto.query();
	}

	// READ (listar por playlist)
	public ResultSet listarPorPlaylist() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM musicas WHERE idPlaylist = "+this.idPlaylist+";";

		objeto.setSQL(sql);

		return objeto.query();
	}

	// UPDATE
	public String alterar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "UPDATE musicas SET nome = '"+this.nome+"', "
					+"duracao = '"+this.duracao+"', "
					+"idPlaylist = "+this.idPlaylist+" "
					+"WHERE idMusica = "+this.idMusica+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Musica Alterada com Sucesso";
	}

	// DELETE
	public String deletar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "DELETE FROM musicas WHERE idMusica = "+this.idMusica+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Musica Removida com Sucesso";
	}
}