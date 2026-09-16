package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
	private String usuario;
	private String senha;

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String cadastrar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "INSERT INTO usuarios (usuario, senha) "
					+"VALUES ('"+this.usuario+"','"+this.senha+"');";

		objeto.setSQL(sql);
		objeto.update();

		return "Cadastrado com Sucesso";
	}

	public boolean logar() throws SQLException, ClassNotFoundException {
		Conexao objeto = new Conexao();
		String sql = "SELECT * FROM usuarios WHERE usuario = '"+this.usuario+"' "
					+"AND senha = '"+this.senha+"';";

		objeto.setSQL(sql);
		ResultSet rs = objeto.query();

		return rs.next();
	}
}
