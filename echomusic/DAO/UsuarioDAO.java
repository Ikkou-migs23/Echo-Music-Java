
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

    public String cadastrar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "INSERT INTO usuarios (usuario, senha) VALUES (?, ?)"
        );

        objeto.setParametros(this.usuario, this.senha);
        objeto.update();

        return "Cadastrado com Sucesso";
    }

    public ResultSet logar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_usuario AS \"idUsuario\", usuario, senha, " +
            "fotonuvem AS \"fotoNuvem\" " +
            "FROM usuarios WHERE usuario = ? AND senha = ?"
        );

        objeto.setParametros(this.usuario, this.senha);

        return objeto.query();
    }

    public ResultSet consultar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_usuario AS \"idUsuario\", usuario, senha, " +
            "fotonuvem AS \"fotoNuvem\" " +
            "FROM usuarios WHERE id_usuario = ?"
        );

        objeto.setParametros(this.idUsuario);

        return objeto.query();
    }

    public ResultSet listar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_usuario AS \"idUsuario\", usuario, senha, " +
            "fotonuvem AS \"fotoNuvem\" FROM usuarios"
        );

        return objeto.query();
    }

    public String alterar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "UPDATE usuarios SET usuario = ?, senha = ? WHERE id_usuario = ?"
        );

        objeto.setParametros(this.usuario, this.senha, this.idUsuario);
        objeto.update();

        return "Usuario Alterado com Sucesso";
    }

    public String alterarFoto(String fotoNuvem) throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "UPDATE usuarios SET fotonuvem = ? WHERE id_usuario = ?"
        );

        objeto.setParametros(fotoNuvem, this.idUsuario);
        objeto.update();

        return "Foto Alterada com Sucesso";
    }

    public String excluir() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "DELETE FROM usuarios WHERE id_usuario = ?"
        );

        objeto.setParametros(this.idUsuario);
        objeto.update();

        return "Usuario Removido com Sucesso";
    }
}
