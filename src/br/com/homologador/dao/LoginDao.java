/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao;

import br.com.homologador.model.Usuario;

public interface LoginDao {

	boolean validaUsuario(Usuario usuario) throws Exception;
}
