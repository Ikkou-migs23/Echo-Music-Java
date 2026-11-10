package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class Conexao {

	private String host = "jdbc:mysql://localhost:3306/echomusic?useSSL=false&serverTimezone=UTC";
	private String user = "root";
	private String pass = "";
	private String sql;
	private Object[] parametros = new Object[0];

	/** Define o SQL; use "?" nos valores e passe-os em setParametros (evita SQL injection e problemas com aspas). */
	public void setSQL(String sql) {
		this.sql = sql;
	}

	public void setParametros(Object... parametros) {
		this.parametros = parametros;
	}

	private Connection abrir() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(host, user, pass);
	}

	private void preencher(PreparedStatement stmt) throws SQLException {
		for (int i = 0; i < parametros.length; i++) {
			stmt.setObject(i + 1, parametros[i]);
		}
	}

	/**
	 * Executa um SELECT. O resultado é copiado para um CachedRowSet (desconectado),
	 * então a conexão pode ser fechada aqui sem vazar e o ResultSet continua legível.
	 */
	public ResultSet query() throws SQLException, ClassNotFoundException {
		try (Connection conexao = abrir();
				PreparedStatement stmt = conexao.prepareStatement(this.sql)) {
			preencher(stmt);
			try (ResultSet rs = stmt.executeQuery()) {
				CachedRowSet copia = RowSetProvider.newFactory().createCachedRowSet();
				copia.populate(rs);
				return copia;
			}
		}
	}

	public void update() throws SQLException, ClassNotFoundException {
		try (Connection conexao = abrir();
				PreparedStatement stmt = conexao.prepareStatement(this.sql)) {
			preencher(stmt);
			stmt.executeUpdate();
		}
	}

	/** Executa um INSERT e devolve o id gerado (AUTO_INCREMENT). */
	public int inserir() throws SQLException, ClassNotFoundException {
		try (Connection conexao = abrir();
				PreparedStatement stmt = conexao.prepareStatement(this.sql, Statement.RETURN_GENERATED_KEYS)) {
			preencher(stmt);
			stmt.executeUpdate();
			try (ResultSet chaves = stmt.getGeneratedKeys()) {
				return chaves.next() ? chaves.getInt(1) : 0;
			}
		}
	}
}
