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

	public String cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "INSERT INTO musicas (nome, duracao, idPlaylist) "
					+"VALUES ('"+this.nome+"','"+this.duracao+"',"+this.idPlaylist+");";

		objeto.setSQL(sql);
		objeto.update();

		return "Musica Cadastrada com Sucesso";
	}

	public ResultSet listarPorPlaylist() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM musicas WHERE idPlaylist = "+this.idPlaylist+";";

		objeto.setSQL(sql);

		return objeto.query();
	}

	public String deletar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "DELETE FROM musicas WHERE idMusica = "+this.idMusica+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Musica Removida com Sucesso";
	}
}
