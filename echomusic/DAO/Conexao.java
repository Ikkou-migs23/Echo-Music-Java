
package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class Conexao {

    private String sql;
    private Object[] parametros = new Object[0];

    public void setSQL(String sql) {
        this.sql = sql;
    }

    public void setParametros(Object... parametros) {
        this.parametros = parametros;
    }


    private Connection abrir()
            throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        try {
            String url = "jdbc:postgresql://" + ConfigSupabase.obrigatorio("db.host")
                    + ":" + ConfigSupabase.opcional("db.port", "5432")
                    + "/" + ConfigSupabase.opcional("db.name", "postgres")
                    + "?sslmode=require";

            return DriverManager.getConnection(url,
                    ConfigSupabase.obrigatorio("db.user"),
                    ConfigSupabase.obrigatorio("db.password"));
        } catch (IOException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }


    private void preencher(PreparedStatement stmt) throws SQLException {

        for (int i = 0; i < parametros.length; i++) {
            stmt.setObject(i + 1, parametros[i]);
        }
    }


    public ResultSet query() throws SQLException, ClassNotFoundException {

        try (Connection conexao = abrir();
             PreparedStatement stmt = conexao.prepareStatement(this.sql)) {

            preencher(stmt);

            try (ResultSet rs = stmt.executeQuery()) {

                CachedRowSet copia =
                    RowSetProvider.newFactory().createCachedRowSet();

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

    public int inserir() throws SQLException, ClassNotFoundException {

        try (Connection conexao = abrir();
             PreparedStatement stmt = conexao.prepareStatement(
                 this.sql,
                 Statement.RETURN_GENERATED_KEYS
             )) {

            preencher(stmt);

            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {

                return chaves.next() ? chaves.getInt(1) : 0;
            }
        }
    }
}
