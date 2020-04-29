/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.homologador.model.DadosCriacao;

public class RelatorioModulo extends DadosCriacao {
	
	private int codigoModulo;
	private String descricao;
	
	List<RelatorioModuloItens> itens = new ArrayList<RelatorioModuloItens>();
	
	public final int getCodigoModulo() {
		return codigoModulo;
	}
	public final void setCodigoModulo(int codigoModulo) {
		this.codigoModulo = codigoModulo;
	}
	public final String getDescricao() {
		return descricao;
	}
	public final void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	public void addItem(RelatorioModuloItens item) {
		this.itens.add(item);
	}
	public List<RelatorioModuloItens> getItens() {
		return this.itens;
	}
}
