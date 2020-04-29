/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;
import br.com.homologador.model.Feature;
import br.com.homologador.model.Modulo;
import br.com.homologador.model.Teste;
import br.com.homologador.model.Tipo;
import br.com.homologador.model.vo.TesteItens;
import br.com.homologador.services.FeatureServices;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.TesteServices;
import br.com.homologador.services.TipoServices;
import br.com.homologador.services.impl.FeatureServicesImpl;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.services.impl.TesteServicesImpl;
import br.com.homologador.services.impl.TipoServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class TesteController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		Teste teste = new Teste();

		String codigoTesteParamter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TESTE);
		if(StringUtils.isLong(codigoTesteParamter)) {
			teste.setCodigoTeste(Integer.valueOf(codigoTesteParamter));
		}		
		String codigoModuloParamter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		if(StringUtils.isLong(codigoModuloParamter)) {
			teste.setCodigoModulo(Integer.valueOf(codigoModuloParamter));
		}
		String codigoFeatureParamter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
		if(StringUtils.isLong(codigoFeatureParamter)) {
			teste.setCodigoFeature(Integer.valueOf(codigoFeatureParamter));
		}
		String codigoTipoParamter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_TIPO);
		if(StringUtils.isLong(codigoTipoParamter)) {
			teste.setCodigoTipo(Integer.valueOf(codigoTipoParamter));
		}

		String inativoParamter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
		if(StringUtils.isLong(inativoParamter)) {
			teste.setInativo(Integer.valueOf(inativoParamter));
		}

		TesteServices testeServices = new TesteServicesImpl(connection);
		try
		{
			testeServices.getAll(teste);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		List<TesteItens> testes = teste.getItens();
		
		testes.sort(Comparator.comparing(t -> t.getDescricaoModulo()));
		
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_TESTES, testes);
		
		return "forward:teste/lista-teste.jsp";
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;		
		boolean isSaved = false;
		
		String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		String codigoFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
		String codigoTipoParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_TIPO);
		String codigoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TESTE);
		String situacaoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_SITUACAO_REGRA);
		String situacaoCasoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_SITUACAO_CASO_TESTE);
		String situacaoComportamentoParameter = request.getParameter(ConstantDataManager.PARAMETER_SITUACAO_COMPORTAMENTO);
		String observacaoParameter = request.getParameter(ConstantDataManager.PARAMETER_OBSERVACAO);
		String conclusaoParameter = request.getParameter(ConstantDataManager.PARAMETER_CONCLUSAO);
		String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
		
		try 
		{
			Teste teste = new Teste();
			
			if(StringUtils.isLong(codigoTesteParameter)) {
				teste.setCodigoTeste(Integer.valueOf(codigoTesteParameter));
			}
			if(StringUtils.isLong(situacaoRegraParameter))
			{
				teste.setSituacaoRegra(Integer.valueOf(situacaoRegraParameter));
			}
			if(StringUtils.isLong(situacaoCasoTesteParameter)) { 
				teste.setSituacaoCasoTeste(Integer.valueOf(situacaoCasoTesteParameter));				
			}
			if(StringUtils.isLong(situacaoComportamentoParameter)) { 
				teste.setSituacaoComportamento(Integer.valueOf(situacaoComportamentoParameter));
			}
			if(!StringUtils.isNull(observacaoParameter)) {				
				teste.setObservacao(observacaoParameter);
			}
			if(!StringUtils.isNull(conclusaoParameter)) {				
				teste.setConclusao(conclusaoParameter);
			}
			if(!StringUtils.isNull(inativoParameter)) { 
				teste.setInativo(Integer.valueOf(inativoParameter));
			} else {
				teste.setInativo(1);
			}
			
			if(!StringUtils.isLong(codigoModuloParameter)) 
			{
				message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
				throw new RuntimeException();
			}
	
			teste.setCodigoModulo(Integer.valueOf(codigoModuloParameter));			
	
			if(codigoFeatureParameter!=null) 
			{
				if(StringUtils.isLong(codigoModuloParameter)) 
				{
					teste.setCodigoFeature(Integer.valueOf(codigoFeatureParameter));
				}
			}
			if(codigoTipoParameter != null) 
			{
				if(StringUtils.isLong(codigoModuloParameter)) 
				{
					teste.setCodigoTipo(Integer.valueOf(codigoTipoParameter));
				}
			}

			TesteServices testeServices = new TesteServicesImpl(connection);
			Integer codigoTeste = testeServices.adicionaTeste(teste);

			if(codigoTeste > 0 && teste.getCodigoTeste() == 0)
			{
				isSaved = testeServices.adiciona(teste, codigoTeste);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		if(isSaved) {
			message = ConstantDataManager.MESSAGE_DADOS_ALTERADOS_SUCESSO;
		} else {
			message = ConstantDataManager.MESSAGE_PROBLEMAS_SALVAR_ALTERAR;
		}
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		return "redirect:entrada?controller=Teste&acao=Listar";
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		final String codigoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = Integer.valueOf(codigoParameter);
				
		TesteServices testeServices = new TesteServicesImpl(connection);
		Teste teste = new Teste();

		List<Tipo> tipos = null;
		List<Feature> features = null;
		List<Modulo> modulos = null;
		try
		{
			teste = testeServices.getTesteById(codigo);

			TipoServices tipoServices = new TipoServicesImpl(connection);
			tipos = tipoServices.getComboTipos();
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_TIPOS, tipos);

			FeatureServices featureServices = new FeatureServicesImpl(connection);
			features = featureServices.getComboFeatures();
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_FEATURES, features);

			ModuloServices moduloServices = new ModuloServicesImpl(connection);
			modulos = moduloServices.getComboModulos();
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_MODULOS, modulos);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if(connection!=null) {
				try 
				{
					connection.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
		request.setAttribute(ConstantDataManager.CADASTRO_TESTE_OBJETO_TESTE, teste);
		return "forward:teste/form-teste-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		boolean isRemoved = false;
		
		String codigoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = Integer.valueOf(codigoParameter);
		
		TesteServices testeServices = new TesteServicesImpl(connection);
		try 
		{
			isRemoved = testeServices.remove(codigo);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if(isRemoved) {
			System.out.println("Teste inativado com sucesso!");
		}
		
		return "redirect:entrada?controller=Teste&acao=Listar";
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
		
		return "forward:teste/form-teste-novo.jsp";
	}
}
