package Negocio;

/**
 * Guarda o id do usuário logado durante a execução do programa.
 *
 * O login atual (TelaLogin) ainda não confere usuário/senha no banco de
 * dados, então não existe uma autenticação real capaz de descobrir o id
 * de um usuário específico. Por isso o valor abaixo começa fixo em 1
 * (um único usuário "padrão") só para que as playlists criadas já saiam
 * vinculadas a um usuário. Quando o login passar a validar de verdade
 * contra o banco (usando Usuario.logar()), basta atualizar
 * idUsuarioLogado com o id retornado no lugar do valor fixo.
 */
public final class Sessao {

    public static int idUsuarioLogado = 1;

    private Sessao() {
        // classe utilitária: não deve ser instanciada
    }
}
