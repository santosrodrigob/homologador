/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnectionMySQL() {
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost/data_teste?useTimezone=true&serverTimezone=UTC",
					"root", "root");
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException(e);
		} 
		catch (ClassNotFoundException e) 
		{
			throw new RuntimeException(e);
		}
	}
	
/*
	public DataSource dataSource;

	public ConnectionFactory() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/data_teste?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("root");
		
		comboPooledDataSource.setMaxPoolSize(12);
		
		this.dataSource = comboPooledDataSource;
	}

	public Connection recuperarConexao() throws Exception {
		return this.dataSource.getConnection();
	}
*/

}
