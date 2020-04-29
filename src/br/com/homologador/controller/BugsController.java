/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;
import br.com.homologador.model.TesteAtributos;
import br.com.homologador.model.vo.BugCasoTeste;
import br.com.homologador.model.vo.BugComportamento;
import br.com.homologador.model.vo.BugRegra;
import br.com.homologador.services.BugsServices;
import br.com.homologador.services.impl.BugsServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class BugsController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		boolean isUpdated = false;
		String message = ConstantDataManager.BLANK;

		final String codigoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TESTE);
		final String codigoCasoTesteParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_CASO_TESTE);
		final String codigoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_REGRA_NEGOCIO);
		final String codigoComportamentoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_COMPORTAMENTO);
		String codigoBugParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_BUG);
		final String descricaoBugParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_BUG);
		final String tipoBugParameter = request.getParameter(ConstantDataManager.PARAMETER_TIPO_BUG);
		final String situacaoParameter = request.getParameter(ConstantDataManager.PARAMETER_SITUACAO);
		
		Integer codigoTeste = 0;
		Integer codigoCasoTeste = 0;
		Integer codigoRegra = 0;
		Integer codigoComportamento = 0;

		BugCasoTeste bugCasoTeste = new BugCasoTeste();
		BugRegra bugRegra = new BugRegra();
		BugComportamento bugComportamento = new BugComportamento();

		if(StringUtils.isLong(situacaoParameter)) {
			bugCasoTeste.setTipo(Integer.valueOf(situacaoParameter));
			bugRegra.setTipo(Integer.valueOf(situacaoParameter));
			bugComportamento.setTipo(Integer.valueOf(situacaoParameter));
		}

		if(StringUtils.isLong(tipoBugParameter)) {
			bugCasoTeste.setTipo(Integer.valueOf(tipoBugParameter));
			bugRegra.setTipo(Integer.valueOf(tipoBugParameter));
			bugComportamento.setTipo(Integer.valueOf(tipoBugParameter));
		}

		if(!StringUtils.isNull(descricaoBugParameter)) {
			bugCasoTeste.setDescricaoBug(descricaoBugParameter);
			bugRegra.setDescricaoBug(descricaoBugParameter);
			bugComportamento.setDescricaoBug(descricaoBugParameter);
		}
	
		if(StringUtils.isLong(codigoBugParameter)) {
			bugCasoTeste.setCodigoBug(Integer.valueOf(codigoBugParameter));
			bugRegra.setCodigoBug(Integer.valueOf(codigoBugParameter));
			bugComportamento.setCodigoBug(Integer.valueOf(codigoBugParameter));
		} else {
			codigoBugParameter = "0";
		}
			
		if(StringUtils.isLong(codigoTesteParameter)) {
			codigoTeste = Integer.valueOf(codigoTesteParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}
		
		BugsServices bugsServices = new BugsServicesImpl(connection);		
		try 
		{
			if(codigoBugParameter.equalsIgnoreCase("999999")) 
			{
				Integer tipo = Integer.valueOf(tipoBugParameter);
				if(StringUtils.isLong(codigoCasoTesteParameter)) {
					codigoCasoTeste = Integer.valueOf(codigoCasoTesteParameter);
					isUpdated = bugsServices.alteraBugsCasoTeste(codigoTeste, tipo, codigoCasoTeste);
				} else if(StringUtils.isLong(codigoRegraParameter)) {
					codigoRegra = Integer.valueOf(codigoRegraParameter);
					isUpdated = bugsServices.alteraBugsRegra(codigoTeste, tipo, codigoRegra);					
				} else if(StringUtils.isLong(codigoComportamentoParameter)) {
					codigoComportamento = Integer.valueOf(codigoComportamentoParameter);
					isUpdated = bugsServices.alteraBugsComportamento(codigoTeste, tipo, codigoComportamento);					
				}
			} else if(StringUtils.isLong(codigoCasoTesteParameter)) {
				codigoCasoTeste = Integer.valueOf(codigoCasoTesteParameter);
				isUpdated = bugsServices.adicionaBugCasoTeste(codigoTeste, codigoCasoTeste, bugCasoTeste);
			} else if(StringUtils.isLong(codigoRegraParameter)) {				
				codigoRegra = Integer.valueOf(codigoRegraParameter);
				isUpdated = bugsServices.adicionaBugRegra(codigoTeste, codigoRegra, bugRegra);
			} else if(StringUtils.isLong(codigoComportamentoParameter)) {
				codigoComportamento = Integer.valueOf(codigoComportamentoParameter);
				isUpdated = bugsServices.adicionaBugComportamento(codigoTeste, codigoComportamento, bugComportamento);
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
		if(codigoCasoTeste != 0 && "0".equalsIgnoreCase(codigoBugParameter)) {
			return "redirect:entrada?controller=Bugs&acao=Mostrar&codigoTeste="+ codigoTeste +"&codigoCasoTeste="+ codigoCasoTeste +" ";
		} else if(codigoRegra != 0 && "0".equalsIgnoreCase(codigoBugParameter)) {
			return "redirect:entrada?controller=Bugs&acao=Mostrar&codigoTeste="+ codigoTeste +"&codigoRegraNegocio="+ codigoRegra +" ";
		} else if(codigoComportamento != 0 && "0".equalsIgnoreCase(codigoBugParameter)) {
			return "redirect:entrada?controller=Bugs&acao=Mostrar&codigoTeste="+ codigoTeste +"&codigoComportamento="+ codigoComportamento +" ";
		} else {
			return "redirect:entrada?controller=TesteAtributos&acao=Listar&codigoTeste="+ codigoTeste +" ";
		}

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

		TesteAtributos testeAtributos = new TesteAtributos();
		
		BugsServices bugsServices = new BugsServicesImpl(connection);
		try 
		{
			if(StringUtils.isLong(codigoCasoTesteParameter)) {
				Integer codigoCasoTeste = Integer.valueOf(codigoCasoTesteParameter);
				bugsServices.getBugsCasoTeste(codigoTeste, codigoCasoTeste, testeAtributos);
				
				request.setAttribute(ConstantDataManager.PARAMETER_CODIGO_CASO_TESTE, codigoCasoTeste);
				request.setAttribute(ConstantDataManager.CADASTRO_BUG_OBJETO_CASO_TESTE, testeAtributos.getBugCasos());

			} else if(StringUtils.isLong(codigoRegraParameter)) {
				Integer codigoRegra = Integer.valueOf(codigoRegraParameter);
				bugsServices.getBugsRegra(codigoTeste, codigoRegra, testeAtributos);

				request.setAttribute(ConstantDataManager.PARAMETER_CODIGO_REGRA_NEGOCIO, codigoRegra);
				request.setAttribute(ConstantDataManager.CADASTRO_BUG_OBJETO_REGRA, testeAtributos.getBugRegras());

			} else if(StringUtils.isLong(codigoComportamentoParameter)) {
				Integer codigoComportamento = Integer.valueOf(codigoComportamentoParameter);
				bugsServices.getBugsComportamento(codigoTeste, codigoComportamento, testeAtributos);

				request.setAttribute(ConstantDataManager.PARAMETER_CODIGO_COMPORTAMENTO, codigoComportamento);
				request.setAttribute(ConstantDataManager.CADASTRO_BUG_OBJETO_COMPORTAMENTO, testeAtributos.getBugComportamentos());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		request.setAttribute(ConstantDataManager.PARAMETER_CODIGO_TESTE, codigoTeste);		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		
		return "forward:bug/form-bug-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
