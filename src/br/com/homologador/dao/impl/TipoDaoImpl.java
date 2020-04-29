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

import br.com.homologador.dao.TipoDao;
import br.com.homologador.model.Tipo;

public class TipoDaoImpl implements TipoDao {
	
	private Connection connection;
	
	public TipoDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public List<Tipo> getAll() throws Exception {

		List<Tipo> tipos = new ArrayList<Tipo>();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	t.codigo_tipo, ");
		query.append("	t.tipo, ");
		query.append("	t.codigo_modulo, ");
		query.append("	m.modulo, ");
		query.append("	t.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	t.inativo ");
		query.append("from ");
		query.append("	tb_tipo t ");
		query.append("	inner join tb_modulo m on t.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on t.codigo_feature=f.codigo_feature ");
		
		PreparedStatement statement = null;
		try
		{	
			statement = this.connection.prepareStatement(query.toString());
			statement.execute();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				Tipo tipo = new Tipo();
				tipo.setCodigoTipo(rs.getInt("codigo_tipo"));
				tipo.setDescricaoTipo(rs.getString("tipo"));
				tipo.setCodigoModulo(rs.getInt("codigo_modulo"));
				tipo.setDescricaoModulo(rs.getString("modulo"));
				tipo.setCodigoFeature(rs.getInt("codigo_feature"));
				tipo.setDescricaoFeature(rs.getString("feature"));
				tipo.setInativo(rs.getInt("inativo"));
				tipos.add(tipo);
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
			if(connection!=null) {
				connection.close();
			}
		}		
		return tipos;	
	}

	@Override
	public boolean adiciona(Tipo tipo) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_tipo ");
		query.append("set ");
		query.append("	tipo = ?, ");
		query.append("	codigo_modulo = ?, ");
		query.append("	codigo_feature = ? ");
		query.append("where ");
		query.append("	codigo_tipo = ? ");
		
		PreparedStatement statement = null;
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, tipo.getDescricaoTipo());
			statement.setInt(2, tipo.getCodigoModulo());
			statement.setInt(3, tipo.getCodigoFeature());
			statement.setInt(4, tipo.getCodigoTipo());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		query = new StringBuilder();
		query.append("insert into tb_tipo ");
		query.append("(");
		query.append("	codigo_tipo, ");
		query.append("	tipo, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature");
		query.append(") select ?, ?, ?, ? ");

		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, tipo.getCodigoTipo());
			statement.setString(2, tipo.getDescricaoTipo());
			statement.setInt(3, tipo.getCodigoModulo());
			statement.setInt(4, tipo.getCodigoFeature());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
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
			if(connection!=null) {
				connection.close();
			}
		}
		return isUpdated;
	}

	@Override
	public Tipo getTipoById(Integer codigo) throws Exception {
		
		Tipo tipo = new Tipo();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_tipo, ");
		query.append("	tipo, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_tipo ");
		query.append("where ");
		query.append("	codigo_tipo = ?");
		
		
		try(PreparedStatement statement = this.connection.prepareStatement(query.toString()))
		{
			statement.setInt(1, codigo);
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				tipo.setCodigoTipo(rs.getInt("codigo_tipo"));
				tipo.setDescricaoTipo(rs.getString("tipo"));
				tipo.setCodigoModulo(rs.getInt("codigo_modulo"));
				tipo.setCodigoFeature(rs.getInt("codigo_feature"));
				tipo.setInativo(rs.getInt("inativo"));
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		return tipo;
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		
		boolean isRemoved = false;
		boolean inativo = false;
		StringBuilder query = new StringBuilder();
		
		query.append("select inativo from tb_tipo where codigo_tipo = ? ");
		
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
		query.append("	tb_tipo ");
		query.append("set ");
		if(!inativo) {
			query.append(" inativo = 1 ");
		} else {
			query.append(" inativo = 0 ");			
		}
		query.append("where ");
		query.append("	codigo_tipo = ? ");
		
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
			if(statement!=null) {
				statement.close();
			}
			if(connection!=null) {
				connection.close();
			}
		}
		return isRemoved;
	}
	
	@Override
	public List<Tipo> getComboTipos() throws Exception {
		
		List<Tipo> tipos = new ArrayList<Tipo>();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_tipo, ");
		query.append("	tipo, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature ");
		query.append("from ");
		query.append("	tb_tipo ");
		query.append("where ");
		query.append("	ifnull(inativo, 0) = 0 ");
		
		try(PreparedStatement statement = this.connection.prepareStatement(query.toString()))
		{	
			statement.execute();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				Tipo tipo = new Tipo();
				tipo.setCodigoTipo(rs.getInt("codigo_tipo"));
				tipo.setDescricaoTipo(rs.getString("tipo"));
				tipo.setCodigoModulo(rs.getInt("codigo_modulo"));
				tipo.setCodigoFeature(rs.getInt("codigo_feature"));
				tipos.add(tipo);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		return tipos;
	}

	@Override
	public List<Tipo> getComboTiposByFeature(Integer codigoModulo, Integer codigoFeature) throws Exception {
		
		List<Tipo> tipos = new ArrayList<Tipo>();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	t.codigo_tipo, ");
		query.append("	concat(t.tipo,' (', t.codigo_tipo,')') as tipo, ");
		query.append("	t.codigo_modulo, ");
		query.append("	m.modulo, ");
		query.append("	t.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	t.inativo ");
		query.append("from ");
		query.append("	tb_tipo t ");
		query.append("	inner join tb_modulo m on t.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on t.codigo_feature=f.codigo_feature ");
		query.append("where ");
		query.append(" 	t.codigo_modulo = ? ");
		query.append(" 	and t.codigo_feature = ? ");
		
		PreparedStatement statement = null;
		try
		{	
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, codigoModulo);
			statement.setInt(2, codigoFeature);
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				Tipo tipo = new Tipo();
				tipo.setCodigoTipo(rs.getInt("codigo_tipo"));
				tipo.setDescricaoTipo(rs.getString("tipo"));
				tipo.setCodigoModulo(rs.getInt("codigo_modulo"));
				tipo.setDescricaoModulo(rs.getString("modulo"));
				tipo.setCodigoFeature(rs.getInt("codigo_feature"));
				tipo.setDescricaoFeature(rs.getString("feature"));
				tipo.setInativo(rs.getInt("inativo"));
				tipos.add(tipo);
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
			if(connection!=null) {
				connection.close();
			}
		}
		return tipos;
	}
}
