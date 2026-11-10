package Negocio;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.PlaylistDAO;

public class Playlist {
	private static final Color[] CORES_CAPA = {
		new Color(105, 65, 180), new Color(224, 62, 76), new Color(38, 132, 255),
		new Color(0, 150, 136), new Color(245, 124, 0), new Color(233, 30, 99),
		new Color(76, 175, 80), new Color(96, 125, 139)
	};

	private int idPlaylist;
	private int idUsuario;
	private String nome;
	private Color cor;
	private String textoCapa;
	private List<Musica> musicas = new ArrayList<Musica>();

	public Playlist() {
	}

	public int getIdPlaylist() {
		return idPlaylist;
	}
	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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
		this.musicas = (musicas == null) ? new ArrayList<Musica>() : musicas;
	}

	/** Monta uma Playlist a partir da linha atual do ResultSet (sem carregar as músicas). */
	private static Playlist deLinha(ResultSet rs) throws SQLException {
		Playlist playlist = new Playlist();
		playlist.setIdPlaylist(rs.getInt("idPlaylist"));
		playlist.setIdUsuario(rs.getInt("idUsuario"));
		playlist.setNome(rs.getString("nome"));
		playlist.setCor(new Color(rs.getInt("cor")));
		playlist.setTextoCapa(rs.getString("textoCapa"));
		if (playlist.getTextoCapa() == null) {
			playlist.setTextoCapa(gerarTextoCapa(playlist.getNome()));
		}
		return playlist;
	}

	/** Iniciais do nome da playlist (até 3 letras), cabendo em VARCHAR(10). */
	private static String gerarTextoCapa(String nome) {
		StringBuilder sb = new StringBuilder();
		for (String palavra : nome.trim().split("\\s+")) {
			if (!palavra.isEmpty() && sb.length() < 3) {
				sb.append(Character.toUpperCase(palavra.charAt(0)));
			}
		}
		return sb.length() == 0 ? "♫" : sb.toString();
	}
// --- CREATE ---
	/** Salva a playlist e todas as músicas dela no banco. */
	public String cadastrar() throws SQLException, ClassNotFoundException {
		if (cor == null) {
			cor = CORES_CAPA[(int) (Math.random() * CORES_CAPA.length)];
		}
		if (textoCapa == null || textoCapa.isBlank()) {
			textoCapa = gerarTextoCapa(nome);
		}

		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdUsuario(this.idUsuario);
		objeto.setNome(this.nome);
		objeto.setCor(this.cor.getRGB() & 0xFFFFFF);
		objeto.setTextoCapa(this.textoCapa);
		this.idPlaylist = objeto.cadastrar();

		for (Musica musica : musicas) {
			musica.setIdPlaylist(this.idPlaylist);
			musica.cadastrar();
		}

		return "Playlist Cadastrada com Sucesso";
	}
// --- READ ---
	public static Playlist consultar(int idPlaylist) throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(idPlaylist);
		ResultSet rs = objeto.consultar();
		Playlist playlist = null;

		if (rs.next()) {
			playlist = deLinha(rs);
			playlist.setMusicas(Musica.listarPorPlaylist(playlist.getIdPlaylist()));
		}

		return playlist;
	}
// --- READ ---
	public static List<Playlist> listar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		ResultSet rs = objeto.listar();
		List<Playlist> lista = new ArrayList<Playlist>();

		while (rs.next()) {
			Playlist playlist = deLinha(rs);
			playlist.setMusicas(Musica.listarPorPlaylist(playlist.getIdPlaylist()));
			lista.add(playlist);
		}

		return lista;
	}
// --- READ ---
	/** Playlists do usuário, já com as músicas carregadas. */
	public static List<Playlist> listarPorUsuario(int idUsuario) throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdUsuario(idUsuario);
		ResultSet rs = objeto.listarPorUsuario();
		List<Playlist> lista = new ArrayList<Playlist>();

		while (rs.next()) {
			Playlist playlist = deLinha(rs);
			playlist.setMusicas(Musica.listarPorPlaylist(playlist.getIdPlaylist()));
			lista.add(playlist);
		}

		return lista;
	}
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);
		objeto.setIdUsuario(this.idUsuario);
		objeto.setNome(this.nome);
		objeto.setCor(this.cor == null ? 0 : (this.cor.getRGB() & 0xFFFFFF));
		objeto.setTextoCapa(this.textoCapa);

		return objeto.alterar();
	}
// --- DELETE ---
	public String deletar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);

		return objeto.deletar();
	}
}
