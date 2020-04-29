/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao;

import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.TesteCasoTeste;
import br.com.homologador.model.vo.TesteComportamento;
import br.com.homologador.model.vo.TesteRegra;

public interface TestesAtributosDao {

	void getAll(TesteAtributos testesAtributos) throws Exception;
	TesteCasoTeste getTesteCasoTesteById(Integer codigoTeste, Integer codigoCasoTeste) throws Exception;
	TesteRegra getTesteRegraById(Integer codigoTeste, Integer codigoRegra) throws Exception;
	TesteComportamento getTesteComportamentoById(Integer codigoTeste, Integer codigoComportamento) throws Exception;
	boolean adicionaTesteCaso(Integer codigoTeste, TesteCasoTeste testeCasoTeste) throws Exception;
	boolean adicionaTesteRegra(Integer codigoTeste, TesteRegra testeRegra) throws Exception;
	boolean adicionaComportamento(Integer codigoTeste, TesteComportamento testeComportamento) throws Exception;
	boolean remove(Integer codigoTeste, TesteCasoTeste testeCasoTeste) throws Exception;
	boolean remove(Integer codigoTeste, TesteRegra testeRegra) throws Exception;
	boolean remove(Integer codigoTeste, TesteComportamento testeComportamento) throws Exception;
}
