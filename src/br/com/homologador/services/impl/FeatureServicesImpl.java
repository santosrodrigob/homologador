/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;
import java.util.List;

import br.com.homologador.dao.FeatureDao;
import br.com.homologador.dao.impl.FeatureDaoImpl;
import br.com.homologador.model.Feature;
import br.com.homologador.services.FeatureServices;

public class FeatureServicesImpl implements FeatureServices {

	private FeatureDao featureDao;

	public FeatureServicesImpl(Connection connection) {
		super();
		this.featureDao = new FeatureDaoImpl(connection);
	}

	@Override
	public boolean adiciona(Feature feature) throws Exception {
		return this.featureDao.adiciona(feature);
	}

	@Override
	public List<Feature> getAll() throws Exception {
		return this.featureDao.getAll();
	}

	@Override
	public Feature getFeatureById(Integer codigo) throws Exception {
		return this.featureDao.getFeatureById(codigo);
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.featureDao.remove(codigo);
	}

	@Override
	public List<Feature> getComboFeatures() throws Exception {
		return this.featureDao.getComboFeatures();
	}

	@Override
	public List<Feature> getComboFeaturesByModulo(Integer codigoModulo) throws Exception {
		return this.featureDao.getComboFeaturesByModulo(codigoModulo);
	}
}
