/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.LeiaMeDao;
import br.com.homologador.dao.impl.LeiaMeDaoImpl;
import br.com.homologador.model.LeiaMe;
import br.com.homologador.model.vo.Filtro;
import br.com.homologador.services.LeiaMeServices;

public class LeiaMeServicesImpl implements LeiaMeServices {

	LeiaMeDao leiaMeDao;

	public LeiaMeServicesImpl(Connection connection) {
		super();
		this.leiaMeDao = new LeiaMeDaoImpl(connection);
	}
	
	
	@Override
	public boolean adiciona(LeiaMe leiaMe) throws Exception {
		this.leiaMeDao.setUltimoFiltro(leiaMe);
		return this.leiaMeDao.adiciona(leiaMe);
	}


	@Override
	public void getAll(LeiaMe leiaMe) throws Exception {
		this.leiaMeDao.getAll(leiaMe);
	}


	@Override
	public Filtro getUltimoFiltro() throws Exception {
		return this.leiaMeDao.getUltimoFiltro();
	}


	@Override
	public LeiaMe getLeiaMeById(Integer codigo) throws Exception {
		return this.leiaMeDao.getLeiaMeById(codigo);
	}


	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.leiaMeDao.remove(codigo);
	}

}
