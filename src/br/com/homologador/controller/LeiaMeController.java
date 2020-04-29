/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;
import br.com.homologador.model.DadosCriacao;
import br.com.homologador.model.LeiaMe;
import br.com.homologador.model.Modulo;
import br.com.homologador.model.vo.Filtro;
import br.com.homologador.services.LeiaMeServices;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.impl.LeiaMeServicesImpl;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class LeiaMeController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;
		
		final String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		final String descricaoParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_LEIA_ME);
		final String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
		final String relatorioParameter = request.getParameter(ConstantDataManager.PARAMETER_RELATORIO);
		
		LeiaMe leiaMe = new LeiaMe();
		
		if(StringUtils.isLong(codigoModuloParameter)) {
			leiaMe.setCodigoModulo(Integer.valueOf(codigoModuloParameter));
		}

		if(!StringUtils.isNull(descricaoParameter)) {
			leiaMe.setDescricaoLeiaMe(descricaoParameter);
		}

		if(StringUtils.isLong(inativoParameter)) {
			leiaMe.setInativo(Integer.valueOf(inativoParameter));
		}
		
		LeiaMeServices leiaMeServices = new LeiaMeServicesImpl(connection);
		try 
		{
			leiaMeServices.getAll(leiaMe);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		List<LeiaMe> leiames = leiaMe.getLeiames();
		
		leiames = leiames.stream()
				.sorted((l1, l2) -> l2.getVersao().compareTo(l1.getVersao()))
				.sorted((l1, l2) -> l1.getDescricaoModulo().compareTo(l2.getDescricaoModulo()))
				.collect(Collectors.toList());

		if(!StringUtils.isNull(relatorioParameter)) 
		{
			boolean isGenerated = false;

			isGenerated = geraArquivos(leiames);
			if(isGenerated) {
				message = ConstantDataManager.MESSAGE_PROCESSO_FINALIZADO;
			} else {
				message = ConstantDataManager.MESSAGE_PROCESSO_FINALIZADO_ERRO;
			}
		}
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_LEIA_ME, leiames);
		return "forward:leia-me/lista-leiame.jsp";
	}

	public boolean geraArquivos(List<LeiaMe> leiames) {
		
		boolean isGenerated = false;
		
		String modulo = null;
		List<String> modulos = new ArrayList<String>();
		
		for (LeiaMe leiaMe : leiames) 
		{
			modulo = leiaMe.getDescricaoModulo();

			if(!modulos.contains(modulo))
			{
				modulos.add(modulo);
			}
		}
		
		for (String model : modulos) 
		{
			DadosCriacao dadosCriacao = new DadosCriacao();
			dadosCriacao.setDataCriacao(LocalDate.now());
			String cabecalho = String.format("%-11s %1s", dadosCriacao.getDataCriacaoFormatada(), model);

			String titulo1 = "DATA";
			String titulo2 = "VERSÃO";
			String titulo3 = "DESCRIÇÃO";
			String titulos = String.format("%-11s %-15s %s", titulo1, titulo2, titulo3);

			PrintStream ps = null;
			try 
			{
				ps = new PrintStream("C:\\DTM\\LEIA-ME\\LEIA-ME - " + model +".txt");
				ps.println(cabecalho);
				ps.println();
				ps.println(titulos);
				ps.println();
				for (LeiaMe leia : leiames) 
				{
					if(leia.getDescricaoModulo().equalsIgnoreCase(model))
					{
						String valores = String.format("%-11s %-15s %s", leia.getDataCriacaoFormatada(), leia.getVersao(), leia.getDescricaoLeiaMe());
						ps.println(valores);
						ps.println();
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
		
		boolean isUpdated = false;
		String message = null;
		
		String codigoleiaParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_LEIA_ME);
		String descricaoParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_LEIA_ME);
		String versaoParameter = request.getParameter(ConstantDataManager.PARAMETER_VERSAO);
		String moduloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);		
		String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
		
		LeiaMe leiaMe = new LeiaMe();
		
		if(StringUtils.isLong(codigoleiaParameter)) {
			leiaMe.setCodigoLeiaMe(Integer.valueOf(codigoleiaParameter));
		}
		
		if(StringUtils.isLong(inativoParameter)) {
			leiaMe.setInativo(Integer.valueOf(inativoParameter));
		}
		
		if(!StringUtils.isLong(moduloParameter) || StringUtils.isNull(descricaoParameter) || StringUtils.isNull(versaoParameter)) {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}

		leiaMe.setDescricaoLeiaMe(descricaoParameter);
		leiaMe.setVersao(versaoParameter);
		leiaMe.setCodigoModulo(Integer.valueOf(moduloParameter));
		
		LeiaMeServices leiaMeServices = new LeiaMeServicesImpl(connection);
		try
		{
			isUpdated = leiaMeServices.adiciona(leiaMe);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		if(isUpdated) {
			message = ConstantDataManager.MESSAGE_DADOS_ALTERADOS_SUCESSO;
		} else {
			message = ConstantDataManager.MESSAGE_PROBLEMAS_SALVAR_ALTERAR;
		}
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		
		return "redirect:entrada?controller=LeiaMe&acao=Listar";
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;
		
		String codigoLeiaMeParameter =request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = null;

		if(StringUtils.isLong(codigoLeiaMeParameter)) {
			codigo = Integer.valueOf(codigoLeiaMeParameter);
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}

		LeiaMeServices leiaMeServices = new LeiaMeServicesImpl(connection);
		LeiaMe leiaMe = null;
		List<Modulo> modulos = null;
		try 
		{
			leiaMe = leiaMeServices.getLeiaMeById(codigo);

			ModuloServices moduloServices = new ModuloServicesImpl(connection);
			modulos = moduloServices.getComboModulos();
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_MODULOS, modulos);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		request.setAttribute(ConstantDataManager.OBJETO_LEIA_ME, leiaMe);
		
		return "forward:leia-me/form-leiame-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;
		boolean isRemoved = false;
		
		String codigoLeiaMeParameter =request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = null;

		if(StringUtils.isLong(codigoLeiaMeParameter)) {
			codigo = Integer.valueOf(codigoLeiaMeParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}
		
		LeiaMeServices leiaMeServices = new LeiaMeServicesImpl(connection);
		try 
		{
			isRemoved = leiaMeServices.remove(codigo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		if(isRemoved) {
			message = ConstantDataManager.MESSAGE_DADOS_ALTERADOS_SUCESSO;
		} else {
			message = ConstantDataManager.MESSAGE_PROBLEMAS_SALVAR_ALTERAR;
		}

		request.setAttribute(ConstantDataManager.MESSAGE, message);
		return "redirect:entrada?controller=LeiaMe&acao=Listar";	
	}

	@Override
	public String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		final String filtrarParameter = request.getParameter(ConstantDataManager.PARAMETER_FILTRAR);
		
		List<Modulo> modulos = null;
		Filtro filtro = null;
		try
		{
			LeiaMeServices leiaMeServices = new LeiaMeServicesImpl(connection);
			filtro = leiaMeServices.getUltimoFiltro();
			
			ModuloServices moduloServices = new ModuloServicesImpl(connection);
			modulos = moduloServices.getComboModulos();
			modulos.sort(Comparator.comparing(m -> m.getDescricaoModulo()));
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_MODULOS, modulos);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		request.setAttribute(ConstantDataManager.OBJETO_GET_ULTIMO_FILTRO, filtro);
	
		if(StringUtils.isNull(filtrarParameter)) {
			return "forward:leia-me/form-leiame-novo.jsp";
		} else {
			return "forward:leia-me/filtrar-leiame.jsp";
		}
	}
}
