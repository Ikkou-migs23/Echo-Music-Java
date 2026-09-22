package Negocio;

import java.sql.SQLException;
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
	public Usuario consultar(int id) throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setId(id);

		return objeto.consultar();
	}
// --- READ ---
	public List<Usuario> listar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();

		return objeto.listar();
	}
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setId(this.id);
		objeto.setUsuario(this.usuario);
		objeto.setSenha(this.senha);

		return objeto.alterar();
	}
// --- DELETE ---
	public String excluir(int id) throws SQLException, ClassNotFoundException {
		UsuarioDAO objeto = new UsuarioDAO();
		objeto.setId(id);

		return objeto.excluir();
	}
}