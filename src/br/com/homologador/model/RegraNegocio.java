/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

public class RegraNegocio extends Tipo {
	
	private int codigoRegra;
	private String descricaoRegraNegocio;
	private int testeFeature;
	
	List<RegraNegocio> regras = new ArrayList<RegraNegocio>();
	
	public final int getCodigoRegra() {
		return this.codigoRegra;
	}

	public final void setCodigoRegra(int codigoRegra) {
		this.codigoRegra = codigoRegra;
	}

	public final String getDescricaoRegraNegocio() {
		return this.descricaoRegraNegocio;
	}

	public final void setDescricaoRegraNegocio(String descricaoRegraNegocio) {
		this.descricaoRegraNegocio = descricaoRegraNegocio;
	}
	
	public void add(RegraNegocio regraNegocio) {
		this.regras.add(regraNegocio);
	}
	
	public final List<RegraNegocio> getRegras() {
		return this.regras;
	}

	public final int getTesteFeature() {
		return this.testeFeature;
	}

	public final void setTesteFeature(int testeFeature) {
		this.testeFeature = testeFeature;
	}
}
