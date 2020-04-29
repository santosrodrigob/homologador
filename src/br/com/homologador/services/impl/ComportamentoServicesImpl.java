/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.ComportamentoDao;
import br.com.homologador.dao.impl.ComportamentoDaoImpl;
import br.com.homologador.model.Comportamento;
import br.com.homologador.services.ComportamentoServices;

public class ComportamentoServicesImpl implements ComportamentoServices {

	private ComportamentoDao comportamentoDao;
	
	public ComportamentoServicesImpl(Connection connection) {
		super();
		this.comportamentoDao = new ComportamentoDaoImpl(connection);
	}

	@Override
	public void getAll(Comportamento comportamento) throws Exception {
		this.comportamentoDao.getAll(comportamento);
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.comportamentoDao.remove(codigo);		
	}

	@Override
	public Comportamento getComportamentoById(Integer codigo) throws Exception {
		return this.comportamentoDao.getComportamentoById(codigo);
	}

	@Override
	public boolean adicionar(Comportamento comportamento) throws Exception {
		this.comportamentoDao.setUltimoFiltro(comportamento);
		return this.comportamentoDao.adicionar(comportamento);
	}
}
