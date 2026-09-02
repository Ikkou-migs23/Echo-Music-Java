package model;

import java.awt.Color;
import java.util.List;

/**
 * Agrupa os dados de uma playlist (nome, cor/texto da capa e músicas) para
 * passar entre PainelBiblioteca, CardPlaylist e TelaPlaylist sem precisar
 * de vários parâmetros soltos.
 */
public class InfoPlaylist {

    private final String nome;
    private final Color cor;
    private final String textoCapa;
    private final List<Musica> musicas;

    public InfoPlaylist(String nome, Color cor, String textoCapa, List<Musica> musicas) {
        this.nome = nome;
        this.cor = cor;
        this.textoCapa = textoCapa;
        this.musicas = musicas;
    }

    public String getNome() {
        return nome;
    }

    public Color getCor() {
        return cor;
    }

    public String getTextoCapa() {
        return textoCapa;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }
}