/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.homologador.dao.TestesAtributosDao;
import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.TesteCasoTeste;
import br.com.homologador.model.vo.TesteComportamento;
import br.com.homologador.model.vo.TesteRegra;

public class TestesAtributosDaoImpl implements TestesAtributosDao {
	
	private Connection connection;

	public TestesAtributosDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void getAll(TesteAtributos testeAtributos) throws Exception {

		StringBuilder query = new StringBuilder();
		
		query.append("select  ");
		query.append("	t.codigo_regra, ");
		query.append("	concat(m.modulo, ' - ', f.feature, ', ', tipo.tipo) as modulo, ");
		query.append("	r.regra_negocio, ");
		query.append("	t.observacao, ");
		query.append("	t.conclusao, ");
		query.append("	t.situacao, ");
		query.append("	ifnull((select count(codigo_bug) from tb_bugs where ifnull(tipo, 3) not in (0, 1) and codigo_teste=t.codigo_teste and codigo_regra=t.codigo_regra),0) as qtde_bugs, ");
		query.append("	t.inativo ");
		query.append("from  ");
		query.append("	tb_teste te ");
		query.append("	inner join tb_teste_atributo t on t.codigo_teste=te.codigo_teste and t.codigo_modulo=te.codigo_modulo and t.codigo_feature=te.codigo_feature and t.codigo_tipo=te.codigo_tipo ");
		query.append("	inner join tb_regra_negocio r on t.codigo_modulo=r.codigo_modulo and t.codigo_feature=r.codigo_feature and t.codigo_tipo=r.codigo_tipo and t.codigo_regra=r.codigo_regra ");
		query.append("	inner join tb_modulo m on t.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on t.codigo_modulo=f.codigo_modulo and t.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo tipo on t.codigo_modulo=tipo.codigo_modulo and t.codigo_feature=tipo.codigo_feature and t.codigo_tipo=tipo.codigo_tipo ");
		query.append("where ");
		query.append("	t.codigo_teste = ? ");

		PreparedStatement statement = null;

		try 
		{
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, testeAtributos.getCodigoTeste());
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				TesteRegra regra = new TesteRegra();
				
				regra.setCodigoRegra(rs.getInt("codigo_regra"));
				regra.setDescricaoModulo(rs.getString("modulo"));
				regra.setDescricaoRegraNegocio(rs.getString("regra_negocio"));
				regra.setObservacao(rs.getString("observacao"));
				regra.setConclusao(rs.getString("conclusao"));
				regra.setSituacao(rs.getInt("situacao"));
				regra.setInativo(rs.getInt("inativo"));
				regra.setQtdeBugs(rs.getInt("qtde_bugs"));
				testeAtributos.addRegra(regra);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		query = new StringBuilder();
		
		query.append("select  ");
		query.append("	t.codigo_caso_teste, ");
		query.append("	concat(m.modulo, ' - ', f.feature, ', ', tipo.tipo) as modulo, ");
		query.append("	c.caso_teste, ");
		query.append("	t.observacao, ");
		query.append("	t.conclusao, ");
		query.append("	t.situacao, ");
		query.append("	ifnull((select count(codigo_bug) from tb_bugs where ifnull(tipo, 3) not in (0, 1) and codigo_teste=t.codigo_teste and codigo_caso_teste=t.codigo_caso_teste),0) as qtde_bugs, ");
		query.append("	t.inativo ");
		query.append("from ");
		query.append("	tb_teste te ");
		query.append("	inner join tb_teste_atributo t on t.codigo_teste=te.codigo_teste and t.codigo_modulo=te.codigo_modulo and t.codigo_feature=te.codigo_feature and t.codigo_tipo=te.codigo_tipo ");
		query.append("	inner join tb_caso_teste c on t.codigo_modulo=c.codigo_modulo and t.codigo_feature=c.codigo_feature and t.codigo_tipo=c.codigo_tipo and t.codigo_caso_teste=c.codigo_caso_teste ");
		query.append("	inner join tb_modulo m on t.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on t.codigo_modulo=f.codigo_modulo and t.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo tipo on t.codigo_modulo=tipo.codigo_modulo and t.codigo_feature=tipo.codigo_feature and t.codigo_tipo=tipo.codigo_tipo ");
		query.append("where ");
		query.append("	t.codigo_teste = ? ");

		try 
		{
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, testeAtributos.getCodigoTeste());
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				TesteCasoTeste caso = new TesteCasoTeste();
				
				caso.setCodigoCasoTeste(rs.getInt("codigo_caso_teste"));
				caso.setDescricaoModulo(rs.getString("modulo"));
				caso.setDescricaoCasoTeste(rs.getString("caso_teste"));
				caso.setObservacao(rs.getString("observacao"));
				caso.setConclusao(rs.getString("conclusao"));
				caso.setSituacao(rs.getInt("situacao"));
				caso.setInativo(rs.getInt("inativo"));
				caso.setQtdeBugs(rs.getInt("qtde_bugs"));
				testeAtributos.addCaso(caso);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		
		query.append("select ");
		query.append("	t.codigo_comportamento, ");
		query.append("	concat(m.modulo, ' - ', f.feature, ', ', tipo.tipo) as modulo, ");
		query.append("	p.comportamento, ");
		query.append("	t.observacao, ");
		query.append("	t.conclusao, ");
		query.append("	t.situacao, ");
		query.append("	ifnull((select count(codigo_bug) from tb_bugs where ifnull(tipo, 3) not in (0, 1) and codigo_teste=t.codigo_teste and codigo_comportamento=t.codigo_comportamento), 0) as qtde_bugs, ");
		query.append("	t.inativo ");
		query.append("from ");
		query.append("	tb_teste te ");
		query.append("	inner join tb_teste_atributo t on t.codigo_teste=te.codigo_teste and t.codigo_modulo=te.codigo_modulo and t.codigo_feature=te.codigo_feature and t.codigo_tipo=te.codigo_tipo ");
		query.append("	inner join tb_comportamento p on t.codigo_modulo=p.codigo_modulo and t.codigo_feature=p.codigo_feature and t.codigo_tipo=p.codigo_tipo and t.codigo_comportamento=p.codigo_comportamento ");
		query.append("	inner join tb_modulo m on t.codigo_modulo=m.codigo_modulo ");
		query.append("	inner join tb_feature f on t.codigo_modulo=f.codigo_modulo and t.codigo_feature=f.codigo_feature ");
		query.append("	inner join tb_tipo tipo on t.codigo_modulo=tipo.codigo_modulo and t.codigo_feature=tipo.codigo_feature and t.codigo_tipo=tipo.codigo_tipo ");
		query.append("where ");
		query.append("	t.codigo_teste = ? ");

		try 
		{
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, testeAtributos.getCodigoTeste());
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				TesteComportamento comportamento = new TesteComportamento();
				
				comportamento.setCodigoComportamento(rs.getInt("codigo_comportamento"));
				comportamento.setDescricaoModulo(rs.getString("modulo"));
				comportamento.setDescricaoComportamento(rs.getString("comportamento"));
				comportamento.setObservacao(rs.getString("observacao"));
				comportamento.setConclusao(rs.getString("conclusao"));
				comportamento.setSituacao(rs.getInt("situacao"));
				comportamento.setInativo(rs.getInt("inativo"));
				comportamento.setQtdeBugs(rs.getInt("qtde_bugs"));
				testeAtributos.addComportamento(comportamento);
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
	}

	@Override
	public TesteCasoTeste getTesteCasoTesteById(Integer codigoTeste, Integer codigoCasoTeste) throws Exception {
		
		TesteCasoTeste testeCasoTeste = new TesteCasoTeste();
		
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	codigo_caso_teste, ");
		query.append("	observacao, ");
		query.append("	conclusao, ");
		query.append("	situacao, ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_teste_atributo ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_caso_teste = ? ");
		
		PreparedStatement statement = null;

		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, codigoTeste);
			statement.setInt(2, codigoCasoTeste);
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {
				
				testeCasoTeste.setCodigoCasoTeste(rs.getInt("codigo_caso_teste"));
				testeCasoTeste.setObservacao(rs.getString("observacao"));
				testeCasoTeste.setConclusao(rs.getString("conclusao"));
				testeCasoTeste.setSituacao(rs.getInt("situacao"));
				testeCasoTeste.setInativo(rs.getInt("inativo"));
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
		return testeCasoTeste;
	}

	@Override
	public TesteRegra getTesteRegraById(Integer codigoTeste, Integer codigoRegra) throws Exception {

		TesteRegra testeRegraNegocio = new TesteRegra();
		
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	codigo_regra, ");
		query.append("	observacao, ");
		query.append("	conclusao, ");
		query.append("	situacao, ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_teste_atributo ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_regra = ? ");
		
		PreparedStatement statement = null;

		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, codigoTeste);
			statement.setInt(2, codigoRegra);
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {

				testeRegraNegocio.setCodigoRegra(rs.getInt("codigo_regra"));
				testeRegraNegocio.setObservacao(rs.getString("observacao"));
				testeRegraNegocio.setConclusao(rs.getString("conclusao"));
				testeRegraNegocio.setSituacao(rs.getInt("situacao"));
				testeRegraNegocio.setInativo(rs.getInt("inativo"));
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
		return testeRegraNegocio;
	}

	@Override
	public TesteComportamento getTesteComportamentoById(Integer codigoTeste, Integer codigoComportamento)
			throws Exception {

		TesteComportamento testeComportamento = new TesteComportamento();
		
		StringBuilder query = new StringBuilder();
		
		query.append("select ");
		query.append("	codigo_comportamento, ");
		query.append("	observacao, ");
		query.append("	conclusao, ");
		query.append("	situacao, ");
		query.append("	inativo ");
		query.append("from ");
		query.append("	tb_teste_atributo ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_comportamento = ? ");
		
		PreparedStatement statement = null;

		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, codigoTeste);
			statement.setInt(2, codigoComportamento);
			statement.executeQuery();
			
			ResultSet rs = statement.getResultSet();
			
			while (rs.next()) {

				testeComportamento.setCodigoComportamento(rs.getInt("codigo_comportamento"));
				testeComportamento.setObservacao(rs.getString("observacao"));
				testeComportamento.setConclusao(rs.getString("conclusao"));
				testeComportamento.setSituacao(rs.getInt("situacao"));
				testeComportamento.setInativo(rs.getInt("inativo"));
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
		return testeComportamento;
	}

	@Override
	public boolean adicionaTesteCaso(Integer codigoTeste, TesteCasoTeste testeCasoTeste) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_teste_atributo ");
		query.append("set ");
		query.append("	observacao = ?, ");
		query.append("	conclusao = ?, ");
		query.append("	situacao = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_caso_teste = ? ");
		
		PreparedStatement statement = null;
		
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, testeCasoTeste.getObservacao());
			statement.setString(2, testeCasoTeste.getConclusao());
			statement.setInt(3, testeCasoTeste.getSituacao());
			statement.setInt(4, testeCasoTeste.getInativo());
			statement.setInt(5, codigoTeste);
			statement.setInt(6, testeCasoTeste.getCodigoCasoTeste());
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
	public boolean adicionaTesteRegra(Integer codigoTeste, TesteRegra testeRegra) throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_teste_atributo ");
		query.append("set ");
		query.append("	observacao = ?, ");
		query.append("	conclusao = ?, ");
		query.append("	situacao = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_regra = ? ");
		
		PreparedStatement statement = null;
		
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, testeRegra.getObservacao());
			statement.setString(2, testeRegra.getConclusao());
			statement.setInt(3, testeRegra.getSituacao());
			statement.setInt(4, testeRegra.getInativo());
			statement.setInt(5, codigoTeste);
			statement.setInt(6, testeRegra.getCodigoRegra());
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
	public boolean adicionaComportamento(Integer codigoTeste, TesteComportamento testeComportamento) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_teste_atributo ");
		query.append("set ");
		query.append("	observacao = ?, ");
		query.append("	conclusao = ?, ");
		query.append("	situacao = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_comportamento = ? ");
		
		PreparedStatement statement = null;
		
		try 
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, testeComportamento.getObservacao());
			statement.setString(2, testeComportamento.getConclusao());
			statement.setInt(3, testeComportamento.getSituacao());
			statement.setInt(4, testeComportamento.getInativo());
			statement.setInt(5, codigoTeste);
			statement.setInt(6, testeComportamento.getCodigoComportamento());
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
	public boolean remove(Integer codigoTeste, TesteCasoTeste testeCasoTeste) throws Exception {
		
		boolean isUpdated = false;
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_teste_atributo ");
		query.append("set ");
		query.append("	situacao = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_caso_teste = ? ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, testeCasoTeste.getSituacao());
			statement.setInt(2, testeCasoTeste.getInativo());
			statement.setInt(3, codigoTeste);
			statement.setInt(4, testeCasoTeste.getCodigoCasoTeste());
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
	public boolean remove(Integer codigoTeste, TesteRegra testeRegra) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_teste_atributo ");
		query.append("set ");
		query.append("	situacao = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_regra = ? ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, testeRegra.getSituacao());
			statement.setInt(2, testeRegra.getInativo());
			statement.setInt(3, codigoTeste);
			statement.setInt(4, testeRegra.getCodigoRegra());
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
	public boolean remove(Integer codigoTeste, TesteComportamento testeComportamento) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_teste_atributo ");
		query.append("set ");
		query.append("	situacao = ?, ");
		query.append("	inativo = ? ");
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_comportamento = ? ");
		
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, testeComportamento.getSituacao());
			statement.setInt(2, testeComportamento.getInativo());
			statement.setInt(3, codigoTeste);
			statement.setInt(4, testeComportamento.getCodigoComportamento());
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
}