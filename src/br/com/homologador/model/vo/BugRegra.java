/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model.vo;

public class BugRegra {

	private int codigoBug;
	private String descricaoBug;
	private int tipo;
	private int situacao;

	public final int getCodigoBug() {
		return codigoBug;
	}
	public final void setCodigoBug(int codigoBug) {
		this.codigoBug = codigoBug;
	}
	public final String getDescricaoBug() {
		return descricaoBug;
	}
	public final void setDescricaoBug(String descricaoBug) {
		this.descricaoBug = descricaoBug;
	}
	public final int getTipo() {
		return tipo;
	}
	public final void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public final int getSituacao() {
		return situacao;
	}
	public final void setSituacao(int situacao) {
		this.situacao = situacao;
	}
}
