package Negocio;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.PlaylistDAO;

public class Playlist {
	private static int proximoId = 1;

	private int idPlaylist;
	private String nome;
	private Color cor;
	private String textoCapa;
	private List<Musica> musicas;

	public Playlist() {
	}

	/** Cria uma playlist em memória (usada pela tela de criar playlist), com id gerado automaticamente. */
	public Playlist(String nome, Color cor, String textoCapa, List<Musica> musicas) {
		this.idPlaylist = proximoId++;
		this.nome = nome;
		this.cor = cor;
		this.textoCapa = textoCapa;
		this.musicas = musicas;
	}

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
	public Color getCor() {
		return cor;
	}
	public void setCor(Color cor) {
		this.cor = cor;
	}
	public String getTextoCapa() {
		return textoCapa;
	}
	public void setTextoCapa(String textoCapa) {
		this.textoCapa = textoCapa;
	}
	public List<Musica> getMusicas() {
		return musicas;
	}
	public void setMusicas(List<Musica> musicas) {
		this.musicas = musicas;
	}
// --- CREATE ---
	public String cadastrar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setNome(this.nome);

		return objeto.cadastrar();
	}
// --- READ ---
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
// --- READ ---
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
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);
		objeto.setNome(this.nome);

		return objeto.alterar();
	}
// --- DELETE ---
	public String deletar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);

		return objeto.deletar();
	}
}