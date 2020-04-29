/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.RegraDao;
import br.com.homologador.dao.impl.RegraDaoImpl;
import br.com.homologador.model.RegraNegocio;
import br.com.homologador.services.RegraServices;

public class RegraServicesImpl implements RegraServices {

	private RegraDao regraDao;
	
	public RegraServicesImpl(Connection connection) {
		super();
		this.regraDao = new RegraDaoImpl(connection);
	}

	@Override
	public void getAll(RegraNegocio regraNegocio) throws Exception {
		this.regraDao.getAll(regraNegocio);
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.regraDao.remove(codigo);		
	}

	@Override
	public RegraNegocio getRegraById(Integer codigo) throws Exception {
		return this.regraDao.getRegraById(codigo);
	}

	@Override
	public boolean adicionar(RegraNegocio regraNegocio) throws Exception {
		this.regraDao.setUltimoFiltro(regraNegocio);
		return this.regraDao.adicionar(regraNegocio);
	}
}
