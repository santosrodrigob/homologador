/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;
import java.util.List;

import br.com.homologador.dao.ModuloDao;
import br.com.homologador.dao.impl.ModuloDaoImpl;
import br.com.homologador.model.Modulo;
import br.com.homologador.model.vo.Filtro;
import br.com.homologador.services.ModuloServices;

public class ModuloServicesImpl implements ModuloServices {

	private ModuloDao moduloDao;
	
	public ModuloServicesImpl(Connection connection) {
		super();
		this.moduloDao = new ModuloDaoImpl(connection);
	}
	
	@Override
	public boolean adiciona(Modulo modulo) throws Exception {
		return this.moduloDao.adiciona(modulo);
	}

	@Override
	public List<Modulo> getAll() throws Exception {
		return this.moduloDao.getAll();
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		return this.moduloDao.remove(codigo);
	}

	@Override
	public Modulo getModuloById(Integer codigo) throws Exception {
		return this.moduloDao.getModuloById(codigo);
	}
	
	@Override
	public List<Modulo> getComboModulos() throws Exception {
		return this.moduloDao.getComboModulos();
	}

	@Override
	public Filtro getUltimoFiltro() throws Exception {
		return this.moduloDao.getUltimoFiltro();
	}
}
