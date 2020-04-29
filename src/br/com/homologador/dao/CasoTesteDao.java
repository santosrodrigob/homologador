/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao;

import br.com.homologador.model.CasoTeste;

public interface CasoTesteDao {

	void getAll(CasoTeste casoTeste) throws Exception;
	boolean remove(Integer codigo) throws Exception;
	CasoTeste getCasoById(Integer codigo) throws Exception;
	boolean adicionar(CasoTeste casoTeste) throws Exception;
	void setUltimoFiltro(CasoTeste casoTeste) throws Exception;
}
