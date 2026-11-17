package Negocio;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.PlaylistDAO;
import DAO.SupabaseStorage;

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
	private String capaNuvem;
	private File capaEscolhida;
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
	public String getCapaNuvem() {
		return capaNuvem;
	}
	public void setCapaNuvem(String capaNuvem) {
		this.capaNuvem = capaNuvem;
	}
	public void setCapaEscolhida(File capaEscolhida) {
		this.capaEscolhida = capaEscolhida;
	}
	public List<Musica> getMusicas() {
		return musicas;
	}
	public void setMusicas(List<Musica> musicas) {
		this.musicas = (musicas == null) ? new ArrayList<Musica>() : musicas;
	}

	private static Playlist deLinha(ResultSet rs) throws SQLException {
		Playlist playlist = new Playlist();
		playlist.setIdPlaylist(rs.getInt("idPlaylist"));
		playlist.setIdUsuario(rs.getInt("idUsuario"));
		playlist.setNome(rs.getString("nome"));
		playlist.setCor(new Color(rs.getInt("cor")));
		playlist.setTextoCapa(rs.getString("textoCapa"));
		playlist.setCapaNuvem(rs.getString("capaNuvem"));
		if (playlist.getTextoCapa() == null) {
			playlist.setTextoCapa(gerarTextoCapa(playlist.getNome()));
		}
		return playlist;
	}

	private static String gerarTextoCapa(String nome) {
		StringBuilder sb = new StringBuilder();
		for (String palavra : nome.trim().split("\\s+")) {
			if (!palavra.isEmpty() && sb.length() < 3) {
				sb.append(Character.toUpperCase(palavra.charAt(0)));
			}
		}
		return sb.toString();
	}
	public String cadastrar() throws SQLException, ClassNotFoundException, IOException {
		if (cor == null) {
			cor = CORES_CAPA[(int) (Math.random() * CORES_CAPA.length)];
		}
		if (textoCapa == null || textoCapa.isBlank()) {
			textoCapa = gerarTextoCapa(nome);
		}

		String chaveCapa = enviarCapa();

		try {
			PlaylistDAO objeto = new PlaylistDAO();
			objeto.setIdUsuario(this.idUsuario);
			objeto.setNome(this.nome);
			objeto.setCor(this.cor.getRGB() & 0xFFFFFF);
			objeto.setTextoCapa(this.textoCapa);
			objeto.setCapaNuvem(chaveCapa);
			this.idPlaylist = objeto.cadastrar();
		} catch (SQLException | ClassNotFoundException | RuntimeException e) {
			apagarCapa(chaveCapa);
			throw e;
		}
		this.capaNuvem = chaveCapa;
		this.capaEscolhida = null;

		List<Musica> salvas = new ArrayList<Musica>();
		try {
			for (Musica musica : musicas) {
				musica.setIdPlaylist(this.idPlaylist);
				musica.cadastrar();
				salvas.add(musica);
			}
		} catch (SQLException | ClassNotFoundException | IOException | RuntimeException e) {
			for (Musica musica : salvas) {
				musica.desfazerCadastro();
			}
			try {
				PlaylistDAO desfazer = new PlaylistDAO();
				desfazer.setIdPlaylist(this.idPlaylist);
				desfazer.deletar();
			} catch (Exception ignorar) {
			}
			apagarCapa(chaveCapa);
			this.capaNuvem = null;
			throw e;
		}

		return "Playlist Cadastrada com Sucesso";
	}

	private String enviarCapa() throws IOException {
		if (capaEscolhida == null) return null;
		if (!SupabaseStorage.configurado()) {
			System.err.println("supabase.properties não encontrado: a capa da playlist não foi salva.");
			return null;
		}

		Path preparada = FotoPerfil.prepararImagem(capaEscolhida);
		try {
			String chave = "capas/" + (idUsuario > 0 ? idUsuario : 0) + "-" + System.currentTimeMillis() + ".jpg";
			SupabaseStorage.enviar(preparada, chave, "image/jpeg");

			try {
				Path copia = CapaPlaylist.arquivoLocal(chave);
				Files.createDirectories(copia.getParent());
				Files.copy(preparada, copia, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException | RuntimeException e) {
				System.err.println("Não foi possível guardar a cópia local da capa: " + e.getMessage());
			}
			return chave;
		} finally {
			Files.deleteIfExists(preparada);
		}
	}

	private static void apagarCapa(String chave) {
		if (chave == null || chave.isBlank()) return;
		try {
			Path local = CapaPlaylist.arquivoLocal(chave);
			if (local != null) Files.deleteIfExists(local);
		} catch (IOException e) {
			System.err.println("Não foi possível apagar a capa local: " + e.getMessage());
		}
		if (!SupabaseStorage.configurado()) return;
		try {
			SupabaseStorage.apagar(chave);
		} catch (IOException e) {
			System.err.println("A capa continua na nuvem: " + e.getMessage());
		}
	}

	public Path arquivoCapa() throws IOException {
		Path arquivo = CapaPlaylist.arquivoLocal(this.capaNuvem);
		if (arquivo == null) return null;

		synchronized (CapaPlaylist.class) {
			if (!Files.isRegularFile(arquivo)) {
				SupabaseStorage.baixar(this.capaNuvem, arquivo);
			}
		}
		return arquivo;
	}
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
	public String alterar() throws SQLException, ClassNotFoundException {
		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);
		objeto.setIdUsuario(this.idUsuario);
		objeto.setNome(this.nome);
		objeto.setCor(this.cor == null ? 0 : (this.cor.getRGB() & 0xFFFFFF));
		objeto.setTextoCapa(this.textoCapa);
		objeto.setCapaNuvem(this.capaNuvem);

		return objeto.alterar();
	}
	public String deletar() throws SQLException, ClassNotFoundException {
		List<Musica> arquivos = new ArrayList<Musica>(this.musicas);
		if (arquivos.isEmpty()) {
			arquivos = Musica.listarPorPlaylist(this.idPlaylist);
		}

		PlaylistDAO objeto = new PlaylistDAO();
		objeto.setIdPlaylist(this.idPlaylist);
		String resposta = objeto.deletar();

		Musica.apagarArquivos(arquivos);
		apagarCapa(this.capaNuvem);

		return resposta;
	}
}
