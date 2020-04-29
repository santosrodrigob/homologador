/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

public class Tipo extends Feature {
	
	private int codigoTipo;
	private String descricaoTipo;
	private int qtdeBugs;
	
	List<Tipo> tipos = new ArrayList<Tipo>();
	
	public final int getCodigoTipo() {
		return codigoTipo;
	}
	public final void setCodigoTipo(int codigoTipo) {
		this.codigoTipo = codigoTipo;
	}
	public final String getDescricaoTipo() {
		return descricaoTipo;
	}
	public final void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}
	
	public final List<Tipo> getTipos() {
		return this.tipos;
	}
	
	public final void add(Tipo tipo) {
		this.tipos.add(tipo);
	}

	public final int getQtdeBugs() {
		return qtdeBugs;
	}
	
	public final void setQtdeBugs(int qtdeBugs) {
		this.qtdeBugs = qtdeBugs;
	}
}
