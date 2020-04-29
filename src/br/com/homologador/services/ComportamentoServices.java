/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services;

import br.com.homologador.model.Comportamento;

public interface ComportamentoServices {

	void getAll(Comportamento comportamento) throws Exception;
	boolean remove(Integer codigo) throws Exception;
	Comportamento getComportamentoById(Integer codigo) throws Exception;
	boolean adicionar(Comportamento comportamento) throws Exception;
}
