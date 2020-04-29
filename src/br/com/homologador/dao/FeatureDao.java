/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao;

import java.util.List;

import br.com.homologador.model.Feature;

public interface FeatureDao {

	boolean adiciona(Feature feature) throws Exception;
	List<Feature> getAll() throws Exception;
	Feature getFeatureById(Integer codigo) throws Exception;
	boolean remove(Integer codigo) throws Exception;
	List<Feature> getComboFeatures() throws Exception;
	List<Feature> getComboFeaturesByModulo(Integer codigoModulo) throws Exception;
}
