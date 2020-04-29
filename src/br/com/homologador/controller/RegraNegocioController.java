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
import br.com.homologador.model.Feature;
import br.com.homologador.model.Modulo;
import br.com.homologador.model.RegraNegocio;
import br.com.homologador.model.Tipo;
import br.com.homologador.model.vo.Filtro;
import br.com.homologador.services.FeatureServices;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.RegraServices;
import br.com.homologador.services.TipoServices;
import br.com.homologador.services.impl.FeatureServicesImpl;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.services.impl.RegraServicesImpl;
import br.com.homologador.services.impl.TipoServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class RegraNegocioController implements Acao {


	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		final String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		final String codigoFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
		final String codigoTipoParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_TIPO);
		final String codigoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_REGRA_NEGOCIO);
		final String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
			
		RegraNegocio regraNegocio = new RegraNegocio();
		
		if(StringUtils.isLong(codigoModuloParameter)) {
			regraNegocio.setCodigoModulo(Integer.valueOf(codigoModuloParameter));
		}

		if(StringUtils.isLong(codigoFeatureParameter)) {
			regraNegocio.setCodigoFeature(Integer.valueOf(codigoFeatureParameter));
		}

		if(StringUtils.isLong(codigoTipoParameter)) {
			regraNegocio.setCodigoTipo(Integer.valueOf(codigoTipoParameter));
		}

		if(StringUtils.isLong(codigoRegraParameter)) {
			regraNegocio.setCodigoRegra(Integer.valueOf(codigoRegraParameter));
		}

		if(StringUtils.isLong(inativoParameter)) {
			regraNegocio.setInativo(Integer.valueOf(inativoParameter));
		}

		RegraServices regraServices = new RegraServicesImpl(connection);
		try 
		{
			regraServices.getAll(regraNegocio);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		List<RegraNegocio> listaRegras = regraNegocio.getRegras();
		
		listaRegras.sort(Comparator.comparing(l -> l.getDescricaoModulo()));
	
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_REGRA_NEGOCIO, listaRegras);
		
		return "forward:regra-de-negocio/lista-regra-negocio.jsp";
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		String message = ConstantDataManager.BLANK;
		boolean isUpdated = false;
		
		String codigoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_REGRA_NEGOCIO);
		String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		String codigoFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
		String codigoTipoParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_TIPO);
		String descricaoRegraParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_REGRA_NEGOCIO);
		String testeFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_TESTE_FEATURE);
		String inativoParameter = request.getParameter(ConstantDataManager.PARAMETER_INATIVO);
		
		RegraNegocio regraNegocio = new RegraNegocio();
		
		if(StringUtils.isLong(codigoModuloParameter) && StringUtils.isLong(codigoFeatureParameter) && StringUtils.isLong(codigoTipoParameter)
				&& !StringUtils.isNull(descricaoRegraParameter) && StringUtils.isLong(testeFeatureParameter)) {
			regraNegocio.setCodigoModulo(Integer.valueOf(codigoModuloParameter));
			regraNegocio.setCodigoFeature(Integer.valueOf(codigoFeatureParameter));
			regraNegocio.setCodigoTipo(Integer.valueOf(codigoTipoParameter));
			regraNegocio.setTesteFeature(Integer.valueOf(testeFeatureParameter));
			regraNegocio.setDescricaoRegraNegocio(descricaoRegraParameter);
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}

		if(StringUtils.isLong(codigoRegraParameter)) {
			regraNegocio.setCodigoRegra(Integer.valueOf(codigoRegraParameter));
		}
		
		if(StringUtils.isLong(inativoParameter)) {
			regraNegocio.setInativo(Integer.valueOf(inativoParameter));
		}

		RegraServices regraServices = new RegraServicesImpl(connection);
		
		try 
		{
			isUpdated = regraServices.adicionar(regraNegocio);
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
		return "redirect:/homologador/entrada?controller=RegraNegocio&acao=Listar";
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		String message = ConstantDataManager.BLANK;
		
		String codigoRegraParameter =request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = null;

		if(StringUtils.isLong(codigoRegraParameter)) {
			codigo = Integer.valueOf(codigoRegraParameter);
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}

		RegraServices regraServices = new RegraServicesImpl(connection);
		RegraNegocio regraNegocio = null;

		List<Tipo> tipos = null;
		List<Feature> features = null;
		List<Modulo> modulos = null;
		try 
		{
			regraNegocio = regraServices.getRegraById(codigo);
			
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
		request.setAttribute(ConstantDataManager.OBJETO_REGRA_NEGOCIO, regraNegocio);
		
		return "forward:regra-de-negocio/form-regra-negocio-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		String message = ConstantDataManager.BLANK;
		boolean isRemoved = false;
		
		String codigoRegraParameter =request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = null;

		if(StringUtils.isLong(codigoRegraParameter)) {
			codigo = Integer.valueOf(codigoRegraParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}
		
		RegraServices regraServices = new RegraServicesImpl(connection);
		try 
		{
			isRemoved = regraServices.remove(codigo);
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
		return "redirect:/homologador/entrada?controller=RegraNegocio&acao=Listar";		
	}

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
		
		request.setAttribute("filtro", filtro);
		
		if(StringUtils.isNull(filtrarParameter)) {
			return "forward:regra-de-negocio/form-regra-negocio-novo.jsp";
		} else {
			return "forward:regra-de-negocio/filtrar-regra-negocio.jsp";
		}
	}
}
