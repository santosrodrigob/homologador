/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao;

import java.util.List;

import br.com.homologador.model.Tipo;

public interface TipoDao {

	List<Tipo> getComboTipos() throws Exception;
	List<Tipo> getAll() throws Exception;
	boolean adiciona(Tipo tipo) throws Exception;
	Tipo getTipoById(Integer codigo) throws Exception;
	boolean remove(Integer codigo) throws Exception;
	List<Tipo> getComboTiposByFeature(Integer codigoModulo, Integer codigoFeature) throws Exception;
}
