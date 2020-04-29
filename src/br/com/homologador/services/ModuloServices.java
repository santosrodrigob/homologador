/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services;

import java.util.List;

import br.com.homologador.model.Modulo;
import br.com.homologador.model.vo.Filtro;

public interface ModuloServices {

	boolean adiciona(Modulo modulo) throws Exception;
	List<Modulo> getAll() throws Exception;
	boolean remove(Integer codigo)throws Exception;
	Modulo getModuloById(Integer codigo) throws Exception;
	List<Modulo> getComboModulos() throws Exception;
	Filtro getUltimoFiltro() throws Exception;
}
