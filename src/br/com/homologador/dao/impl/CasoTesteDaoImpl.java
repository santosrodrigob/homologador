/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.homologador.dao.CasoTesteDao;
import br.com.homologador.model.CasoTeste;

public class CasoTesteDaoImpl implements CasoTesteDao {
	
	private Connection connection;
	
	public CasoTesteDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void getAll(CasoTeste casoTeste) throws Exception {

		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	c.codigo_caso_teste, ");
		query.append("	c.caso_teste, ");
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
		query.append("	tb_caso_teste c ");
		query.append("	inner join tb_modulo m  on c.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on c.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo p on c.codigo_tipo=p.codigo_tipo ");
		query.append("where ");
		query.append("	1 = 1 ");
		if(casoTeste.getInativo() > 1) {
			query.append(" 	and ifnull(c.inativo, 0) < ? ");
		} else {
			query.append(" 	and ifnull(c.inativo, 0) = ? ");
		}
		if(casoTeste.getCodigoCasoTeste() > 0) {
			query.append(" 	and c.codigo_caso_teste = ? ");			
		} else if(casoTeste.getCodigoTipo() > 0) {
			query.append(" 	and c.codigo_modulo = ? ");
			query.append(" 	and c.codigo_feature = ? ");
			query.append(" 	and c.codigo_tipo = ? ");
		} else if(casoTeste.getCodigoFeature() > 0) {
			query.append(" 	and c.codigo_modulo = ? ");
			query.append(" 	and c.codigo_feature = ? ");
		} else if(casoTeste.getCodigoModulo() > 0) {
			query.append(" 	and c.codigo_modulo = ? ");
		}
				
		PreparedStatement statement = null;
		
		ResultSet rs = null;
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, casoTeste.getInativo());
			if(casoTeste.getCodigoCasoTeste() > 0) {
				statement.setInt(2, casoTeste.getCodigoCasoTeste());
			} else if(casoTeste.getCodigoTipo() > 0) {
				statement.setInt(2, casoTeste.getCodigoModulo());
				statement.setInt(3, casoTeste.getCodigoFeature());
				statement.setInt(4, casoTeste.getCodigoTipo());				
			} else if(casoTeste.getCodigoFeature() > 0) {
				statement.setInt(2, casoTeste.getCodigoModulo());				
				statement.setInt(3, casoTeste.getCodigoFeature());
			} else if(casoTeste.getCodigoModulo() > 0) {
				statement.setInt(2, casoTeste.getCodigoModulo());
			}
			statement.executeQuery();
			rs = statement.getResultSet();
			
			while(rs.next()) {
				CasoTeste casoDeTeste = new CasoTeste();
				casoDeTeste.setCodigoCasoTeste(rs.getInt("codigo_caso_teste"));
				casoDeTeste.setDescricaoCasoTeste(rs.getString("caso_teste"));
				casoDeTeste.setCodigoModulo(rs.getInt("codigo_modulo"));
				casoDeTeste.setDescricaoModulo(rs.getString("modulo"));
				casoDeTeste.setCodigoFeature(rs.getInt("codigo_feature"));
				casoDeTeste.setDescricaoFeature(rs.getString("feature"));
				casoDeTeste.setCodigoTipo(rs.getInt("codigo_tipo"));
				casoDeTeste.setTesteFeature(rs.getInt("teste_feature"));
				casoDeTeste.setDescricaoTipo(rs.getString("tipo"));
				casoDeTeste.setDataCriacaoFormatada(rs.getString("data_criacao"));
				casoDeTeste.setInativo(rs.getInt("inativo"));
				casoTeste.add(casoDeTeste);
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
		query.append("	tb_caso_teste ");
		query.append("where ");
		query.append("	codigo_caso_teste = ? ");
		
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
		query.append("	tb_caso_teste ");
		query.append("set ");
		if(isInactived) {
			query.append("	inativo = 0 ");			
		} else {
			query.append("	inativo = 1 ");						
		}
		query.append("where ");
		query.append("	codigo_caso_teste = ? ");
		
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
	public CasoTeste getCasoById(Integer codigo) throws Exception {

		CasoTeste casoTeste = new CasoTeste();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_caso_teste, ");
		query.append("	caso_teste, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	codigo_tipo, ");
		query.append("	ifnull(teste_feature, 0) as teste_feature, ");
		query.append("	ifnull(inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_caso_teste ");
		query.append("where ");
		query.append("	codigo_caso_teste = ? ");
						
		try(PreparedStatement statement = this.connection.prepareStatement(query.toString()))
		{
			statement.setInt(1, codigo);
			statement.executeQuery();

			ResultSet rs = statement.getResultSet();
			while(rs.next()) 
			{
				casoTeste.setCodigoCasoTeste(rs.getInt("codigo_caso_teste"));
				casoTeste.setDescricaoCasoTeste(rs.getString("caso_teste"));
				casoTeste.setCodigoModulo(rs.getInt("codigo_modulo"));
				casoTeste.setCodigoFeature(rs.getInt("codigo_feature"));
				casoTeste.setCodigoTipo(rs.getInt("codigo_tipo"));
				casoTeste.setTesteFeature(rs.getInt("teste_feature"));
				casoTeste.setInativo(rs.getInt("inativo"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return casoTeste;
	}

	@Override
	public boolean adicionar(CasoTeste casoTeste) throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append(" 	tb_caso_teste ");
		query.append("set ");
		query.append("	caso_teste = ?, ");
		query.append("	teste_feature = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_caso_teste = ? ");

		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, casoTeste.getDescricaoCasoTeste());
			statement.setInt(2, casoTeste.getTesteFeature());
			statement.setInt(3, casoTeste.getInativo());
			statement.setInt(4, casoTeste.getCodigoCasoTeste());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		
		query.append("insert into tb_caso_teste ");
		query.append("(");
		query.append("	caso_teste, ");
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
				statement.setString(1, casoTeste.getDescricaoCasoTeste());
				statement.setInt(2, casoTeste.getCodigoModulo());
				statement.setInt(3, casoTeste.getCodigoFeature());
				statement.setInt(4, casoTeste.getCodigoTipo());
				statement.setInt(5, casoTeste.getTesteFeature());
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
	public void setUltimoFiltro(CasoTeste casoTeste) throws Exception {
		
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
			statement.setInt(1, casoTeste.getCodigoModulo());
			statement.setInt(2, casoTeste.getCodigoFeature());
			statement.setInt(3, casoTeste.getCodigoTipo());
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
				statement.setInt(1, casoTeste.getCodigoModulo());
				statement.setInt(2, casoTeste.getCodigoFeature());
				statement.setInt(3, casoTeste.getCodigoTipo());
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
