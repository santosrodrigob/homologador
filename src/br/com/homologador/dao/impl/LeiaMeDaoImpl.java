/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import br.com.homologador.dao.LeiaMeDao;
import br.com.homologador.model.LeiaMe;
import br.com.homologador.model.vo.Filtro;

public class LeiaMeDaoImpl implements LeiaMeDao {
	
	Connection connection;
	
	public LeiaMeDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public boolean adiciona(LeiaMe leiaMe) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update tb_leia_me ");
		query.append("set ");
		query.append("	leia_me = ?, ");
		query.append("	versao = ?, ");
		query.append("	codigo_modulo = ?, ");
		query.append("	codigo_feature = ?, ");
		query.append("	inativo = ?, ");
		if(null!=leiaMe.getSolicitante()) {
			query.append("	solicitante = ?, ");
		}
		query.append("	data_alteracao = now() ");
		query.append("where ");
		query.append("	codigo_leia_me = ? ");
		
		PreparedStatement statement = null;
		
		try 
		{
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, leiaMe.getDescricaoLeiaMe());
			statement.setString(2, leiaMe.getVersao());
			statement.setInt(3, Integer.valueOf(leiaMe.getCodigoModulo()));
			statement.setInt(4, Integer.valueOf(leiaMe.getCodigoFeature()));
			statement.setInt(5, Integer.valueOf(leiaMe.getInativo()));
			if(null!=leiaMe.getSolicitante()) {
				statement.setString(6, leiaMe.getSolicitante());				
			}
			statement.setInt(7, Integer.valueOf(leiaMe.getCodigoLeiaMe()));
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
				query.append("tb_leia_me");
				query.append("(");
				query.append("	leia_me, ");
				query.append("	versao, ");
				query.append("	codigo_modulo, ");
				query.append("	codigo_feature, ");
				if(null!=leiaMe.getSolicitante()) {
					query.append("	solicitante, ");
				}
				query.append("	data_criacao ");
				query.append(") ");
				query.append("select ?, ?, ?, ?,");
				if(null!=leiaMe.getSolicitante()) {
					query.append(" ?, ");
				}
				query.append(" now() ");
			
				statement = this.connection.prepareStatement(query.toString());
				statement.setString(1, leiaMe.getDescricaoLeiaMe());
				statement.setString(2, leiaMe.getVersao());
				statement.setInt(3, Integer.valueOf(leiaMe.getCodigoModulo()));
				statement.setInt(4, Integer.valueOf(leiaMe.getCodigoFeature()));
				if(null!=leiaMe.getSolicitante()) {
					statement.setString(5, leiaMe.getSolicitante());					
				}
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
	public void setUltimoFiltro(LeiaMe leiaMe) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_filtro ");
		query.append("set ");
		query.append("	codigo_modulo_leia_me = ?, ");
		query.append("	versao_leia_me = ?, ");
		if(leiaMe.getCodigoFeature()>0 && null!=leiaMe.getSolicitante()) {
			query.append("	codigo_feature_leia_me = ?, ");
			query.append("	solicitante = ?, ");
		} else if(leiaMe.getCodigoFeature()>0) {
			query.append("	codigo_feature_leia_me = ?, ");
		} else if(null!=leiaMe.getSolicitante()) {
			query.append("	solicitante = ?, ");
		}
		query.append("	data_alteracao = now() ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, leiaMe.getCodigoModulo());
			statement.setString(2, leiaMe.getVersao());
			if(leiaMe.getCodigoFeature()>0 && null!=leiaMe.getSolicitante()) {
				statement.setInt(3, leiaMe.getCodigoFeature());
				statement.setString(4, leiaMe.getSolicitante());
			} else if(leiaMe.getCodigoFeature()>0) {
				statement.setInt(3, leiaMe.getCodigoFeature());
			} else if(null!=leiaMe.getSolicitante()) {
				statement.setString(3, leiaMe.getSolicitante());				
			}
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
		query.append("	codigo_modulo_leia_me, ");
		query.append("	codigo_feature_leia_me, ");
		query.append("	versao_leia_me, ");
		if(null!=leiaMe.getSolicitante()) {
			query.append("	solicitante, ");			
		}
		query.append("	data_criacao");
		query.append(") ");
		query.append("	select ?, ?, ?, ?, now() ");
		
		try
		{
			if(!isUpdated)
			{
				statement = this.connection.prepareStatement(query.toString());
				statement.setInt(1, leiaMe.getCodigoModulo());
				statement.setInt(2, leiaMe.getCodigoFeature());
				statement.setString(3, leiaMe.getVersao());
				if(null!=leiaMe.getSolicitante()) {
					statement.setString(4, leiaMe.getSolicitante());
				}
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

	@Override
	public void getAll(LeiaMe leiaMe) throws Exception {

		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	l.codigo_leia_me, ");
		query.append("	l.leia_me, ");
		query.append("	l.versao, ");
		query.append("	l.solicitante, ");
		query.append("	l.codigo_modulo, ");
		query.append("	m.modulo, ");
		query.append("	l.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	ifnull(l.codigo_usuario, 0) as codigo_usuario, ");
		query.append("	l.data_criacao, ");
		query.append("	l.data_alteracao, ");
		query.append("	ifnull(l.inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_leia_me l ");
		query.append("	inner join tb_modulo m  on l.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f  on l.codigo_modulo=f.codigo_modulo and l.codigo_feature=f.codigo_feature ");
		query.append("where ");
		query.append("	1 = 1 ");
		if(leiaMe.getInativo() > 1) {
			query.append(" 	and ifnull(l.inativo, 0) < ? ");
		} else {
			query.append(" 	and ifnull(l.inativo, 0) = ? ");
		}
		if(leiaMe.getDescricaoLeiaMe() != null && leiaMe.getCodigoModulo() > 0) {
			query.append(" 	and l.leia_me like ? ");
			query.append(" 	and l.codigo_modulo = ? ");
		} else if(leiaMe.getDescricaoLeiaMe() != null) {
			query.append(" 	and l.leia_me like ? ");
		} else if(leiaMe.getCodigoModulo() > 0) {
			query.append(" 	and l.codigo_modulo = ? ");
		}
				
		PreparedStatement statement = null;
		
		ResultSet rs = null;
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, leiaMe.getInativo());
			if(leiaMe.getDescricaoLeiaMe() != null && leiaMe.getCodigoModulo() > 0) {
				statement.setString(2, '%'+ leiaMe.getDescricaoLeiaMe()+'%');
				statement.setInt(3, leiaMe.getCodigoModulo());
			} else if(leiaMe.getDescricaoLeiaMe() != null) {
				statement.setString(2, '%'+ leiaMe.getDescricaoLeiaMe()+'%');
			} else if(leiaMe.getCodigoModulo() > 0) {
				statement.setInt(2, leiaMe.getCodigoModulo());
			}
			statement.executeQuery();
			
			rs = statement.getResultSet();
			while(rs.next()) {
				LeiaMe leia = new LeiaMe();
				leia.setCodigoLeiaMe(rs.getInt("codigo_leia_me"));
				leia.setDescricaoLeiaMe(rs.getString("leia_me"));
				leia.setVersao(rs.getString("versao"));
				leia.setCodigoModulo(rs.getInt("codigo_modulo"));
				leia.setDescricaoModulo(rs.getString("modulo"));
				leia.setCodigoFeature(rs.getInt("codigo_feature"));
				leia.setDescricaoFeature(rs.getString("feature"));
				leia.setSolicitante(rs.getString("solicitante"));
				leia.setCodigoUsuarioCriacao(rs.getInt("codigo_usuario"));
				if(rs.getString("data_criacao") != null) {
					LocalDate ldCriacao = LocalDate.parse(rs.getString("data_criacao"));
					leia.setDataCriacao(ldCriacao);
				}
				if(rs.getString("data_alteracao") != null) {
					LocalDate ldAlteracao = LocalDate.parse(rs.getString("data_alteracao"));
					leia.setDataAlteracao(ldAlteracao);
				}
				leia.setInativo(rs.getInt("inativo"));
				leiaMe.add(leia);
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
	public Filtro getUltimoFiltro() throws Exception {
		
		Filtro filtro = new Filtro();
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	l.codigo_modulo_leia_me, ");
		query.append("	l.codigo_feature_leia_me, ");
		query.append("	l.solicitante, ");
		query.append("	l.versao_leia_me ");
		query.append("from ");
		query.append("	tb_filtro l ");
		query.append("	inner join tb_modulo m on l.codigo_modulo_leia_me=m.codigo_modulo ");
		query.append("	inner join tb_feature f on l.codigo_modulo_leia_me=f.codigo_modulo and l.codigo_feature_leia_me=f.codigo_feature ");

		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.executeQuery();
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				filtro.setCodigoModulo(rs.getInt("codigo_modulo_leia_me"));
				filtro.setCodigoFeature(rs.getInt("codigo_feature_leia_me"));
				filtro.setSolicitante(rs.getString("solicitante"));
				filtro.setVersao(rs.getString("versao_leia_me"));
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

	@Override
	public LeiaMe getLeiaMeById(Integer codigo) throws Exception {
		
		LeiaMe leiaMe = new LeiaMe();
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_leia_me, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	leia_me, ");
		query.append("	versao, ");
		query.append("	solicitante, ");
		query.append("	codigo_usuario, ");
		query.append("	data_criacao, ");
		query.append("	data_alteracao, ");
		query.append("	ifnull(inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_leia_me ");
		query.append("where ");
		query.append("	codigo_leia_me = ?");
						
		try(PreparedStatement statement = this.connection.prepareStatement(query.toString()))
		{
			statement.setInt(1, codigo);
			statement.executeQuery();

			ResultSet rs = statement.getResultSet();
			while(rs.next()) 
			{
				leiaMe.setCodigoLeiaMe(rs.getInt("codigo_leia_me"));
				leiaMe.setDescricaoLeiaMe(rs.getString("leia_me"));
				leiaMe.setCodigoModulo(rs.getInt("codigo_modulo"));
				leiaMe.setCodigoFeature(rs.getInt("codigo_feature"));
				leiaMe.setVersao(rs.getString("versao"));
				leiaMe.setSolicitante(rs.getString("solicitante"));
				leiaMe.setCodigoUsuarioCriacao(rs.getInt("codigo_usuario"));
				if(rs.getString("data_criacao") != null) {
					LocalDate ldCriacao = LocalDate.parse(rs.getString("data_criacao"));
					leiaMe.setDataCriacao(ldCriacao);
				}
				if(rs.getString("data_alteracao") != null) {
					LocalDate ldAlteracao = LocalDate.parse(rs.getString("data_alteracao"));
					leiaMe.setDataAlteracao(ldAlteracao);
				}
				leiaMe.setInativo(rs.getInt("inativo"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return leiaMe;
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
	
		boolean isRemoved = false;
		boolean isInactived = false;
		
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_leia_me ");
		query.append("where ");
		query.append("	codigo_leia_me = ? ");
		
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
		query.append("	tb_leia_me ");
		query.append("set ");
		if(isInactived) {
			query.append("	inativo = 0 ");			
		} else {
			query.append("	inativo = 1 ");						
		}
		query.append("where ");
		query.append("	codigo_leia_me = ? ");
		
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
}
