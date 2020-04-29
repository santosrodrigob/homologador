/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

public class CasoTeste extends Tipo {
	
	private int codigoCasoTeste;
	private String descricaoCasoTeste;
	private int testeFeature;

	private List<CasoTeste> listaCasos = new ArrayList<CasoTeste>();
	
	public final int getCodigoCasoTeste() {
		return codigoCasoTeste;
	}
	public final void setCodigoCasoTeste(int codigoCasoTeste) {
		this.codigoCasoTeste = codigoCasoTeste;
	}
	
	public final String getDescricaoCasoTeste() {
		return descricaoCasoTeste;
	}
	public final void setDescricaoCasoTeste(String descricaoCasoTeste) {
		this.descricaoCasoTeste = descricaoCasoTeste;
	}

	public final int getTesteFeature() {
		return this.testeFeature;
	}

	public final void setTesteFeature(int testeFeature) {
		this.testeFeature = testeFeature;
	}
	
	public void add(CasoTeste casoTeste) {
		this.listaCasos.add(casoTeste);
	}
	
	public final List<CasoTeste> getListaCasos() {
		return this.listaCasos;
	}
}
