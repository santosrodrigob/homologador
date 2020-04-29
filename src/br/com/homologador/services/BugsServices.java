/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services;

import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.BugCasoTeste;
import br.com.homologador.model.vo.BugComportamento;
import br.com.homologador.model.vo.BugRegra;

public interface BugsServices {

	void getBugsRegra(Integer codigoTeste, Integer codigoRegra, TesteAtributos testeAtributos) throws Exception;
	void getBugsComportamento(Integer codigoTeste, Integer codigoComportamento, TesteAtributos testeAtributos) throws Exception;
	void getBugsCasoTeste(Integer codigoTeste, Integer codigoCasoTeste, TesteAtributos testeAtributos) throws Exception;
	boolean adicionaBugCasoTeste(Integer codigoTeste, Integer codigoCasoTeste, BugCasoTeste bugCasoTeste) throws Exception;
	boolean adicionaBugRegra(Integer codigoTeste, Integer codigoRegra, BugRegra bugRegra) throws Exception;
	boolean adicionaBugComportamento(Integer codigoTeste, Integer codigoComportamento, BugComportamento bugComportamento) throws Exception;
	boolean alteraCasoRegraComportamento(Integer codigoTeste, Integer tipo) throws Exception;
	boolean alteraBugsCasoTeste(Integer codigoTeste, Integer tipo, Integer codigoCasoTeste) throws Exception;
	boolean alteraBugsRegra(Integer codigoTeste, Integer tipo, Integer codigoRegra) throws Exception;
	boolean alteraBugsComportamento(Integer codigoTeste, Integer tipo, Integer codigoComportamento) throws Exception;
}
