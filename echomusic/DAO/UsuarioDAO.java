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
		String sql = "INSERT INTO usuarios (usuario, senha) "
					+"VALUES ('"+this.usuario+"','"+this.senha+"');";

		objeto.setSQL(sql);
		objeto.update();

		return "Cadastrado com Sucesso";
	}
// --- READ ---
	public boolean logar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM usuarios WHERE usuario = '"+this.usuario+"' "
					+"AND senha = '"+this.senha+"';";

		objeto.setSQL(sql);
		ResultSet rs = objeto.query();

		return rs.next();
	}
// --- READ ---
	public ResultSet consultar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM usuarios WHERE idUsuario = "+this.idUsuario+";";

		objeto.setSQL(sql);

		return objeto.query();
	}
// --- READ ---
	public ResultSet listar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM usuarios;";

		objeto.setSQL(sql);

		return objeto.query();
	}
// --- UPDATE ---
	public String alterar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "UPDATE usuarios SET usuario = '"+this.usuario+"', "
					+"senha = '"+this.senha+"' "
					+"WHERE idUsuario = "+this.idUsuario+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Usuario Alterado com Sucesso";
	}
// --- DELETE ---
	public String excluir() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "DELETE FROM usuarios WHERE idUsuario = "+this.idUsuario+";";

		objeto.setSQL(sql);
		objeto.update();

		return "Usuario Removido com Sucesso";
	}
}