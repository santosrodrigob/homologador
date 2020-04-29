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

import br.com.homologador.dao.FeatureDao;
import br.com.homologador.model.Feature;

public class FeatureDaoImpl implements FeatureDao {

	private Connection connection;
	
	public FeatureDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public boolean adiciona(Feature feature) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update tb_feature ");
		query.append("set ");
		query.append("	feature = ?, ");
		query.append("	codigo_modulo = ? ");
		query.append("where ");
		query.append("	codigo_feature = ? ");
		
		PreparedStatement statement = null;
		
		try 
		{
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, feature.getDescricaoFeature());
			statement.setInt(2, Integer.valueOf(feature.getCodigoModulo()));
			statement.setInt(3, Integer.valueOf(feature.getCodigoFeature()));
			statement.execute();
			isUpdated = statement.getUpdateCount() > 0;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		query = new StringBuilder();

		try 
		{
			if(!isUpdated) {

				query.append("insert into ");
				query.append("tb_feature");
				query.append("(");
				query.append("	codigo_feature, ");
				query.append("	feature, ");
				query.append("	codigo_modulo ");
				query.append(") ");
				query.append("select ?, ?, ? ");
			
				statement = this.connection.prepareStatement(query.toString());
				statement.setInt(1, Integer.valueOf(feature.getCodigoFeature()));
				statement.setString(2, feature.getDescricaoFeature());
				statement.setInt(3, Integer.valueOf(feature.getCodigoModulo()));
				statement.execute();
				isUpdated = statement.getUpdateCount() > 0;			
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
		return isUpdated;
	}

	@Override
	public List<Feature> getAll() throws Exception {
		
		List<Feature> listaFeatures = new ArrayList<Feature>();

		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	f.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	f.codigo_modulo, ");
		query.append("	m.modulo, ");
		query.append("	ifnull(f.inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_feature f ");
		query.append("inner join tb_modulo m on f.codigo_modulo=m.codigo_modulo");
		
		PreparedStatement statement = null;
				
		try
		{
			statement = connection.prepareStatement(query.toString());
			statement.execute();
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Feature feature = new Feature();
				feature.setCodigoFeature(rs.getInt("codigo_feature"));
				feature.setDescricaoFeature(rs.getString("feature"));
				feature.setCodigoModulo(rs.getInt("codigo_modulo"));
				feature.setDescricaoModulo(rs.getString("modulo"));
				feature.setInativo(rs.getInt("inativo"));
				listaFeatures.add(feature);
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
		return listaFeatures;
	}

	@Override
	public Feature getFeatureById(Integer codigo) throws Exception {

		Feature feature = new Feature();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	f.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	f.codigo_modulo, ");
		query.append("	m.modulo ");
		query.append("from ");
		query.append("	tb_feature f ");
		query.append("	inner join tb_modulo m on f.codigo_modulo=m.codigo_modulo ");
		query.append("where ");
		query.append("	codigo_feature = ? ");
				
		try(PreparedStatement statement = this.connection.prepareStatement(query.toString()))
		{
			statement.setInt(1, codigo);
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				
				feature.setCodigoFeature(rs.getInt("codigo_feature"));
				feature.setDescricaoFeature(rs.getString("feature"));
				feature.setCodigoModulo(rs.getInt("codigo_modulo"));
				feature.setDescricaoModulo(rs.getString("modulo"));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		return feature;
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		
		boolean isRemoved = false;
		boolean inativo = false;
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_feature ");
		query.append("where ");
		query.append("	codigo_feature = ? ");
		
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
		query.append("	tb_feature ");
		query.append("set ");
		if(!inativo) {
			query.append("	inativo = 1 ");
		} else {
			query.append("	inativo = 0 ");			
		}
		query.append("where ");
		query.append("	codigo_feature = ? ");
		
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
	public List<Feature> getComboFeatures() throws Exception {

		List<Feature> features = new ArrayList<Feature>();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_feature, ");
		query.append("	feature, ");
		query.append("	codigo_modulo ");
		query.append("from ");
		query.append("	tb_feature ");
		query.append("where ");
		query.append("	ifnull(inativo, 0) = 0 ");
		
		try(PreparedStatement statement = connection.prepareStatement(query.toString()))
		{
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			while (rs.next()) 
			{
				Feature feature = new Feature();
				feature.setCodigoFeature(rs.getInt("codigo_feature"));
				feature.setDescricaoFeature(rs.getString("feature"));
				feature.setCodigoModulo(rs.getInt("codigo_modulo"));
				features.add(feature);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		return features;
	}

	@Override
	public List<Feature> getComboFeaturesByModulo(Integer codigoModulo) throws Exception {

		List<Feature> features = new ArrayList<Feature>();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_feature, ");
		query.append("	concat(feature,' (', codigo_feature,')') as feature, ");
		query.append("	codigo_modulo ");
		query.append("from ");
		query.append("	tb_feature ");
		query.append("where ");
		query.append("	ifnull(inativo, 0) = 0 ");
		query.append("	and codigo_modulo = ? ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			
			statement.setInt(1, codigoModulo);
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			while (rs.next()) 
			{
				Feature feature = new Feature();
				feature.setCodigoFeature(rs.getInt("codigo_feature"));
				feature.setDescricaoFeature(rs.getString("feature"));
				feature.setCodigoModulo(rs.getInt("codigo_modulo"));
				features.add(feature);
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
	return features;
	}
}
