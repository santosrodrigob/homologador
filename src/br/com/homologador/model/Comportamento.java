/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

public class Comportamento extends Tipo {
	
	private int codigoComportamento;
	private String descricaoComportamento;
	private int testeFeature;

	List<Comportamento> listaComportamentos = new ArrayList<Comportamento>();
	
	public final int getCodigoComportamento() {
		return codigoComportamento;
	}
	public final void setCodigoComportamento(int codigoComportamento) {
		this.codigoComportamento = codigoComportamento;
	}
	public final String getDescricaoComportamento() {
		return descricaoComportamento;
	}
	public final void setDescricaoComportamento(String descricaoComportamento) {
		this.descricaoComportamento = descricaoComportamento;
	}

	public final int getTesteFeature() {
		return this.testeFeature;
	}

	public final void setTesteFeature(int testeFeature) {
		this.testeFeature = testeFeature;
	}
	
	public void add(Comportamento comportamento) {
		this.listaComportamentos.add(comportamento);
	}
	
	public final List<Comportamento> getListaComportamentos() {
		return listaComportamentos;
	}
}
