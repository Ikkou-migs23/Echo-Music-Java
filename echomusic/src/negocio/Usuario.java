package negocio;

public class Usuario{
	
	private String nome;
	private String senha;
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getUsuario() {
		return nome;
	}
	public void setUsuario(String nome) {
		this.nome = nome;
	}
	
}