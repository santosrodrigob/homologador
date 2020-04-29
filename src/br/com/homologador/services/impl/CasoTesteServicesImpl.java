/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.CasoTesteDao;
import br.com.homologador.dao.impl.CasoTesteDaoImpl;
import br.com.homologador.model.CasoTeste;
import br.com.homologador.services.CasoTesteServices;

public class CasoTesteServicesImpl implements CasoTesteServices {

	private CasoTesteDao casoTesteDao;
	
	public CasoTesteServicesImpl(Connection connection) {
		super();
		this.casoTesteDao = new CasoTesteDaoImpl(connection);
	}

	@Override
	public void getAll(CasoTeste casoTeste) throws Exception {
		this.casoTesteDao.getAll(casoTeste);
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.casoTesteDao.remove(codigo);		
	}

	@Override
	public CasoTeste getCasoById(Integer codigo) throws Exception {
		return this.casoTesteDao.getCasoById(codigo);
	}

	@Override
	public boolean adicionar(CasoTeste casoTeste) throws Exception {
		this.casoTesteDao.setUltimoFiltro(casoTeste);
		return this.casoTesteDao.adicionar(casoTeste);
	}
}
