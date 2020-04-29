/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.homologador.dao.ComportamentoDao;
import br.com.homologador.model.Comportamento;

public class ComportamentoDaoImpl implements ComportamentoDao {
	
	private Connection connection;
	
	public ComportamentoDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void getAll(Comportamento comportamento) throws Exception {

		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	c.codigo_comportamento, ");
		query.append("	c.comportamento, ");
		query.append("	c.codigo_modulo, ");
		query.append("	m.modulo, ");
		query.append("	c.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	c.codigo_tipo, ");
		query.append("	p.tipo, ");
		query.append("	c.teste_feature, ");
		query.append("	c.data_criacao, ");
		query.append("	ifnull(c.inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_comportamento c ");
		query.append("	inner join tb_modulo m  on c.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on c.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo p on c.codigo_tipo=p.codigo_tipo ");
		query.append("where ");
		query.append("	1 = 1 ");
		if(comportamento.getInativo() > 1) {
			query.append(" 	and ifnull(c.inativo, 0) < ? ");
		} else {
			query.append(" 	and ifnull(c.inativo, 0) = ? ");
		}
		if(comportamento.getCodigoComportamento() > 0) {
			query.append(" 	and c.codigo_comportamento = ? ");			
		} else if(comportamento.getCodigoTipo() > 0) {
			query.append(" 	and c.codigo_modulo = ? ");
			query.append(" 	and c.codigo_feature = ? ");
			query.append(" 	and c.codigo_tipo = ? ");
		} else if(comportamento.getCodigoFeature() > 0) {
			query.append(" 	and c.codigo_modulo = ? ");
			query.append(" 	and c.codigo_feature = ? ");
		} else if(comportamento.getCodigoModulo() > 0) {
			query.append(" 	and c.codigo_modulo = ? ");
		}
				
		PreparedStatement statement = null;
		
		ResultSet rs = null;
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, comportamento.getInativo());
			if(comportamento.getCodigoComportamento() > 0) {
				statement.setInt(2, comportamento.getCodigoComportamento());
			} else if(comportamento.getCodigoTipo() > 0) {
				statement.setInt(2, comportamento.getCodigoModulo());
				statement.setInt(3, comportamento.getCodigoFeature());
				statement.setInt(4, comportamento.getCodigoTipo());				
			} else if(comportamento.getCodigoFeature() > 0) {
				statement.setInt(2, comportamento.getCodigoModulo());				
				statement.setInt(3, comportamento.getCodigoFeature());
			} else if(comportamento.getCodigoModulo() > 0) {
				statement.setInt(2, comportamento.getCodigoModulo());
			}
			statement.executeQuery();
			rs = statement.getResultSet();
			
			while(rs.next()) {
				Comportamento comport = new Comportamento();
				comport.setCodigoComportamento(rs.getInt("codigo_comportamento"));
				comport.setDescricaoComportamento(rs.getString("comportamento"));
				comport.setCodigoModulo(rs.getInt("codigo_modulo"));
				comport.setDescricaoModulo(rs.getString("modulo"));
				comport.setCodigoFeature(rs.getInt("codigo_feature"));
				comport.setDescricaoFeature(rs.getString("feature"));
				comport.setCodigoTipo(rs.getInt("codigo_tipo"));
				comport.setTesteFeature(rs.getInt("teste_feature"));
				comport.setDescricaoTipo(rs.getString("tipo"));
				comport.setDataCriacaoFormatada(rs.getString("data_criacao"));
				comport.setInativo(rs.getInt("inativo"));
				comportamento.add(comport);
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
	}

	@Override
	public boolean remove(Integer codigo) throws SQLException {
		
		boolean isRemoved = false;
		boolean isInactived = false;
		
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_comportamento ");
		query.append("where ");
		query.append("	codigo_comportamento = ? ");
		
		PreparedStatement statement = null;

		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, codigo);
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				isInactived = rs.getBoolean("inativo");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		query = new StringBuilder();
		
		query.append("update ");
		query.append("	tb_comportamento ");
		query.append("set ");
		if(isInactived) {
			query.append("	inativo = 0 ");			
		} else {
			query.append("	inativo = 1 ");						
		}
		query.append("where ");
		query.append("	codigo_comportamento = ? ");
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, codigo);
			statement.executeUpdate();
			
			isRemoved = statement.getUpdateCount() > 0;
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
		return isRemoved;
	}

	@Override
	public Comportamento getComportamentoById(Integer codigo) throws Exception {

		Comportamento comportamento = new Comportamento();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_comportamento, ");
		query.append("	comportamento, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	codigo_tipo, ");
		query.append("	ifnull(teste_feature, 0) as teste_feature, ");
		query.append("	ifnull(inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_comportamento ");
		query.append("where ");
		query.append("	codigo_comportamento = ? ");
						
		try(PreparedStatement statement = this.connection.prepareStatement(query.toString()))
		{
			statement.setInt(1, codigo);
			statement.executeQuery();

			ResultSet rs = statement.getResultSet();
			while(rs.next()) 
			{
				comportamento.setCodigoComportamento(rs.getInt("codigo_comportamento"));
				comportamento.setDescricaoComportamento(rs.getString("comportamento"));
				comportamento.setCodigoModulo(rs.getInt("codigo_modulo"));
				comportamento.setCodigoFeature(rs.getInt("codigo_feature"));
				comportamento.setCodigoTipo(rs.getInt("codigo_tipo"));
				comportamento.setTesteFeature(rs.getInt("teste_feature"));
				comportamento.setInativo(rs.getInt("inativo"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return comportamento;
	}

	@Override
	public boolean adicionar(Comportamento comportamento) throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append(" 	tb_comportamento ");
		query.append("set ");
		query.append("	comportamento = ?, ");
		query.append("	teste_feature = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_comportamento = ? ");

		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, comportamento.getDescricaoComportamento());
			statement.setInt(2, comportamento.getTesteFeature());
			statement.setInt(3, comportamento.getInativo());
			statement.setInt(4, comportamento.getCodigoComportamento());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		
		query.append("insert into tb_comportamento ");
		query.append("(");
		query.append("	comportamento, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	codigo_tipo, ");
		query.append("	teste_feature");
		query.append(") select ?, ?, ?, ?, ? ");
		
		try
		{
			if(!isUpdated) 
			{
				statement = this.connection.prepareStatement(query.toString());
				statement.setString(1, comportamento.getDescricaoComportamento());
				statement.setInt(2, comportamento.getCodigoModulo());
				statement.setInt(3, comportamento.getCodigoFeature());
				statement.setInt(4, comportamento.getCodigoTipo());
				statement.setInt(5, comportamento.getTesteFeature());
				statement.executeUpdate();
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
	public void setUltimoFiltro(Comportamento comportamento) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_filtro ");
		query.append("set ");
		query.append("	codigo_modulo = ?, ");
		query.append("	codigo_feature = ?, ");
		query.append("	codigo_tipo = ?, ");
		query.append("	data_alteracao = now() ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, comportamento.getCodigoModulo());
			statement.setInt(2, comportamento.getCodigoFeature());
			statement.setInt(3, comportamento.getCodigoTipo());
			statement.executeUpdate();
			
			isUpdated = statement.getUpdateCount() > 0;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		query.append("insert into ");
		query.append("	tb_filtro ");
		query.append("(");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	codigo_tipo, ");
		query.append("	data_alteracao");
		query.append(") ");
		query.append("	select ?, ?, ?, now() ");
		
		try
		{
			if(!isUpdated)
			{
				statement = this.connection.prepareStatement(query.toString());
				statement.setInt(1, comportamento.getCodigoModulo());
				statement.setInt(2, comportamento.getCodigoFeature());
				statement.setInt(3, comportamento.getCodigoTipo());
				statement.executeUpdate();
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
	}
}
