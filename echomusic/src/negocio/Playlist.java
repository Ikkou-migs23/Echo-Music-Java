package negocio;

import java.awt.Color;
import java.util.List;

/**
 * Representa as informações de uma playlist exibidas na biblioteca
 * (nome, cor/textura da capa e lista de músicas).
 *
 * Antes chamada de {@code InfoPlaylist}; renomeada para {@code Playlist}
 * para refletir melhor o que a classe representa.
 */
public class Playlist {

    private final String nome;
    private final Color cor;
    private final String textoCapa;
    private final List<Musica> musicas;

    public Playlist(String nome, Color cor, String textoCapa, List<Musica> musicas) {
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
