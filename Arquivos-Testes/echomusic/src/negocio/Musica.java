package negocio;

/**
 * Representa uma música (nome + duração) dentro de uma playlist.
 */
public class Musica {

    private final String nome;
    private final String duracao;

    public Musica(String nome, String duracao) {
        this.nome = nome;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public String getDuracao() {
        return duracao;
    }
}
