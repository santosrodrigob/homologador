/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;
import br.com.homologador.model.DadosCriacao;
import br.com.homologador.model.Modulo;
import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.TesteCasoTeste;
import br.com.homologador.model.vo.TesteComportamento;
import br.com.homologador.model.vo.TesteRegra;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.TesteAtributosServices;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.services.impl.TestesAtributosServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class TesteAtributosController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		final String relatorioParameter = request.getParameter(ConstantDataManager.PARAMETER_RELATORIO);
		final String codigoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TESTE);
		
		String message = ConstantDataManager.BLANK;
		
		TesteAtributos testesAtributos = new TesteAtributos();

		if(StringUtils.isLong(codigoTesteParameter)) {
			testesAtributos.setCodigoTeste(Integer.valueOf(codigoTesteParameter));		
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}

		TesteAtributosServices testeAtributosServices = new TestesAtributosServicesImpl(connection);
		try 
		{
			testeAtributosServices.getAll(testesAtributos);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if(connection!=null) 
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					throw new RuntimeException();
				}
			}
		}
		
		List<TesteRegra> listaRegras =  testesAtributos.getRegras();
		List<TesteCasoTeste> listaCasos =  testesAtributos.getCasos();
		List<TesteComportamento> listaComportamentos =  testesAtributos.getComportamentos();
	
		request.setAttribute(ConstantDataManager.PARAMETER_CODIGO_TESTE, testesAtributos.getCodigoTeste());
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_CASOS_TESTE, listaCasos);
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_REGRA_NEGOCIO, listaRegras);
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_COMPORTAMENTOS, listaComportamentos);
		
		if(StringUtils.isLong(relatorioParameter))
		{
			boolean isGenerated = false;

			isGenerated = geraArquivos(listaRegras, listaCasos, listaComportamentos, testesAtributos.getCodigoTeste());
			if(isGenerated) {
				message = ConstantDataManager.MESSAGE_PROCESSO_FINALIZADO;
			} else {
				message = ConstantDataManager.MESSAGE_PROCESSO_FINALIZADO_ERRO;
			}
		}
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		return "forward:teste-atributos/lista-testes-gerenciar.jsp";
	}

	private boolean geraArquivos(List<TesteRegra> listaRegras, List<TesteCasoTeste> listaCasos,
			List<TesteComportamento> listaComportamentos, int codigoTeste) {
		
		boolean isGenerated = false;
					
		String modulo = null;
		List<String> modulos = new ArrayList<String>();
		
		for (TesteRegra regras : listaRegras)
		{
			modulo = regras.getDescricaoModulo();

			if(!modulos.contains(modulo))
			{
				modulos.add(modulo);
			}
		}

		for (String model : modulos) 
		{
			DadosCriacao dadosCriacao = new DadosCriacao();
			dadosCriacao.setDataCriacao(LocalDate.now());
			String cabecalho = String.format("%-15s %1s", dadosCriacao.getDataCriacaoFormatada(), model);

			String titulo1 = "CODIGO";
			String titulo2 = "DESCRI��O";
			String titulos = String.format("%-15s %s", titulo1, titulo2);
			String foot1 = "RESULTADO: ";
			String footer = String.format("%-10s", foot1);
			String divisor = "/----------------------------/";
			String titulo3 = "  ## REGRAS DE NEG�CIO";
			String titulo4 = "  ## CASOS DE TESTE";
			String titulo5 = "  ## COMPORTAMENTOS";

			PrintStream ps = null;
			try 
			{
				ps = new PrintStream("C:\\DTM\\RELATORIOS\\TESTES\\" + codigoTeste + " - " + model + ".txt");
				ps.println(cabecalho);
				ps.println();
				ps.println(divisor);
				ps.println(titulo4);
				ps.println(divisor);
				ps.println(titulos);
				ps.println();
				if(listaCasos.size()>0) {
					for (TesteCasoTeste casos : listaCasos)
					{
						if(casos.getDescricaoModulo().equalsIgnoreCase(model))
						{
							String valores = String.format("%-1s - %s", casos.getCodigoCasoTeste(),
									casos.getDescricaoCasoTeste());
							ps.println(valores);
							ps.println(footer);
							ps.println();
						}
					}
				}
				ps.println(divisor);
				ps.println(titulo3);
				ps.println(divisor);
				ps.println(titulos);
				ps.println();
				if(listaRegras.size()>0) {
					for (TesteRegra regras : listaRegras)
					{
						if(regras.getDescricaoModulo().equalsIgnoreCase(model))
						{
							String valores = String.format("%-1s - %s", regras.getCodigoRegra(),
									regras.getDescricaoRegraNegocio());
							ps.println(valores);
							ps.println(footer);
							ps.println();
						}
					}					
				}
				ps.println(divisor);
				ps.println(titulo5);
				ps.println(divisor);
				ps.println(titulos);
				ps.println();
				if(listaComportamentos.size()>0) {
					for (TesteComportamento comportamento : listaComportamentos)
					{
						if(comportamento.getDescricaoModulo().equalsIgnoreCase(model))
						{
							String valores = String.format("%-1s - %s", comportamento.getCodigoComportamento(),
									comportamento.getDescricaoComportamento());
							ps.println(valores);
							ps.println(footer);
							ps.println();
						}
					}
				}
				ps.close();
				isGenerated = true;
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
				isGenerated = false;
			}
		}
		return isGenerated;
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		String message = ConstantDataManager.BLANK;
		boolean isUpdated = false;
		
		final String codigoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TESTE);
		final String codigoCasoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_CASO_TESTE);
		final String codigoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_REGRA_NEGOCIO);
		final String codigoComportamentoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_COMPORTAMENTO);
		final String observacaoParameter = request.getParameter(ConstantDataManager.PARAMETER_OBSERVACAO);
		final String conclusaoParameter = request.getParameter(ConstantDataManager.PARAMETER_CONCLUSAO);
		final String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
		final String situacaoParameter = request.getParameter(ConstantDataManager.PARAMETER_SITUACAO);

		Integer codigoTeste = 0;

		if(StringUtils.isLong(codigoTesteParameter)) {
			codigoTeste = Integer.valueOf(codigoTesteParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}
		
		TesteCasoTeste testeCasoTeste = new TesteCasoTeste();		
		TesteRegra testeRegra = new TesteRegra();
		TesteComportamento testeComportamento = new TesteComportamento();
		
		if(!StringUtils.isNull(observacaoParameter)) {
			testeCasoTeste.setObservacao(observacaoParameter);
			testeRegra.setObservacao(observacaoParameter);
			testeComportamento.setObservacao(observacaoParameter);
		}

		if(!StringUtils.isNull(conclusaoParameter)) {
			testeCasoTeste.setConclusao(conclusaoParameter);
			testeRegra.setConclusao(conclusaoParameter);
			testeComportamento.setConclusao(conclusaoParameter);
		}

		if(StringUtils.isLong(situacaoParameter)) {
			testeCasoTeste.setSituacao(Integer.valueOf(situacaoParameter));
			testeRegra.setSituacao(Integer.valueOf(situacaoParameter));
			testeComportamento.setSituacao(Integer.valueOf(situacaoParameter));
		}

		if(StringUtils.isLong(inativoParameter)) {
			testeCasoTeste.setInativo(Integer.valueOf(inativoParameter));
			testeRegra.setInativo(Integer.valueOf(inativoParameter));
			testeComportamento.setInativo(Integer.valueOf(inativoParameter));
		}
		
		TesteAtributosServices testeAtributosServices = new TestesAtributosServicesImpl(connection);
		try 
		{
			if(StringUtils.isLong(codigoCasoTesteParameter)) {		
				testeCasoTeste.setCodigoCasoTeste(Integer.valueOf(codigoCasoTesteParameter));
				isUpdated = testeAtributosServices.adicionaTesteCaso(codigoTeste, testeCasoTeste);

			} else if(StringUtils.isLong(codigoRegraParameter)) {
				testeRegra.setCodigoRegra(Integer.valueOf(codigoRegraParameter));
				isUpdated = testeAtributosServices.adicionaTesteRegra(codigoTeste, testeRegra);

			} else if(StringUtils.isLong(codigoComportamentoParameter)) {
				testeComportamento.setCodigoComportamento(Integer.valueOf(codigoComportamentoParameter));
				isUpdated = testeAtributosServices.adicionaComportamento(codigoTeste, testeComportamento);
			} 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		if(isUpdated) {
			message = ConstantDataManager.MESSAGE_DADOS_ALTERADOS_SUCESSO;
		} else {
			message = ConstantDataManager.MESSAGE_PROBLEMAS_SALVAR_ALTERAR;
		}

		request.setAttribute(ConstantDataManager.MESSAGE, message);
		return "redirect:entrada?controller=TesteAtributos&acao=Listar&codigoTeste="+ codigoTeste + " ";
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;
		
		final String codigoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TESTE);
		final String codigoCasoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_CASO_TESTE);
		final String codigoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_REGRA_NEGOCIO);
		final String codigoComportamentoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_COMPORTAMENTO);

		Integer codigoTeste = 0;
		
		if(StringUtils.isLong(codigoTesteParameter)) {
			codigoTeste = Integer.valueOf(codigoTesteParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}
		
		TesteCasoTeste testeCasoTeste = null;
		TesteRegra testeRegra = null;
		TesteComportamento testeComportamento = null;

		TesteAtributosServices testeAtributosServices = new TestesAtributosServicesImpl(connection);
		try 
		{
			if(StringUtils.isLong(codigoCasoTesteParameter)) {
				Integer codigoCasoTeste = Integer.valueOf(codigoCasoTesteParameter);
				testeCasoTeste = testeAtributosServices.getTesteCasoTesteById(codigoTeste, codigoCasoTeste);
				request.setAttribute(ConstantDataManager.CADASTRO_TESTE_ATRIBUTOS_OBJETO_CASO_TESTE, testeCasoTeste);

			} else if(StringUtils.isLong(codigoRegraParameter)) {
				Integer codigoRegra = Integer.valueOf(codigoRegraParameter);
				testeRegra = testeAtributosServices.getTesteRegraById(codigoTeste, codigoRegra);
				request.setAttribute(ConstantDataManager.CADASTRO_TESTE_ATRIBUTOS_OBJETO_REGRA_NEGOCIO, testeRegra);

			} else if(StringUtils.isLong(codigoComportamentoParameter)) {
				Integer codigoComportamento = Integer.valueOf(codigoComportamentoParameter);
				testeComportamento = testeAtributosServices.getTesteComportamentoById(codigoTeste, codigoComportamento);
				request.setAttribute(ConstantDataManager.CADASTRO_TESTE_ATRIBUTOS_OBJETO_COMPORTAMENTO, testeComportamento);
			} 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		request.setAttribute(ConstantDataManager.PARAMETER_CODIGO_TESTE, codigoTeste);
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		return "forward:teste-atributos/form-teste-atributos-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;
		boolean isUpdated = false;
		
		final String codigoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TESTE);
		final String codigoCasoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_CASO_TESTE);
		final String codigoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_REGRA_NEGOCIO);
		final String codigoComportamentoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_COMPORTAMENTO);
		final String situacaoParameter = request.getParameter(ConstantDataManager.PARAMETER_SITUACAO);
		final String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);

		Integer codigoTeste = 0;

		if(StringUtils.isLong(codigoTesteParameter)) {
			codigoTeste = Integer.valueOf(codigoTesteParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}
		
		TesteCasoTeste testeCasoTeste = new TesteCasoTeste();		
		TesteRegra testeRegra = new TesteRegra();
		TesteComportamento testeComportamento = new TesteComportamento();

		if(StringUtils.isLong(inativoParameter)) {
			testeCasoTeste.setInativo(Integer.valueOf(inativoParameter));
			testeRegra.setInativo(Integer.valueOf(inativoParameter));
			testeComportamento.setInativo(Integer.valueOf(inativoParameter));
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}

		if(StringUtils.isLong(situacaoParameter)) {
			testeCasoTeste.setSituacao(Integer.valueOf(situacaoParameter));
			testeRegra.setSituacao(Integer.valueOf(situacaoParameter));
			testeComportamento.setSituacao(Integer.valueOf(situacaoParameter));
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}

		if(StringUtils.isLong(codigoCasoTesteParameter) || StringUtils.isLong(codigoRegraParameter) || StringUtils.isLong(codigoComportamentoParameter)) 
		{
			if(StringUtils.isLong(codigoCasoTesteParameter)) {
				testeCasoTeste.setCodigoCasoTeste(Integer.valueOf(codigoCasoTesteParameter));
			} else if(StringUtils.isLong(codigoRegraParameter)) {
				testeRegra.setCodigoRegra(Integer.valueOf(codigoRegraParameter));
			} else if(StringUtils.isLong(codigoComportamentoParameter)) {			
				testeComportamento.setCodigoComportamento(Integer.valueOf(codigoComportamentoParameter));		
			}
			
		} else {			
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}

		
		TesteAtributosServices testeAtributosServices = new TestesAtributosServicesImpl(connection);
		try 
		{
			if(StringUtils.isLong(codigoCasoTesteParameter)) {		
				isUpdated = testeAtributosServices.remove(codigoTeste, testeCasoTeste);

			} else if(StringUtils.isLong(codigoRegraParameter)) {
				isUpdated = testeAtributosServices.remove(codigoTeste, testeRegra);

			} else if(StringUtils.isLong(codigoComportamentoParameter)) {
				isUpdated = testeAtributosServices.remove(codigoTeste, testeComportamento);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		if(isUpdated) {
			message = ConstantDataManager.MESSAGE_DADOS_ALTERADOS_SUCESSO;
		} else {
			message = ConstantDataManager.MESSAGE_PROBLEMAS_SALVAR_ALTERAR;
		}
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		
		return "redirect:entrada?controller=TesteAtributos&acao=Listar&codigoTeste="+ codigoTeste + " ";
	}

	@Override
	public String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		List<Modulo> modulos = null;
		try
		{
			ModuloServices moduloServices = new ModuloServicesImpl(connection);
			modulos = moduloServices.getComboModulos();
			modulos.sort(Comparator.comparing(m -> m.getDescricaoModulo()));
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_MODULOS, modulos);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return "forward:teste-atributos/filtrar-teste-atributos.jsp";
	}
}