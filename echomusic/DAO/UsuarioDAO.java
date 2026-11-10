package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
	private int idUsuario;
	private String usuario;
	private String senha;

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
// --- CREATE ---
	public String cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("INSERT INTO usuarios (usuario, senha) VALUES (?, ?)");
		objeto.setParametros(this.usuario, this.senha);
		objeto.update();

		return "Cadastrado com Sucesso";
	}
// --- READ ---
	/** Devolve o usuário (com idUsuario) se nome e senha baterem; ResultSet vazio caso contrário. */
	public ResultSet logar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM usuarios WHERE usuario = ? AND senha = ?");
		objeto.setParametros(this.usuario, this.senha);

		return objeto.query();
	}
// --- READ ---
	public ResultSet consultar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM usuarios WHERE idUsuario = ?");
		objeto.setParametros(this.idUsuario);

		return objeto.query();
	}
// --- READ ---
	public ResultSet listar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("SELECT * FROM usuarios");

		return objeto.query();
	}
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("UPDATE usuarios SET usuario = ?, senha = ? WHERE idUsuario = ?");
		objeto.setParametros(this.usuario, this.senha, this.idUsuario);
		objeto.update();

		return "Usuario Alterado com Sucesso";
	}
// --- DELETE ---
	public String excluir() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		objeto.setSQL("DELETE FROM usuarios WHERE idUsuario = ?");
		objeto.setParametros(this.idUsuario);
		objeto.update();

		return "Usuario Removido com Sucesso";
	}
}
