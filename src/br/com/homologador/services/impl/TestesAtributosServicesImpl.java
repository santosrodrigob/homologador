/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.TestesAtributosDao;
import br.com.homologador.dao.impl.TestesAtributosDaoImpl;
import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.TesteCasoTeste;
import br.com.homologador.model.vo.TesteComportamento;
import br.com.homologador.model.vo.TesteRegra;
import br.com.homologador.services.TesteAtributosServices;

public class TestesAtributosServicesImpl implements TesteAtributosServices {

	private TestesAtributosDao testesAtributosDao;
	
	public TestesAtributosServicesImpl(Connection connection) {
		super();
		this.testesAtributosDao = new TestesAtributosDaoImpl(connection);
	}

	@Override
	public void getAll(TesteAtributos testesAtributos) throws Exception {
		this.testesAtributosDao.getAll(testesAtributos);
	}

	@Override
	public TesteCasoTeste getTesteCasoTesteById(Integer codigoTeste, Integer codigoCasoTeste) throws Exception {
		return this.testesAtributosDao.getTesteCasoTesteById(codigoTeste, codigoCasoTeste);
	}

	@Override
	public TesteRegra getTesteRegraById(Integer codigoTeste, Integer codigoRegra) throws Exception {
		return this.testesAtributosDao.getTesteRegraById(codigoTeste, codigoRegra);
	}

	@Override
	public TesteComportamento getTesteComportamentoById(Integer codigoTeste, Integer codigoComportamento)
			throws Exception {
		return this.testesAtributosDao.getTesteComportamentoById(codigoTeste, codigoComportamento);
	}

	@Override
	public boolean adicionaTesteCaso(Integer codigoTeste, TesteCasoTeste testeCasoTeste) throws Exception {
		return this.testesAtributosDao.adicionaTesteCaso(codigoTeste, testeCasoTeste);
	}

	@Override
	public boolean adicionaTesteRegra(Integer codigoTeste, TesteRegra testeRegra) throws Exception {
		return this.testesAtributosDao.adicionaTesteRegra(codigoTeste, testeRegra);
	}

	@Override
	public boolean adicionaComportamento(Integer codigoTeste, TesteComportamento testeComportamento) throws Exception {
		return this.testesAtributosDao.adicionaComportamento(codigoTeste, testeComportamento);
	}

	@Override
	public boolean remove(Integer codigoTeste, TesteCasoTeste testeCasoTeste) throws Exception {
		return this.testesAtributosDao.remove(codigoTeste, testeCasoTeste);
	}

	@Override
	public boolean remove(Integer codigoTeste, TesteRegra testeRegra) throws Exception {
		return this.testesAtributosDao.remove(codigoTeste, testeRegra);
	}

	@Override
	public boolean remove(Integer codigoTeste, TesteComportamento testeComportamento) throws Exception {
		return this.testesAtributosDao.remove(codigoTeste, testeComportamento);
	}
}
