
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {

    private int idPlaylist;
    private int idUsuario;
    private String nome;
    private int cor;
    private String textoCapa;
    private String capaNuvem;

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public void setTextoCapa(String textoCapa) {
        this.textoCapa = textoCapa;
    }


    public void setCapaNuvem(String capaNuvem) {
        this.capaNuvem = capaNuvem;
    }


    public int cadastrar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "INSERT INTO playlists (id_usuario, nome, cor, texto_capa, capanuvem) " +
            "VALUES (?, ?, ?, ?, ?)"
        );

        objeto.setParametros(
            this.idUsuario,
            this.nome,
            this.cor,
            this.textoCapa,
            this.capaNuvem
        );

        return objeto.inserir();
    }


    public ResultSet consultar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_playlist AS \"idPlaylist\", " +
            "id_usuario AS \"idUsuario\", nome, cor, " +
            "texto_capa AS \"textoCapa\", capanuvem AS \"capaNuvem\" " +
            "FROM playlists WHERE id_playlist = ?"
        );

        objeto.setParametros(this.idPlaylist);

        return objeto.query();
    }


    public ResultSet listar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_playlist AS \"idPlaylist\", " +
            "id_usuario AS \"idUsuario\", nome, cor, " +
            "texto_capa AS \"textoCapa\", capanuvem AS \"capaNuvem\" " +
            "FROM playlists"
        );

        return objeto.query();
    }


    public ResultSet listarPorUsuario() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_playlist AS \"idPlaylist\", " +
            "id_usuario AS \"idUsuario\", nome, cor, " +
            "texto_capa AS \"textoCapa\", capanuvem AS \"capaNuvem\" " +
            "FROM playlists WHERE id_usuario = ? " +
            "ORDER BY id_playlist"
        );

        objeto.setParametros(this.idUsuario);

        return objeto.query();
    }


    public String alterar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "UPDATE playlists SET id_usuario = ?, nome = ?, " +
            "cor = ?, texto_capa = ?, capanuvem = ? WHERE id_playlist = ?"
        );

        objeto.setParametros(
            this.idUsuario,
            this.nome,
            this.cor,
            this.textoCapa,
            this.capaNuvem,
            this.idPlaylist
        );

        objeto.update();

        return "Playlist Alterada com Sucesso";
    }

    public String deletar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "DELETE FROM playlists WHERE id_playlist = ?"
        );

        objeto.setParametros(this.idPlaylist);
        objeto.update();

        return "Playlist Removida com Sucesso";
    }
}
