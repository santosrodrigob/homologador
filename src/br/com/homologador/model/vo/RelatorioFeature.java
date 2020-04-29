/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.homologador.model.Feature;

public class RelatorioFeature extends Feature {
	
	private String descricao;

	List<RelatorioFeatureItens> itens = new ArrayList<RelatorioFeatureItens>();
	
	public final String getDescricao() {
		return descricao;
	}
	public final void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void add(RelatorioFeatureItens item) {
		this.itens.add(item);
	}
	public final List<RelatorioFeatureItens> getItens() {
		return itens;
	}
}
