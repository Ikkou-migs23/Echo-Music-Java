package Negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda as playlists do usuário em memória durante a execução do programa.
 *
 * Começa vazio — sem playlists de exemplo pré-cadastradas — e ainda não há
 * persistência em banco de dados: tudo que for criado ou removido pelo
 * usuário vive apenas enquanto o programa estiver aberto. Centralizar a
 * lista aqui (em vez de recriá-la a cada tela) é o que permite que criar e
 * deletar playlists reflita em todas as telas durante a mesma execução.
 */
public final class RepositorioPlaylists {

    private static final List<Playlist> playlists = new ArrayList<>();

    private RepositorioPlaylists() {
        // classe utilitária: não deve ser instanciada
    }

    /** Lista viva das playlists atuais — alterações nela refletem para quem já a leu. */
    public static List<Playlist> listar() {
        return playlists;
    }

    public static void adicionar(Playlist playlist) {
        playlists.add(playlist);
    }

    public static void remover(Playlist playlist) {
        playlists.remove(playlist);
    }
}
