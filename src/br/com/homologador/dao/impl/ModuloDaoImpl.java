/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.homologador.dao.ModuloDao;
import br.com.homologador.model.Modulo;
import br.com.homologador.model.vo.Filtro;

public class ModuloDaoImpl implements ModuloDao {

	private Connection connection;
	
	public ModuloDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}
	
	@Override
	public boolean adiciona(Modulo modulo) throws Exception {

		StringBuilder query = new StringBuilder();
		boolean isUpdated = false;

		query.append("update tb_modulo set modulo = ? where codigo_modulo = ? ");

		PreparedStatement statement = null;

		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, modulo.getDescricaoModulo());
			statement.setInt(2, modulo.getCodigoModulo());
			statement.executeUpdate();
			
			isUpdated = statement.getUpdateCount() > 0;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		query = new StringBuilder();
		query.append("insert into tb_modulo(modulo) select ? ");

		try
		{
			if(!isUpdated) 
			{ 	
				statement = this.connection.prepareStatement(query.toString());
				statement.setString(1, modulo.getDescricaoModulo());
				statement.executeUpdate();
				isUpdated = statement.getUpdateCount() > 0;				
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally 
		{
			if(this.connection!=null) {
				this.connection.close();
			}
			if(statement!=null) {
				statement.close();
			}
		}
		return isUpdated;
	}

	@Override
	public List<Modulo> getAll() throws Exception {
		
		List<Modulo> modulos = new ArrayList<Modulo>();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_modulo, ");
		query.append("	modulo, ");
		query.append("	ifnull(inativo,0) as inativo ");
		query.append("from ");
		query.append("	tb_modulo ");
		
		PreparedStatement statement = null;
				
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			
			while(rs.next()) {

				Modulo modulo = new Modulo();
				modulo.setCodigoModulo(rs.getInt("codigo_modulo"));
				modulo.setDescricaoModulo(rs.getString("modulo"));
				modulo.setInativo(rs.getInt("inativo"));
				modulos.add(modulo);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally 
		{
			if(this.connection!=null) {
				this.connection.close();
			}
			if(statement!=null) {
				statement.close();
			}
		}
		return modulos;
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		
		boolean isRemoved = false;
		boolean inativo = false;
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_modulo ");
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		
		PreparedStatement statement = null;

		try 
		{
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, codigo);
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				inativo = rs.getBoolean("inativo");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		query = new StringBuilder();
		
		query.append("update ");
		query.append("	tb_modulo ");
		query.append("set ");
		if(!inativo) {
			query.append("	inativo = 1 ");
		} else {
			query.append("	inativo = 0 ");			
		}
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());

			statement.setInt(1, codigo);
			statement.execute();
			isRemoved = statement.getUpdateCount() > 0;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally 
		{
			if(this.connection!=null) {
				this.connection.close();
			}
			if(statement!=null) {
				statement.close();
			}
		}
		return isRemoved;
	}

	@Override
	public Modulo getModuloById(Integer codigo) throws Exception {
		
		Modulo modulo = new Modulo();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("codigo_modulo, modulo ");
		query.append("from tb_modulo ");
		query.append("where codigo_modulo = ? ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, codigo);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			
			while(rs.next()) {
				modulo.setCodigoModulo(rs.getInt("codigo_modulo"));
				modulo.setDescricaoModulo(rs.getString("modulo"));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally 
		{
			if(this.connection!=null) {
				this.connection.close();
			}
			if(statement!=null) {
				statement.close();
			}
		}
		return modulo;
	}
	
	@Override
	public List<Modulo> getComboModulos() throws Exception {
		
		List<Modulo> listaModulo = new ArrayList<Modulo>();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_modulo, ");
		query.append("	concat(modulo,' (', codigo_modulo,')') as modulo ");
		query.append("from ");
		query.append("	tb_modulo ");
		query.append("where ");
		query.append("	ifnull(inativo, 0) = 0 ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				Modulo modulo = new Modulo();
				modulo.setCodigoModulo(rs.getInt("codigo_modulo"));
				modulo.setDescricaoModulo(rs.getString("modulo"));
				listaModulo.add(modulo);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally 
		{
			if(this.connection!=null) {
				this.connection.close();
			}
			if(statement!=null) {
				statement.close();
			}
		}
		return listaModulo;
	}

	@Override
	public Filtro getUltimoFiltro() throws Exception {
		
		Filtro filtro = new Filtro();
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	l.codigo_modulo, ");
		query.append("	l.codigo_feature, ");
		query.append("	l.codigo_tipo ");
		query.append("from ");
		query.append("	tb_filtro l ");
		query.append("	inner join tb_modulo m on l.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on l.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo t on l.codigo_tipo=t.codigo_tipo ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				filtro.setCodigoModulo(rs.getInt("codigo_modulo"));
				filtro.setCodigoFeature(rs.getInt("codigo_feature"));
				filtro.setCodigoTipo(rs.getInt("codigo_tipo"));
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally 
		{
			if(statement!=null) {
				statement.close();
			}
		}
		return filtro;
	}
}
