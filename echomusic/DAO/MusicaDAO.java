
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicaDAO {

    private int idMusica;
    private String nome;
    private String duracao;
    private String caminho;
    private String caminhoNuvem;
    private int idPlaylist;

    public void setIdMusica(int idMusica) {
        this.idMusica = idMusica;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public void setCaminhoNuvem(String caminhoNuvem) {
        this.caminhoNuvem = caminhoNuvem;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String cadastrar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "INSERT INTO musicas (nome, duracao, caminho, caminhonuvem, id_playlist) " +
            "VALUES (?, ?, ?, ?, ?)"
        );

        objeto.setParametros(
            this.nome,
            this.duracao,
            this.caminho,
            this.caminhoNuvem,
            this.idPlaylist
        );

        objeto.update();

        return "Musica Cadastrada com Sucesso";
    }

    public ResultSet consultar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_musica AS \"idMusica\", nome, duracao, " +
            "caminho, caminhonuvem AS \"caminhoNuvem\", " +
            "id_playlist AS \"idPlaylist\" " +
            "FROM musicas WHERE id_musica = ?"
        );

        objeto.setParametros(this.idMusica);

        return objeto.query();
    }

    public ResultSet listar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_musica AS \"idMusica\", nome, duracao, " +
            "caminho, caminhonuvem AS \"caminhoNuvem\", " +
            "id_playlist AS \"idPlaylist\" FROM musicas"
        );

        return objeto.query();
    }

    public ResultSet listarPorPlaylist() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "SELECT id_musica AS \"idMusica\", nome, duracao, " +
            "caminho, caminhonuvem AS \"caminhoNuvem\", " +
            "id_playlist AS \"idPlaylist\" " +
            "FROM musicas WHERE id_playlist = ? " +
            "ORDER BY id_musica"
        );

        objeto.setParametros(this.idPlaylist);

        return objeto.query();
    }

    public String alterar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "UPDATE musicas SET nome = ?, duracao = ?, " +
            "caminho = ?, caminhonuvem = ?, " +
            "id_playlist = ? WHERE id_musica = ?"
        );

        objeto.setParametros(
            this.nome,
            this.duracao,
            this.caminho,
            this.caminhoNuvem,
            this.idPlaylist,
            this.idMusica
        );

        objeto.update();

        return "Musica Alterada com Sucesso";
    }

    public String deletar() throws SQLException, ClassNotFoundException {

        Conexao objeto = new Conexao();

        objeto.setSQL(
            "DELETE FROM musicas WHERE id_musica = ?"
        );

        objeto.setParametros(this.idMusica);
        objeto.update();

        return "Musica Removida com Sucesso";
    }
}
