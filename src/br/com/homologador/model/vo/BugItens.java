/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model.vo;

public class BugItens {
	
	private int codigoTeste;
	private int codigoBug;
	private String descricaoBug;
	private String descricao;
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
	public int getCodigoTeste() {
		return codigoTeste;
	}
	public void setCodigoTeste(int codigoTeste) {
		this.codigoTeste = codigoTeste;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
