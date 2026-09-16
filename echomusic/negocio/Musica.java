package negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.MusicaDAO;

public class Musica {
	private int idMusica;
	private String nome;
	private String duracao;
	private int idPlaylist;

	public int getIdMusica() {
		return idMusica;
	}
	public void setIdMusica(int idMusica) {
		this.idMusica = idMusica;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public int getIdPlaylist() {
		return idPlaylist;
	}
	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}

	public String cadastrar() throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		objeto.setNome(this.nome);
		objeto.setDuracao(this.duracao);
		objeto.setIdPlaylist(this.idPlaylist);

		return objeto.cadastrar();
	}

	public String deletar() throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		objeto.setIdMusica(this.idMusica);

		return objeto.deletar();
	}

	public static List<Musica> listarPorPlaylist(int idPlaylist) throws SQLException, ClassNotFoundException {
		MusicaDAO objeto = new MusicaDAO();
		objeto.setIdPlaylist(idPlaylist);
		ResultSet rs = objeto.listarPorPlaylist();
		List<Musica> lista = new ArrayList<Musica>();

		while (rs.next()) {
			Musica musica = new Musica();
			musica.setIdMusica(rs.getInt("idMusica"));
			musica.setNome(rs.getString("nome"));
			musica.setDuracao(rs.getString("duracao"));
			musica.setIdPlaylist(idPlaylist);
			lista.add(musica);
		}

		return lista;
	}
}
