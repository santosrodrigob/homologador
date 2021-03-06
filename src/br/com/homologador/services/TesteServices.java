/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services;

import br.com.homologador.model.Teste;

public interface TesteServices {

	Integer adicionaTeste(Teste teste) throws Exception;
	boolean adiciona(Teste teste, Integer codigoTeste) throws Exception;
	void getAll(Teste teste) throws Exception;
	boolean remove(Integer codigo) throws Exception;
	Teste getTesteById(Integer codigo) throws Exception;
}
