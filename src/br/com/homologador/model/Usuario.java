/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

public class Usuario {

	private int login;
	private String senha;
	private String usuario;
	
	public final int getLogin() {
		return login;
	}
	public final void setLogin(int login) {
		this.login = login;
	}
	public final String getSenha() {
		return senha;
	}
	public final void setSenha(String senha) {
		this.senha = senha;
	}
	public final String getUsuario() {
		return usuario;
	}
	public final void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public boolean validaUsuario(Usuario usuario) {
		if(usuario.login == this.login && usuario.senha == this.senha) {
			return true;
		} else {
			return false;
		}
	}
}
