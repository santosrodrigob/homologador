/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.BugsDao;
import br.com.homologador.dao.impl.BugsDaoImpl;
import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.BugCasoTeste;
import br.com.homologador.model.vo.BugComportamento;
import br.com.homologador.model.vo.BugRegra;
import br.com.homologador.services.BugsServices;

public class BugsServicesImpl implements BugsServices {

	private BugsDao bugsDao;
	
	public BugsServicesImpl(Connection connection) {
		super();
		this.bugsDao = new BugsDaoImpl(connection);
	}

	@Override
	public void getBugsCasoTeste(Integer codigoTeste, Integer codigoCasoTeste, TesteAtributos testeAtributos) throws Exception {
		this.bugsDao.getBugsCasoTeste(codigoTeste, codigoCasoTeste, testeAtributos);
	}

	@Override
	public void getBugsRegra(Integer codigoTeste, Integer codigoRegra, TesteAtributos testeAtributos) throws Exception {
		this.bugsDao.getBugsRegra(codigoTeste, codigoRegra, testeAtributos);
	}

	@Override
	public void getBugsComportamento(Integer codigoTeste, Integer codigoComportamento, TesteAtributos testeAtributos) throws Exception {
		this.bugsDao.getBugsComportamento(codigoTeste, codigoComportamento, testeAtributos);
	}

	@Override
	public boolean adicionaBugCasoTeste(Integer codigoTeste, Integer codigoCasoTeste, BugCasoTeste bugCasoTeste) throws Exception {
		return this.bugsDao.adicionaBugCasoTeste(codigoTeste, codigoCasoTeste, bugCasoTeste);
	}

	@Override
	public boolean adicionaBugRegra(Integer codigoTeste, Integer codigoRegra, BugRegra bugRegra) throws Exception {
		return this.bugsDao.adicionaBugRegra(codigoTeste, codigoRegra, bugRegra);
	}

	@Override
	public boolean adicionaBugComportamento(Integer codigoTeste, Integer codigoComportamento, BugComportamento bugComportamento) throws Exception {
		return this.bugsDao.adicionaBugComportamento(codigoTeste, codigoComportamento, bugComportamento);
	}

	@Override
	public boolean alteraCasoRegraComportamento(Integer codigoTeste, Integer tipo)
			throws Exception {
		return this.bugsDao.alteraCasoRegraComportamento(codigoTeste, tipo);
	}

	@Override
	public boolean alteraBugsCasoTeste(Integer codigoTeste, Integer tipo, Integer codigoCasoTeste) throws Exception {
		return this.bugsDao.alteraBugsCasoTeste(codigoTeste, tipo, codigoCasoTeste);
	}

	@Override
	public boolean alteraBugsRegra(Integer codigoTeste, Integer tipo, Integer codigoRegra) throws Exception {
		return this.bugsDao.alteraBugsRegra(codigoTeste, tipo, codigoRegra);
	}

	@Override
	public boolean alteraBugsComportamento(Integer codigoTeste, Integer tipo, Integer codigoComportamento)
			throws Exception {
		return this.bugsDao.alteraBugsComportamento(codigoTeste, tipo, codigoComportamento);
	}
}
