/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.homologador.dao.RegraDao;
import br.com.homologador.model.RegraNegocio;

public class RegraDaoImpl implements RegraDao {
	
	private Connection connection;
	
	public RegraDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void getAll(RegraNegocio regraNegocio) throws Exception {

		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	r.codigo_regra, ");
		query.append("	r.regra_negocio, ");
		query.append("	r.codigo_modulo, ");
		query.append("	m.modulo, ");
		query.append("	r.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	r.codigo_tipo, ");
		query.append("	p.tipo, ");
		query.append("	r.teste_feature, ");
		query.append("	r.data_criacao, ");
		query.append("	ifnull(r.inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_regra_negocio r ");
		query.append("	inner join tb_modulo m  on r.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on r.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo p on r.codigo_tipo=p.codigo_tipo ");
		query.append("where ");
		query.append("	1 = 1 ");
		if(regraNegocio.getInativo() > 1) {
			query.append(" 	and ifnull(r.inativo, 0) < ? ");
		} else {
			query.append(" 	and ifnull(r.inativo, 0) = ? ");
		}
		if(regraNegocio.getCodigoRegra() > 0) {
			query.append(" 	and r.codigo_regra = ? ");			
		} else if(regraNegocio.getCodigoTipo() > 0) {
			query.append(" 	and r.codigo_modulo = ? ");
			query.append(" 	and r.codigo_feature = ? ");
			query.append(" 	and r.codigo_tipo = ? ");
		} else if(regraNegocio.getCodigoFeature() > 0) {
			query.append(" 	and r.codigo_modulo = ? ");
			query.append(" 	and r.codigo_feature = ? ");
		} else if(regraNegocio.getCodigoModulo() > 0) {
			query.append(" 	and r.codigo_modulo = ? ");
		}
				
		PreparedStatement statement = null;
		
		ResultSet rs = null;
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, regraNegocio.getInativo());
			if(regraNegocio.getCodigoRegra() > 0) {
				statement.setInt(2, regraNegocio.getCodigoRegra());
			} else if(regraNegocio.getCodigoTipo() > 0) {
				statement.setInt(2, regraNegocio.getCodigoModulo());
				statement.setInt(3, regraNegocio.getCodigoFeature());
				statement.setInt(4, regraNegocio.getCodigoTipo());				
			} else if(regraNegocio.getCodigoFeature() > 0) {
				statement.setInt(2, regraNegocio.getCodigoModulo());				
				statement.setInt(3, regraNegocio.getCodigoFeature());
			} else if(regraNegocio.getCodigoModulo() > 0) {
				statement.setInt(2, regraNegocio.getCodigoModulo());
			}
			statement.executeQuery();
			
			rs = statement.getResultSet();
			while(rs.next()) {
				RegraNegocio regraDeNegocio = new RegraNegocio();
				regraDeNegocio.setCodigoRegra(rs.getInt("codigo_regra"));
				regraDeNegocio.setDescricaoRegraNegocio(rs.getString("regra_negocio"));
				regraDeNegocio.setCodigoModulo(rs.getInt("codigo_modulo"));
				regraDeNegocio.setDescricaoModulo(rs.getString("modulo"));
				regraDeNegocio.setCodigoFeature(rs.getInt("codigo_feature"));
				regraDeNegocio.setDescricaoFeature(rs.getString("feature"));
				regraDeNegocio.setCodigoTipo(rs.getInt("codigo_tipo"));
				regraDeNegocio.setTesteFeature(rs.getInt("teste_feature"));
				regraDeNegocio.setDescricaoTipo(rs.getString("tipo"));
				regraDeNegocio.setDataCriacaoFormatada(rs.getString("data_criacao"));
				regraDeNegocio.setInativo(rs.getInt("inativo"));
				regraNegocio.add(regraDeNegocio);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
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
		query.append("	tb_regra_negocio ");
		query.append("where ");
		query.append("	codigo_regra = ? ");
		
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
		query.append("	tb_regra_negocio ");
		query.append("set ");
		if(isInactived) {
			query.append("	inativo = 0 ");			
		} else {
			query.append("	inativo = 1 ");						
		}
		query.append("where ");
		query.append("	codigo_regra = ? ");
		
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
	public RegraNegocio getRegraById(Integer codigo) throws Exception {
				
		RegraNegocio regraNegocio = new RegraNegocio();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_regra, ");
		query.append("	regra_negocio, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	codigo_tipo, ");
		query.append("	ifnull(teste_feature, 0) as teste_feature, ");
		query.append("	ifnull(inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_regra_negocio ");
		query.append("where ");
		query.append("	codigo_regra = ? ");
						
		try(PreparedStatement statement = this.connection.prepareStatement(query.toString()))
		{
			statement.setInt(1, codigo);
			statement.executeQuery();

			ResultSet rs = statement.getResultSet();
			while(rs.next()) 
			{
				regraNegocio.setCodigoRegra(rs.getInt("codigo_regra"));
				regraNegocio.setDescricaoRegraNegocio(rs.getString("regra_negocio"));
				regraNegocio.setCodigoModulo(rs.getInt("codigo_modulo"));
				regraNegocio.setCodigoFeature(rs.getInt("codigo_feature"));
				regraNegocio.setCodigoTipo(rs.getInt("codigo_tipo"));
				regraNegocio.setTesteFeature(rs.getInt("teste_feature"));
				regraNegocio.setInativo(rs.getInt("inativo"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return regraNegocio;
	}

	@Override
	public boolean adicionar(RegraNegocio regraNegocio) throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("tb_regra_negocio ");
		query.append("set ");
		query.append("	regra_negocio = ?, ");
		query.append("	teste_feature = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_regra = ? ");

		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, regraNegocio.getDescricaoRegraNegocio());
			statement.setInt(2, regraNegocio.getTesteFeature());
			statement.setInt(3, regraNegocio.getInativo());
			statement.setInt(4, regraNegocio.getCodigoRegra());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		
		query.append("insert into tb_regra_negocio ");
		query.append("(");
		query.append("	regra_negocio, ");
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
				statement.setString(1, regraNegocio.getDescricaoRegraNegocio());
				statement.setInt(2, regraNegocio.getCodigoModulo());
				statement.setInt(3, regraNegocio.getCodigoFeature());
				statement.setInt(4, regraNegocio.getCodigoTipo());
				statement.setInt(5, regraNegocio.getTesteFeature());
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
	public void setUltimoFiltro(RegraNegocio regraNegocio) throws Exception {
		
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
			statement.setInt(1, regraNegocio.getCodigoModulo());
			statement.setInt(2, regraNegocio.getCodigoFeature());
			statement.setInt(3, regraNegocio.getCodigoTipo());
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
				statement.setInt(1, regraNegocio.getCodigoModulo());
				statement.setInt(2, regraNegocio.getCodigoFeature());
				statement.setInt(3, regraNegocio.getCodigoTipo());
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
