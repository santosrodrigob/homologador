/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.TesteDao;
import br.com.homologador.dao.impl.TesteDaoImpl;
import br.com.homologador.model.Teste;
import br.com.homologador.services.TesteServices;

public class TesteServicesImpl implements TesteServices {

	private TesteDao testeDao;
	
	public TesteServicesImpl(Connection connection) {
		super();
		this.testeDao = new TesteDaoImpl(connection);
	}

	@Override
	public Integer adicionaTeste(Teste teste) throws Exception {
		return this.testeDao.adicionaTeste(teste);
	}

	@Override
	public boolean adiciona(Teste teste, Integer codigoTeste) throws Exception {
		return this.testeDao.adiciona(teste, codigoTeste);
	}

	@Override
	public void getAll(Teste teste) throws Exception {
		this.testeDao.getAll(teste);
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.testeDao.remove(codigo);
	}

	@Override
	public Teste getTesteById(Integer codigo) throws Exception {
		return this.testeDao.getTesteById(codigo);
	}
}
