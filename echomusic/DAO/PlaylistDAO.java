package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {
	private int idPlaylist;
	private int idUsuario;
	private String nome;
	private int cor;
	private String textoCapa;

	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCor(int cor) {
		this.cor = cor;
	}
	public void setTextoCapa(String textoCapa) {
		this.textoCapa = textoCapa;
	}
// --- CREATE ---
	/** Insere a playlist e devolve o idPlaylist gerado pelo banco. */
	public int cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("INSERT INTO playlists (idUsuario, nome, cor, textoCapa) VALUES (?, ?, ?, ?)");
		objeto.setParametros(this.idUsuario, this.nome, this.cor, this.textoCapa);

		return objeto.inserir();
	}
// --- READ ---
	public ResultSet consultar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM playlists WHERE idPlaylist = ?");
		objeto.setParametros(this.idPlaylist);

		return objeto.query();
	}
// --- READ ---
	public ResultSet listar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM playlists");

		return objeto.query();
	}
// --- READ ---
	public ResultSet listarPorUsuario() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM playlists WHERE idUsuario = ? ORDER BY idPlaylist");
		objeto.setParametros(this.idUsuario);

		return objeto.query();
	}
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("UPDATE playlists SET idUsuario = ?, nome = ?, cor = ?, textoCapa = ? WHERE idPlaylist = ?");
		objeto.setParametros(this.idUsuario, this.nome, this.cor, this.textoCapa, this.idPlaylist);
		objeto.update();

		return "Playlist Alterada com Sucesso";
	}
// --- DELETE ---
	/** As músicas da playlist são apagadas junto (ON DELETE CASCADE). */
	public String deletar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("DELETE FROM playlists WHERE idPlaylist = ?");
		objeto.setParametros(this.idPlaylist);
		objeto.update();

		return "Playlist Removida com Sucesso";
	}
}
