/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.model;

import java.util.ArrayList;
import java.util.List;

public class Feature extends Modulo {
	
	private int codigoFeature;
	private String descricaoFeature;

	List<Feature> features = new ArrayList<Feature>();
	
	public final int getCodigoFeature() {
		return this.codigoFeature;
	}
	public final void setCodigoFeature(int codigoFeature) {
		this.codigoFeature = codigoFeature;
	}
	public final String getDescricaoFeature() {
		return this.descricaoFeature;
	}
	public final void setDescricaoFeature(String descricaoFeature) {
		this.descricaoFeature = descricaoFeature;
	}
	
	public final List<Feature> getFeatures() {
		return this.features;
	}

	public final void add(Feature feature) {
		this.features.add(feature);
	}

}