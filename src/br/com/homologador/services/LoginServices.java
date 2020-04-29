/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.services;

import br.com.homologador.model.Usuario;

public interface LoginServices {

	boolean validaUsuario(Usuario usuario) throws Exception;
}
