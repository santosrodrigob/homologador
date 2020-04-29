/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

public class Modulo extends DadosCriacao {
	
	private int codigoModulo;
	private String descricaoModulo;
	private int inativo;	
	
	public final int getCodigoModulo() {
		return this.codigoModulo;
	}
	public final void setCodigoModulo(int codigoModulo) {
		this.codigoModulo = codigoModulo;
	}
	public final String getDescricaoModulo() {
		return this.descricaoModulo;
	}
	public final void setDescricaoModulo(String descricaoModulo) {
		this.descricaoModulo = descricaoModulo;
	}
	public final int getInativo() {
		return inativo;
	}
	public final void setInativo(int inativo) {
		this.inativo = inativo;
	}
}