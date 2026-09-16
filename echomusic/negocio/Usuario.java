package negocio;

import java.sql.SQLException;

import DAO.UsuarioDAO;

public class Usuario {
	private String usuario;
	private String senha;

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

		return objeto.logar();
	}
}
