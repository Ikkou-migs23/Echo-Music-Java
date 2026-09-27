package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

	private String host = "jdbc:mysql://localhost:3306/echomusic?useSSL=false&serverTimezone=UTC";
	private String user = "root";
	private String pass = "";
	private String sql;

	public void setSQL(String sql) {
		this.sql = sql;
	}

	public ResultSet query() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conexao = DriverManager.getConnection(host, user, pass);
		Statement stmt = conexao.createStatement();

		// A conexão não pode ser fechada aqui: o ResultSet só pode ser lido
		// enquanto a conexão que o gerou continuar aberta.
		return stmt.executeQuery(this.sql);
	}

	public void update() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conexao = DriverManager.getConnection(host, user, pass);
		Statement stmt = conexao.createStatement();
		stmt.executeUpdate(this.sql);

		conexao.close();
	}
}
