/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services.impl;

import java.sql.Connection;

import br.com.homologador.dao.LoginDao;
import br.com.homologador.dao.impl.LoginDaoImpl;
import br.com.homologador.model.Usuario;
import br.com.homologador.services.LoginServices;

public class LoginServicesImpl implements LoginServices {

	private LoginDao loginDao;
	
	public LoginServicesImpl(Connection connection) {
		super();
		this.loginDao = new LoginDaoImpl(connection);
	}

	@Override
	public boolean validaUsuario(Usuario usuario) throws Exception {
		return this.loginDao.validaUsuario(usuario);
	}
	
}
