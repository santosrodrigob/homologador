/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.homologador.dao.LoginDao;
import br.com.homologador.model.Usuario;

public class LoginDaoImpl implements LoginDao {
	
	private Connection connection;
	
	public LoginDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public boolean validaUsuario(Usuario usuario) throws Exception {
		
		boolean isValid = false;
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	u.codigo_usuario, ");
		query.append("	u.senha, ");
		query.append("	u.usuario ");
		query.append("from ");
		query.append("	tb_usuario u ");
		query.append("	inner join tb_role r on u.codigo_acesso_homologador=r.codigo_acesso ");
		query.append("where ");
		query.append("	codigo_usuario = ? ");
		query.append("	and senha = ? ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				
				usuario.setLogin(rs.getInt("codigo_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setUsuario(rs.getString("usuario"));
				isValid = usuario.validaUsuario(usuario);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally 
		{
			if(statement!=null) {
				statement.close();
			}
			if(connection!=null) {
				connection.close();
			}
		}
		return isValid;
	}
}
