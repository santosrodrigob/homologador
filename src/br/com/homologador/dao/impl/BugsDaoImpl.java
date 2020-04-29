/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.homologador.dao.BugsDao;
import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.BugCasoTeste;
import br.com.homologador.model.vo.BugComportamento;
import br.com.homologador.model.vo.BugRegra;

public class BugsDaoImpl implements BugsDao {

	private Connection connection;
	
	public BugsDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void getBugsCasoTeste(Integer codigoTeste, Integer codigoCasoTeste, TesteAtributos testeAtributos) throws Exception {
		
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_bug, ");
		query.append("	bug, ");
		query.append("	situacao, ");
		query.append("	tipo ");
		query.append("from ");
		query.append("	tb_bugs ");
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

				BugCasoTeste bugCasoTeste = new BugCasoTeste();
				
				bugCasoTeste.setCodigoBug(rs.getInt("codigo_bug"));
				bugCasoTeste.setDescricaoBug(rs.getString("bug"));
				bugCasoTeste.setSituacao(rs.getInt("situacao"));
				bugCasoTeste.setTipo(rs.getInt("tipo"));
				testeAtributos.addBugCaso(bugCasoTeste);
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
	public void getBugsRegra(Integer codigoTeste, Integer codigoRegra, TesteAtributos testeAtributos) throws Exception {
				
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_bug, ");
		query.append("	bug, ");
		query.append("	situacao, ");
		query.append("	tipo ");
		query.append("from ");
		query.append("	tb_bugs ");
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

				BugRegra bugRegra = new BugRegra();
				
				bugRegra.setCodigoBug(rs.getInt("codigo_bug"));
				bugRegra.setDescricaoBug(rs.getString("bug"));
				bugRegra.setSituacao(rs.getInt("situacao"));
				bugRegra.setTipo(rs.getInt("tipo"));
				testeAtributos.addBugRagra(bugRegra);
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
	public void getBugsComportamento(Integer codigoTeste, Integer codigoComportamento, TesteAtributos testeAtributos) throws Exception {
				
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("	codigo_bug, ");
		query.append("	bug, ");
		query.append("	situacao, ");
		query.append("	tipo ");
		query.append("from ");
		query.append("	tb_bugs ");
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
				
				BugComportamento bugComportamento = new BugComportamento();
				
				bugComportamento.setCodigoBug(rs.getInt("codigo_bug"));
				bugComportamento.setDescricaoBug(rs.getString("bug"));
				bugComportamento.setSituacao(rs.getInt("situacao"));
				bugComportamento.setTipo(rs.getInt("tipo"));
				testeAtributos.addBugComportamento(bugComportamento);
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
	public boolean adicionaBugCasoTeste(Integer codigoTeste, Integer codigoCasoTeste, BugCasoTeste bugCasoTeste) throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_bugs ");
		query.append("set ");
		query.append("	bug = ?, ");
		query.append("	tipo = ? ");
		if(bugCasoTeste.getTipo() == 2) {
			query.append(", situacao = 1 ");
		}
		query.append("where ");
		query.append("	codigo_bug = ? ");
		
		PreparedStatement statement = null;
			
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, bugCasoTeste.getDescricaoBug());
			statement.setInt(2, bugCasoTeste.getTipo());
			statement.setInt(3, bugCasoTeste.getCodigoBug());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		
		query.append("insert into tb_bugs");
		query.append("(");
		query.append("	bug, ");
		query.append("	codigo_teste, ");
		query.append("	codigo_caso_teste, ");
		query.append("	tipo");
		query.append(") select ?, ?, ?, ? ");

		try
		{
			if(!isUpdated) 
			{
				statement = this.connection.prepareStatement(query.toString());
				statement.setString(1, bugCasoTeste.getDescricaoBug());
				statement.setInt(2, codigoTeste);
				statement.setInt(3, codigoCasoTeste);
				statement.setInt(4, bugCasoTeste.getTipo());
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
	public boolean adicionaBugRegra(Integer codigoTeste, Integer codigoRegra, BugRegra bugRegra) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_bugs ");
		query.append("set ");
		query.append("	bug = ?, ");
		query.append("	tipo = ? ");
		if(bugRegra.getTipo() == 2) {
			query.append(", situacao = 1 ");
		}
		query.append("where ");
		query.append("	codigo_bug = ? ");
		
		PreparedStatement statement = null;
			
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, bugRegra.getDescricaoBug());
			statement.setInt(2, bugRegra.getTipo());
			statement.setInt(3, bugRegra.getCodigoBug());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		
		query.append("insert into tb_bugs");
		query.append("(");
		query.append("	bug, ");
		query.append("	codigo_teste, ");
		query.append("	codigo_regra, ");
		query.append("	tipo");
		query.append(") select ?, ?, ?, ? ");

		try
		{
			if(!isUpdated) 
			{
				statement = this.connection.prepareStatement(query.toString());
				statement.setString(1, bugRegra.getDescricaoBug());
				statement.setInt(2, codigoTeste);
				statement.setInt(3, codigoRegra);
				statement.setInt(4, bugRegra.getTipo());
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
	public boolean adicionaBugComportamento(Integer codigoTeste, Integer codigoComportamento, BugComportamento bugComportamento) throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_bugs ");
		query.append("set ");
		query.append("	bug = ?, ");
		query.append("	tipo = ? ");
		if(bugComportamento.getTipo() == 2) {
			query.append(", situacao = 1 ");
		}
		query.append("where ");
		query.append("	codigo_bug = ? ");
		
		PreparedStatement statement = null;
			
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setString(1, bugComportamento.getDescricaoBug());
			statement.setInt(2, bugComportamento.getTipo());
			statement.setInt(3, bugComportamento.getCodigoBug());
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}

		query = new StringBuilder();
		
		query.append("insert into tb_bugs");
		query.append("(");
		query.append("	bug, ");
		query.append("	codigo_teste, ");
		query.append("	codigo_comportamento, ");
		query.append("	tipo");
		query.append(") select ?, ?, ?, ? ");

		try
		{
			if(!isUpdated) 
			{
				statement = this.connection.prepareStatement(query.toString());
				statement.setString(1, bugComportamento.getDescricaoBug());
				statement.setInt(2, codigoTeste);
				statement.setInt(3, codigoComportamento);
				statement.setInt(4, bugComportamento.getTipo());
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
	public boolean alteraCasoRegraComportamento(Integer codigoTeste, Integer tipo)
			throws Exception {
		
		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_bugs ");
		query.append("set ");
		query.append("	tipo = ? ");
		if(tipo == 2) {
			query.append(", situacao = 1 ");
		}
		query.append("where ");
		query.append("	codigo_teste = ? ");
		
		PreparedStatement statement = null;
			
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, tipo);
			statement.setInt(2, codigoTeste);
			statement.executeUpdate();
			isUpdated = statement.getUpdateCount() > 0;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		return isUpdated;
	}

	@Override
	public boolean alteraBugsCasoTeste(Integer codigoTeste, Integer tipo, Integer codigoCasoTeste) throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_bugs ");
		query.append("set ");
		query.append("	tipo = ? ");
		if(tipo == 2) {
			query.append(", situacao = 1 ");
		}
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_caso_teste = ? ");
		
		PreparedStatement statement = null;
			
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, tipo);
			statement.setInt(2, codigoTeste);
			statement.setInt(3, codigoCasoTeste);
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
	public boolean alteraBugsRegra(Integer codigoTeste, Integer tipo, Integer codigoRegra) throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_bugs ");
		query.append("set ");
		query.append("	tipo = ? ");
		if(tipo == 2) {
			query.append(", situacao = 1 ");
		}
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_regra = ? ");
		
		PreparedStatement statement = null;
			
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, tipo);
			statement.setInt(2, codigoTeste);
			statement.setInt(3, codigoRegra);
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
	public boolean alteraBugsComportamento(Integer codigoTeste, Integer tipo, Integer codigoComportamento)
			throws Exception {

		boolean isUpdated = false;
		
		StringBuilder query = new StringBuilder();
		query.append("update ");
		query.append("	tb_bugs ");
		query.append("set ");
		query.append("	tipo = ? ");
		if(tipo == 2) {
			query.append(", situacao = 1 ");
		}
		query.append("where ");
		query.append("	codigo_teste = ? ");
		query.append("	and codigo_comportamento = ? ");
		
		PreparedStatement statement = null;
			
		try
		{
			statement = this.connection.prepareStatement(query.toString());
			statement.setInt(1, tipo);
			statement.setInt(2, codigoTeste);
			statement.setInt(3, codigoComportamento);
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
