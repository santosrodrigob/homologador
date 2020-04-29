/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;
import br.com.homologador.model.CasoTeste;
import br.com.homologador.model.Feature;
import br.com.homologador.model.Modulo;
import br.com.homologador.model.Tipo;
import br.com.homologador.model.vo.Filtro;
import br.com.homologador.services.CasoTesteServices;
import br.com.homologador.services.FeatureServices;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.TipoServices;
import br.com.homologador.services.impl.CasoTesteServicesImpl;
import br.com.homologador.services.impl.FeatureServicesImpl;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.services.impl.TipoServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class CasoTesteController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		final String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		final String codigoFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
		final String codigoTipoParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_TIPO);
		final String codigoCasoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_CASO_TESTE);
		final String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
			
		CasoTeste casoTeste = new CasoTeste();
		
		if(StringUtils.isLong(codigoModuloParameter)) {
			casoTeste.setCodigoModulo(Integer.valueOf(codigoModuloParameter));
		}

		if(StringUtils.isLong(codigoFeatureParameter)) {
			casoTeste.setCodigoFeature(Integer.valueOf(codigoFeatureParameter));
		}

		if(StringUtils.isLong(codigoTipoParameter)) {
			casoTeste.setCodigoTipo(Integer.valueOf(codigoTipoParameter));
		}

		if(StringUtils.isLong(codigoCasoParameter)) {
			casoTeste.setCodigoCasoTeste(Integer.valueOf(codigoCasoParameter));
		}

		if(StringUtils.isLong(inativoParameter)) {
			casoTeste.setInativo(Integer.valueOf(inativoParameter));
		}

		CasoTesteServices casoTesteServices = new CasoTesteServicesImpl(connection);
		try 
		{
			casoTesteServices.getAll(casoTeste);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		List<CasoTeste> listaCasos = casoTeste.getListaCasos();
		
		listaCasos.sort(Comparator.comparing(c -> c.getDescricaoModulo()));
		
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_CASOS_TESTE, listaCasos);
		
		return "forward:caso-teste/lista-caso-teste.jsp";
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		String message = ConstantDataManager.BLANK;
		boolean isUpdated = false;
		
		String codigoCasoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_CASO_TESTE);
		String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		String codigoFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
		String codigoTipoParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_TIPO);
		String descricaoCasoParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_CASO_TESTE);
		String testeFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_TESTE_FEATURE);
		String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
		
		CasoTeste casoTeste = new CasoTeste();
		
		if(StringUtils.isLong(codigoModuloParameter) && StringUtils.isLong(codigoFeatureParameter) && StringUtils.isLong(codigoTipoParameter)
				&& !StringUtils.isNull(descricaoCasoParameter) && StringUtils.isLong(testeFeatureParameter)) {
			casoTeste.setCodigoModulo(Integer.valueOf(codigoModuloParameter));
			casoTeste.setCodigoFeature(Integer.valueOf(codigoFeatureParameter));
			casoTeste.setCodigoTipo(Integer.valueOf(codigoTipoParameter));
			casoTeste.setTesteFeature(Integer.valueOf(testeFeatureParameter));
			casoTeste.setDescricaoCasoTeste(descricaoCasoParameter);
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}

		if(StringUtils.isLong(codigoCasoParameter)) {
			casoTeste.setCodigoCasoTeste(Integer.valueOf(codigoCasoParameter));
		}
		
		if(StringUtils.isLong(inativoParameter)) {
			casoTeste.setInativo(Integer.valueOf(inativoParameter));
		}

		CasoTesteServices casoTesteServices = new CasoTesteServicesImpl(connection);
		try 
		{
			isUpdated = casoTesteServices.adicionar(casoTeste);
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
		return "redirect:/homologador/entrada?controller=CasoTeste&acao=Listar";	
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;
		
		String codigoCasoParameter =request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = null;

		if(StringUtils.isLong(codigoCasoParameter)) {
			codigo = Integer.valueOf(codigoCasoParameter);
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}

		CasoTesteServices casoTesteServices = new CasoTesteServicesImpl(connection);
		CasoTeste casoTeste = null;

		List<Tipo> tipos = null;
		List<Feature> features = null;
		List<Modulo> modulos = null;
		
		try 
		{
			casoTeste = casoTesteServices.getCasoById(codigo);
			
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
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		request.setAttribute(ConstantDataManager.OBJETO_CASO_TESTE, casoTeste);
		
		return "forward:caso-teste/form-caso-teste-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = ConstantDataManager.BLANK;
		boolean isRemoved = false;
		
		String codigoCasoParameter =request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = null;

		if(StringUtils.isLong(codigoCasoParameter)) {
			codigo = Integer.valueOf(codigoCasoParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}
		
		CasoTesteServices casoTesteServices = new CasoTesteServicesImpl(connection);
		try 
		{
			isRemoved = casoTesteServices.remove(codigo);
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
		return "redirect:/homologador/entrada?controller=CasoTeste&acao=Listar";
	}
	
	List<Tipo> ultimoFiltro;
	
	@Override
	public String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		final String filtrarParameter = request.getParameter(ConstantDataManager.PARAMETER_FILTRAR);

		List<Modulo> modulos = null;
		List<Tipo> tipos = null;
		List<Feature> features = null;
		Filtro filtro = null;
		try
		{
			ModuloServices moduloServices = new ModuloServicesImpl(connection);
			filtro = moduloServices.getUltimoFiltro();
			
			TipoServices tipoServices = new TipoServicesImpl(connection);
			tipos = tipoServices.getComboTipos();
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_TIPOS, tipos);

			FeatureServices featureServices = new FeatureServicesImpl(connection);
			features = featureServices.getComboFeatures();
			request.setAttribute(ConstantDataManager.OBJETO_COMBO_FEATURES, features);

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
			return "forward:caso-teste/form-caso-teste-novo.jsp";
		} else {
			return "forward:caso-teste/filtrar-caso-teste.jsp";
		}
	}
}
