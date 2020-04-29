/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;
import java.util.List;

import br.com.homologador.dao.TipoDao;
import br.com.homologador.dao.impl.TipoDaoImpl;
import br.com.homologador.model.Tipo;
import br.com.homologador.services.TipoServices;

public class TipoServicesImpl implements TipoServices {
	
	private TipoDao tipoDao;
	
	public TipoServicesImpl(Connection connection) {
		super();
		this.tipoDao = new TipoDaoImpl(connection);
	}

	@Override
	public List<Tipo> getComboTipos() throws Exception {
		return this.tipoDao.getComboTipos();
	}

	@Override
	public List<Tipo> getAll() throws Exception {
		return this.tipoDao.getAll();
	}

	@Override
	public boolean adiciona(Tipo tipo) throws Exception {
		return this.tipoDao.adiciona(tipo);
	}

	@Override
	public Tipo getTipoById(Integer codigo) throws Exception {
		return this.tipoDao.getTipoById(codigo);
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.tipoDao.remove(codigo);
	}

	@Override
	public List<Tipo> getComboTiposByFeature(Integer codigoModulo, Integer codigoFeature) throws Exception {
		return this.tipoDao.getComboTiposByFeature(codigoModulo, codigoFeature);
	}
}
