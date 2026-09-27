package Negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.UsuarioDAO;

public class Usuario {
	private int id;
	private String usuario;
	private String senha;

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
// --- CREATE ---
	public String cadastrar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setUsuario(this.usuario);
		objeto.setSenha(this.senha);

		return objeto.cadastrar();
	}
// --- READ ---
	public boolean logar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setUsuario(this.usuario);
		objeto.setSenha(this.senha);

		return objeto.logar();
	}
// --- READ ---
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
		}

		return usuario;
	}
// --- READ ---
	public static List<Usuario> listar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		ResultSet rs = objeto.listar();
		List<Usuario> lista = new ArrayList<Usuario>();

		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(rs.getInt("idUsuario"));
			usuario.setUsuario(rs.getString("usuario"));
			usuario.setSenha(rs.getString("senha"));
			lista.add(usuario);
		}

		return lista;
	}
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setIdUsuario(this.id);
		objeto.setUsuario(this.usuario);
		objeto.setSenha(this.senha);

		return objeto.alterar();
	}
// --- DELETE ---
	public String excluir(int id) throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setIdUsuario(id);

		return objeto.excluir();
	}
}