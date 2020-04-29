/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.homologador.dao.TesteDao;
import br.com.homologador.model.CasoTeste;
import br.com.homologador.model.Comportamento;
import br.com.homologador.model.RegraNegocio;
import br.com.homologador.model.Teste;
import br.com.homologador.model.vo.TesteItens;

public class TesteDaoImpl implements TesteDao {
	
	private Connection connection;
	
	public TesteDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}
	
	@Override
	public Integer adicionaTeste(Teste teste) throws Exception {
		
		boolean isUpdated = false;
		Integer codigoTeste = 0;
		StringBuilder query = new StringBuilder();

		query.append("update ");
		query.append("	tb_teste ");
		query.append("set ");
		query.append("	conclusao = ?, ");
		query.append("	observacao = ?, ");
		query.append("	situacao_regra = ?, ");
		query.append("	situacao_caso_teste = ?, ");
		query.append("	situacao_comportamento = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_teste = ? ");

		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, teste.getConclusao());
			statement.setString(2, teste.getObservacao());
			statement.setInt(3, teste.getSituacaoRegra());
			statement.setInt(4, teste.getSituacaoCasoTeste());
			statement.setInt(5, teste.getSituacaoComportamento());
			statement.setInt(6, teste.getInativo());
			statement.setInt(7, teste.getCodigoTeste());
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
			if(isUpdated) {
				if(statement!=null) {
					statement.close();
				}
				if(connection!=null) {
					connection.close();
				}				
			}
		}
		
		try 
		{
			if(!isUpdated) 
			{
				query = new StringBuilder();
	
				query.append("insert into tb_teste ");
				query.append("(");
				query.append("	codigo_modulo, ");
				query.append("	codigo_feature, ");
				query.append("	codigo_tipo, ");
				query.append("	data_criacao ");
				query.append(")");
				query.append("select ?, ?, ?, now()");

				statement = this.connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
				this.connection.setAutoCommit(false);

				statement.setInt(1, teste.getCodigoModulo());
				statement.setInt(2, teste.getCodigoFeature());
				statement.setInt(3, teste.getCodigoTipo());
				statement.executeUpdate();

				ResultSet rs = statement.getGeneratedKeys();
				while (rs.next()) 
				{
					codigoTeste = rs.getInt(1);
				}

				if(codigoTeste == 0) {
					connection.rollback();
				}
			}
		}
		catch (Exception e)
		{
			connection.rollback();
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally 
		{
			if(statement!=null) {
				statement.close();
			}
		}
		return codigoTeste;
	}
	
	@Override
	public void getAll(Teste teste) throws Exception {
		
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	t.codigo_teste, ");
		query.append("	t.codigo_modulo, ");
		query.append("	m.modulo, ");
		query.append("	t.codigo_feature, ");
		query.append("	f.feature, ");
		query.append("	t.codigo_tipo, ");
		query.append("	p.tipo, ");
		query.append("	t.data_criacao, ");
		query.append("	ifnull(t.situacao_caso_teste, 0) as situacao_caso_teste, ");
		query.append("	ifnull(t.situacao_regra, 0) as situacao_regra, ");
		query.append("	ifnull(t.situacao_comportamento, 0) as situacao_comportamento, ");
		query.append("	ifnull(t.conclusao, ' - ') as conclusao, ");
		query.append("	ifnull(t.observacao, ' - ') as observacao, ");
		query.append("	ifnull((select count(codigo_bug) from tb_bugs where ifnull(tipo, 3) not in (0, 1) and codigo_teste=t.codigo_teste), 0) as qtde_bugs, ");
		query.append("	ifnull(t.inativo, 0) as inativo ");
		query.append("from ");
		query.append("	tb_teste t ");
		query.append("	inner join tb_modulo m  on t.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on t.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo p on t.codigo_tipo=p.codigo_tipo ");
		query.append("where 1=1 ");
		if(teste.getInativo() == 1) {
			query.append(" 	and ifnull(t.inativo,0) = 1 ");
		} else if(teste.getInativo() == 0) {
			query.append(" 	and ifnull(t.inativo,0) = 0 ");			
		}
		if(teste.getCodigoTeste() > 0) {
			query.append(" 	and t.codigo_teste = ? ");
		} else if(teste.getCodigoTipo() > 0) {
			query.append(" 	and t.codigo_modulo = ? ");
			query.append(" 	and t.codigo_feature = ? ");
			query.append(" 	and t.codigo_tipo = ? ");
		} else if(teste.getCodigoFeature() > 0) {
			query.append(" 	and t.codigo_modulo = ? ");
			query.append(" 	and t.codigo_feature = ? ");
		} else if(teste.getCodigoModulo() > 0) {
			query.append(" 	and t.codigo_modulo = ? ");
		}
		
		PreparedStatement statement = null;
		
		try
		{
			statement = connection.prepareStatement(query.toString());
			if(teste.getCodigoTeste() > 0) {
				statement.setInt(1, teste.getCodigoTeste());				
			} else if(teste.getCodigoTipo() > 0) {
				statement.setInt(1, teste.getCodigoModulo());
				statement.setInt(2, teste.getCodigoFeature());
				statement.setInt(3, teste.getCodigoTipo());
			} else if(teste.getCodigoFeature() > 0) {
				statement.setInt(1, teste.getCodigoModulo());
				statement.setInt(2, teste.getCodigoFeature());
			} else if(teste.getCodigoModulo() > 0) {
				statement.setInt(1, teste.getCodigoModulo());
			}

			statement.executeQuery();
			try(ResultSet rs = statement.getResultSet()) {
				
				while(rs.next()) {

					TesteItens testeItens = new TesteItens();		
					testeItens.setCodigoTeste(rs.getInt("codigo_teste"));
					testeItens.setCodigoModulo(rs.getInt("codigo_modulo"));
					testeItens.setDescricaoModulo(rs.getString("modulo"));
					testeItens.setCodigoFeature(rs.getInt("codigo_feature"));
					testeItens.setDescricaoFeature(rs.getString("feature"));
					testeItens.setCodigoTipo(rs.getInt("codigo_tipo"));
					testeItens.setDescricaoTipo(rs.getString("tipo"));
					testeItens.setDataCriacaoFormatada(rs.getString("data_criacao"));
					testeItens.setSituacaoCasoTeste(rs.getInt("situacao_caso_teste"));
					testeItens.setSituacaoRegra(rs.getInt("situacao_regra"));
					testeItens.setSituacaoComportamento(rs.getInt("situacao_comportamento"));
					testeItens.setConclusao(rs.getString("conclusao"));
					testeItens.setObservacao(rs.getString("observacao"));
					testeItens.setQtdeBugs(rs.getInt("qtde_bugs"));
					testeItens.setInativo(rs.getInt("inativo"));
					teste.add(testeItens);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			connection.rollback();
			throw new RuntimeException();
		}
		finally 
		{
			if(statement!=null) {
				statement.close();
			}
			if(connection!=null) {
				connection.commit();
				connection.close();
			}
		}
	}

	@Override
	public boolean adiciona(Teste teste, Integer codigoTeste) throws Exception {

		boolean isUpdated = false;
		List<CasoTeste> listaCasoTeste = new ArrayList<CasoTeste>();
		List<RegraNegocio> listaRegra = new ArrayList<RegraNegocio>();
		List<Comportamento> listaCompotamento = new ArrayList<Comportamento>();

		PreparedStatement statement = null;

		StringBuilder query = new StringBuilder();		

		query.append("select ");
		query.append("	codigo_regra ");
		query.append("from ");
		query.append("	tb_regra_negocio ");
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		query.append("	and codigo_feature = ? ");
		query.append("	and codigo_tipo = ? ");
		query.append("	and ifnull(inativo, 0) = 0 ");
		query.append("	and ifnull(teste_feature, 0) = 0 ");
		query.append(" union all ");
		query.append("select ");
		query.append("	codigo_regra ");
		query.append("from ");
		query.append("	tb_regra_negocio ");
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		query.append("	and codigo_feature = ? ");
		query.append("	and ifnull(inativo, 0) = 0");
		query.append("	and ifnull(teste_feature, 0) = 1");

		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			this.connection.setAutoCommit(false);

			statement.setInt(1, teste.getCodigoModulo());
			statement.setInt(2, teste.getCodigoFeature());
			statement.setInt(3, teste.getCodigoTipo());
			statement.setInt(4, teste.getCodigoModulo());
			statement.setInt(5, teste.getCodigoFeature());
			statement.executeQuery();

			try(ResultSet rs = statement.getResultSet()) 
			{
				while(rs.next()) {
					
					RegraNegocio regraNegocio = new RegraNegocio();
					regraNegocio.setCodigoRegra(rs.getInt("codigo_regra"));
					listaRegra.add(regraNegocio);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		query = new StringBuilder();		

		query.append("select ");
		query.append("	codigo_caso_teste ");
		query.append("from ");
		query.append("	tb_caso_teste ");
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		query.append("	and codigo_feature = ? ");
		query.append("	and codigo_tipo = ? ");
		query.append("	and ifnull(inativo, 0) = 0 ");
		query.append("	and ifnull(teste_feature, 0) = 0 ");
		query.append(" union all ");
		query.append("select ");
		query.append("	codigo_caso_teste ");
		query.append("from ");
		query.append("	tb_caso_teste ");
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		query.append("	and codigo_feature = ? ");
		query.append("	and ifnull(inativo, 0) = 0 ");
		query.append("	and ifnull(teste_feature, 0) = 1 ");

		try
		{
			statement = this.connection.prepareStatement(query.toString());
			this.connection.setAutoCommit(false);

			statement.setInt(1, teste.getCodigoModulo());
			statement.setInt(2, teste.getCodigoFeature());
			statement.setInt(3, teste.getCodigoTipo());
			statement.setInt(4, teste.getCodigoModulo());
			statement.setInt(5, teste.getCodigoFeature());
			statement.executeQuery();

				try(ResultSet rs = statement.getResultSet()) {

				while(rs.next()) {

					CasoTeste casoTeste = new CasoTeste();
					casoTeste.setCodigoCasoTeste(rs.getInt("codigo_caso_teste"));
					listaCasoTeste.add(casoTeste);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			connection.rollback();
			throw new RuntimeException();
		}
		
		query = new StringBuilder();		

		query.append("select ");
		query.append("	codigo_comportamento ");
		query.append("from ");
		query.append("	tb_comportamento ");
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		query.append("	and codigo_feature = ? ");
		query.append("	and codigo_tipo = ? ");
		query.append("	and ifnull(inativo, 0) = 0");
		query.append("	and ifnull(teste_feature, 0) = 0 ");
		query.append(" union all ");
		query.append("select ");
		query.append("	codigo_comportamento ");
		query.append("from ");
		query.append("	tb_comportamento ");
		query.append("where ");
		query.append("	codigo_modulo = ? ");
		query.append("	and codigo_feature = ? ");
		query.append("	and ifnull(inativo, 0) = 0 ");
		query.append("	and ifnull(teste_feature, 0) = 1 ");

		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			this.connection.setAutoCommit(false);

			statement.setInt(1, teste.getCodigoModulo());
			statement.setInt(2, teste.getCodigoFeature());
			statement.setInt(3, teste.getCodigoTipo());
			statement.setInt(4, teste.getCodigoModulo());
			statement.setInt(5, teste.getCodigoFeature());
			statement.executeQuery();

			try(ResultSet rs = statement.getResultSet()) {

				while(rs.next()) {
					
					Comportamento comportamento = new Comportamento();
					comportamento.setCodigoComportamento(rs.getInt("codigo_comportamento"));
					listaCompotamento.add(comportamento);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		try
		{
 			if(listaRegra != null && listaRegra.size() > 0)
			{
				for (RegraNegocio regraNegocio : listaRegra) 
				{
					query = new StringBuilder();
	
					query.append("insert into tb_teste_atributo ");
					query.append("(");
					query.append("	codigo_teste, ");
					query.append("	codigo_modulo, ");
					query.append("	codigo_feature, ");
					query.append("	codigo_tipo, ");
					query.append("	codigo_regra, ");
					query.append("	codigo_caso_teste, ");
					query.append("	codigo_comportamento, ");
					query.append("	data_hora_ini");
					query.append(")");
					query.append("select ?, ?, ?, ?, ?, ?, ?, now()");

					try
					{
						statement = this.connection.prepareStatement(query.toString());
						this.connection.setAutoCommit(false);
													
						statement.setInt(1, codigoTeste);
						statement.setInt(2, teste.getCodigoModulo());
						statement.setInt(3, teste.getCodigoFeature());
						statement.setInt(4, teste.getCodigoTipo());
						statement.setInt(5, regraNegocio.getCodigoRegra());
						statement.setNull(6, Types.INTEGER);
						statement.setNull(7, Types.INTEGER);
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
						if(statement!=null)
						{
							statement.close();
						}
					}
				}
			} else {
				isUpdated = true;
			}

			if(isUpdated && listaCasoTeste != null && listaCasoTeste.size() > 0)
			{
				for (CasoTeste casoTeste : listaCasoTeste) 
				{
					query = new StringBuilder();
					query.append("insert into tb_teste_atributo ");
					query.append("(");
					query.append("	codigo_teste, ");
					query.append("	codigo_modulo, ");
					query.append("	codigo_feature, ");
					query.append("	codigo_tipo, ");
					query.append("	codigo_regra, ");
					query.append("	codigo_caso_teste, ");
					query.append("	codigo_comportamento, ");
					query.append("	data_hora_ini");
					query.append(")");
					query.append("select ?, ?, ?, ?, ?, ?, ?, now()");

					try 
					{
						statement = this.connection.prepareStatement(query.toString());
						this.connection.setAutoCommit(false);
													
						statement.setInt(1, codigoTeste);
						statement.setInt(2, teste.getCodigoModulo());
						statement.setInt(3, teste.getCodigoFeature());
						statement.setInt(4, teste.getCodigoTipo());
						statement.setNull(5, Types.INTEGER);
						statement.setInt(6, casoTeste.getCodigoCasoTeste());
						statement.setNull(7, Types.INTEGER);
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
						if(statement!=null)
						{
							statement.close();
						}
					}
				}
			} else {
				isUpdated = true;
			}
			
			if(isUpdated && listaCompotamento != null && listaCompotamento.size() > 0)
			{
				for (Comportamento comportamento : listaCompotamento) 
				{
					query = new StringBuilder();

					query.append("update ");
					query.append("	tb_teste_atributo ");
					query.append("set ");
					query.append("	data_hora_fim = now(), ");
					query.append("	situacao = ?, ");
					query.append("	conclusao = ?, ");
					query.append("	observacao = ?, ");
					query.append("	inativo = ? ");
					query.append("where ");
					query.append("	codigo_teste = ? ");
					query.append("	and codigo_comportamento = ? ");

					try 
					{
						statement = this.connection.prepareStatement(query.toString());
						this.connection.setAutoCommit(false);

						statement.setInt(1, Types.BIT);
						statement.setInt(2, Types.VARCHAR);
						statement.setInt(3, Types.VARCHAR);
						statement.setInt(4, Types.BIT);
						statement.setInt(5, codigoTeste);
						statement.setInt(6, comportamento.getCodigoComportamento());
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
						if(statement!=null)
						{
							statement.close();
						}
					}

					query = new StringBuilder();

					query.append("insert into tb_teste_atributo ");
					query.append("(");
					query.append("	codigo_teste, ");
					query.append("	codigo_modulo, ");
					query.append("	codigo_feature, ");
					query.append("	codigo_tipo, ");
					query.append("	codigo_regra, ");
					query.append("	codigo_caso_teste, ");
					query.append("	codigo_comportamento, ");
					query.append("	data_hora_ini");
					query.append(")");
					query.append("select ?, ?, ?, ?, ?, ?, ?, now()");

					try 
					{
						statement = this.connection.prepareStatement(query.toString());
						this.connection.setAutoCommit(false);
													
						statement.setInt(1, codigoTeste);
						statement.setInt(2, teste.getCodigoModulo());
						statement.setInt(3, teste.getCodigoFeature());
						statement.setInt(4, teste.getCodigoTipo());
						statement.setNull(5, Types.INTEGER);
						statement.setNull(6, Types.INTEGER);
						statement.setInt(7, comportamento.getCodigoComportamento());
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
						if(statement!=null)
						{
							statement.close();
						}
					}
				}
			}
		}
		catch (Exception e) 
		{
			connection.rollback();
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally 
		{
			if(connection!=null) 
			{
				if(isUpdated) {
					connection.commit();
				} else {
					connection.rollback();
				}
				connection.close();
			}			
			if(statement!=null) 
			{
				statement.close();
			}
		}
	return isUpdated;
	}

	@Override
	public boolean remove(Integer codigo) throws Exception {
		
		boolean isRemoved = false;
		boolean inativo = false;
		StringBuilder query = new StringBuilder();
		
		query.append("select inativo from tb_teste where codigo_teste = ? ");
		
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
		query.append("	tb_teste ");
		query.append("set ");
		if(!inativo) {
			query.append("	inativo = 1 ");
		} else {
			query.append("	inativo = 0 ");			
		}
		query.append("where ");
		query.append("	codigo_teste = ? ");
		
		try
		{
			statement = connection.prepareStatement(query.toString());
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
			if(connection!=null) 
			{
				connection.close();
			}			
			if(statement!=null) 
			{
				statement.close();
			}
		}
		return isRemoved;
	}

	@Override
	public Teste getTesteById(Integer codigo) throws Exception {
		
		Teste teste = new Teste();
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_teste, ");
		query.append("	codigo_modulo, ");
		query.append("	codigo_feature, ");
		query.append("	codigo_tipo, ");
		query.append("	data_criacao, ");
		query.append("	situacao_regra, ");
		query.append("	situacao_caso_teste, ");
		query.append("	situacao_comportamento, ");
		query.append("	conclusao, ");
		query.append("	observacao, ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_teste ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		
		try(PreparedStatement statement = connection.prepareStatement(query.toString()))
		{
			statement.setInt(1, codigo);
			statement.executeQuery();			

			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				
				teste.setCodigoTeste(rs.getInt("codigo_teste"));
				teste.setCodigoModulo(rs.getInt("codigo_modulo"));
				teste.setCodigoFeature(rs.getInt("codigo_feature"));
				teste.setCodigoTipo(rs.getInt("codigo_tipo"));
				teste.setDataCriacaoFormatada(rs.getString("data_criacao"));
				teste.setSituacaoCasoTeste(rs.getInt("situacao_caso_teste"));
				teste.setSituacaoRegra(rs.getInt("situacao_regra"));
				teste.setSituacaoComportamento(rs.getInt("situacao_comportamento"));
				teste.setConclusao(rs.getString("conclusao"));
				teste.setObservacao(rs.getString("observacao"));
				teste.setInativo(rs.getInt("inativo"));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		return teste;
	}
}
