/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services;

import br.com.homologador.model.RegraNegocio;

public interface RegraServices {

	void getAll(RegraNegocio regraNegocio) throws Exception;
	boolean remove(Integer codigo) throws Exception;
	RegraNegocio getRegraById(Integer codigo) throws Exception;
	boolean adicionar(RegraNegocio regraNegocio) throws Exception;
}
