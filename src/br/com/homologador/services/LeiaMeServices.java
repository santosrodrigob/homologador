/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services;

import br.com.homologador.model.LeiaMe;
import br.com.homologador.model.vo.Filtro;

public interface LeiaMeServices {

	boolean adiciona(LeiaMe leiaMe) throws Exception;
	void getAll(LeiaMe leiaMe) throws Exception;
	Filtro getUltimoFiltro() throws Exception;
	LeiaMe getLeiaMeById(Integer codigo) throws Exception;
	boolean remove(Integer codigo) throws Exception;
}
