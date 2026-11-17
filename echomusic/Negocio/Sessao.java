package Negocio;

public final class Sessao {

	private static Usuario usuarioLogado;

	private Sessao() {
	}

	public static void iniciar(Usuario usuario) {
		usuarioLogado = usuario;
	}

	public static Usuario getUsuario() {
		return usuarioLogado;
	}

	public static int getIdUsuario() {
		return usuarioLogado == null ? 0 : usuarioLogado.getId();
	}

	public static void encerrar() {
		usuarioLogado = null;
	}
}
