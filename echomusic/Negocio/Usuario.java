package Negocio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.SupabaseStorage;
import DAO.UsuarioDAO;

public class Usuario {
	private int id;
	private String usuario;
	private String senha;
	private String fotoNuvem;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getFotoNuvem() {
		return fotoNuvem;
	}
	public void setFotoNuvem(String fotoNuvem) {
		this.fotoNuvem = fotoNuvem;
	}

	private static String lerFoto(ResultSet rs) {
		try {
			return rs.getString("fotoNuvem");
		} catch (SQLException e) {
			return null;
		}
	}
	public String cadastrar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setUsuario(this.usuario);
		objeto.setSenha(this.senha);

		return objeto.cadastrar();
	}

	public boolean logar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setUsuario(this.usuario);
		objeto.setSenha(this.senha);
		ResultSet rs = objeto.logar();

		if (rs.next()) {
			this.id = rs.getInt("idUsuario");
			this.fotoNuvem = lerFoto(rs);
			return true;
		}
		return false;
	}
	public static Usuario consultar(int id) throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setIdUsuario(id);
		ResultSet rs = objeto.consultar();
		Usuario usuario = null;

		if (rs.next()) {
			usuario = new Usuario();
			usuario.setId(rs.getInt("idUsuario"));
			usuario.setUsuario(rs.getString("usuario"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setFotoNuvem(lerFoto(rs));
		}

		return usuario;
	}
	public static List<Usuario> listar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		ResultSet rs = objeto.listar();
		List<Usuario> lista = new ArrayList<Usuario>();

		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(rs.getInt("idUsuario"));
			usuario.setUsuario(rs.getString("usuario"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setFotoNuvem(lerFoto(rs));
			lista.add(usuario);
		}

		return lista;
	}
	public String alterar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setIdUsuario(this.id);
		objeto.setUsuario(this.usuario);
		objeto.setSenha(this.senha);

		return objeto.alterar();
	}
	public void salvarFoto(File imagem) throws IOException, SQLException, ClassNotFoundException {
		Path preparada = FotoPerfil.prepararImagem(imagem);
		String chaveAntiga = this.fotoNuvem;
		String chaveNova = null;
		Path copia = null;

		try {
			if (SupabaseStorage.configurado()) {
				chaveNova = "perfil/" + this.id + "-" + System.currentTimeMillis() + ".jpg";
				SupabaseStorage.enviar(preparada, chaveNova, "image/jpeg");
			} else {
				System.err.println("supabase.properties não encontrado: a foto fica só neste computador.");
			}

			copia = FotoPerfil.arquivoLocal(this.id, chaveNova);
			Files.createDirectories(copia.getParent());
			Files.copy(preparada, copia, StandardCopyOption.REPLACE_EXISTING);

			UsuarioDAO objeto = new UsuarioDAO();
			objeto.setIdUsuario(this.id);
			objeto.alterarFoto(chaveNova);
		} catch (IOException | SQLException | ClassNotFoundException | RuntimeException e) {
			if (chaveNova != null) {
				try {
					SupabaseStorage.apagar(chaveNova);
				} catch (IOException ignorar) {
				}
			}
			if (copia != null) {
				Files.deleteIfExists(copia);
			}
			throw e;
		} finally {
			Files.deleteIfExists(preparada);
		}

		this.fotoNuvem = chaveNova;
		apagarFotoAntiga(chaveAntiga, chaveNova);
	}

	private void apagarFotoAntiga(String chaveAntiga, String chaveNova) {
		try {
			Path antigoLocal = FotoPerfil.arquivoLocal(this.id, chaveAntiga);
			if (antigoLocal != null && !antigoLocal.equals(FotoPerfil.arquivoLocal(this.id, chaveNova))) {
				Files.deleteIfExists(antigoLocal);
			}
			if (chaveAntiga != null && !chaveAntiga.isBlank() && SupabaseStorage.configurado()) {
				SupabaseStorage.apagar(chaveAntiga);
			}
		} catch (IOException e) {
			System.err.println("Não foi possível apagar a foto anterior: " + e.getMessage());
		}
	}

	public Path arquivoFoto() throws IOException {
		Path arquivo = FotoPerfil.arquivoLocal(this.id, this.fotoNuvem);
		if (arquivo == null) return null;

		if (!Files.isRegularFile(arquivo)) {
			if (this.fotoNuvem == null || this.fotoNuvem.isBlank()) return null;
			SupabaseStorage.baixar(this.fotoNuvem, arquivo);
		}
		return arquivo;
	}
	public String excluir(int id) throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setIdUsuario(id);

		return objeto.excluir();
	}
}
