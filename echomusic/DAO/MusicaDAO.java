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
// --- CREATE ---
	public String cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("INSERT INTO musicas (nome, duracao, idPlaylist) VALUES (?, ?, ?)");
		objeto.setParametros(this.nome, this.duracao, this.idPlaylist);
		objeto.update();

		return "Musica Cadastrada com Sucesso";
	}
// --- READ ---
	public ResultSet consultar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM musicas WHERE idMusica = ?");
		objeto.setParametros(this.idMusica);

		return objeto.query();
	}
// --- READ ---
	public ResultSet listar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM musicas");

		return objeto.query();
	}
// --- READ ---
	public ResultSet listarPorPlaylist() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM musicas WHERE idPlaylist = ? ORDER BY idMusica");
		objeto.setParametros(this.idPlaylist);

		return objeto.query();
	}
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("UPDATE musicas SET nome = ?, duracao = ?, idPlaylist = ? WHERE idMusica = ?");
		objeto.setParametros(this.nome, this.duracao, this.idPlaylist, this.idMusica);
		objeto.update();

		return "Musica Alterada com Sucesso";
	}
// --- DELETE ---
	public String deletar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("DELETE FROM musicas WHERE idMusica = ?");
		objeto.setParametros(this.idMusica);
		objeto.update();

		return "Musica Removida com Sucesso";
	}
}
