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
import br.com.homologador.model.Tipo;
import br.com.homologador.services.FeatureServices;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.TipoServices;
import br.com.homologador.services.impl.FeatureServicesImpl;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.services.impl.TipoServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class TipoController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		List<Tipo> tipos = null;

		TipoServices tipoServices = new TipoServicesImpl(connection);
		try 
		{
			tipos = tipoServices.getAll();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		tipos.sort(Comparator.comparing(t -> t.getDescricaoModulo()));
		
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_TIPOS, tipos);
		
		return "forward:tipo/lista-tipos.jsp";
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = null;
		boolean isUpdated = false;
		
		final String moduloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		final String featureParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_FEATURE);
		final String codigoTipoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_TIPO);
		final String descricaoTipoParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_TIPO);
		
		Tipo tipo = new Tipo();
		
		if(StringUtils.isLong(moduloParameter) && StringUtils.isLong(featureParameter) && !StringUtils.isNull(descricaoTipoParameter)) {
			tipo.setCodigoModulo(Integer.valueOf(moduloParameter));
			tipo.setCodigoFeature(Integer.valueOf(featureParameter));
			tipo.setDescricaoTipo(descricaoTipoParameter);
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}
		
		if(StringUtils.isLong(codigoTipoParameter)) {
			tipo.setCodigoTipo(Integer.valueOf(codigoTipoParameter));
		}
				
		TipoServices tipoServices = new TipoServicesImpl(connection);
		try
		{
			isUpdated = tipoServices.adiciona(tipo);
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

		return "redirect:entrada?controller=Tipo&acao=Listar";
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String codigoParamereter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = Integer.valueOf(codigoParamereter); 

		Tipo tipo = null;
		List<Feature> features  = null;
		List<Modulo> modulos = null;
		
		TipoServices tipoServices = new TipoServicesImpl(connection);
		try 
		{
			tipo = tipoServices.getTipoById(codigo);

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
			throw new RuntimeException();
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
		
		request.setAttribute(ConstantDataManager.CADASTRO_TIPO_OBJETO_TIPO, tipo);

		return "forward:tipo/form-tipo-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		boolean isRemoved = false;
		String message = null;
		
		String codigoParamereter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = Integer.valueOf(codigoParamereter); 
		
		TipoServices tipoServices = new TipoServicesImpl(connection);
		try 
		{
			isRemoved = tipoServices.remove(codigo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		if(isRemoved) {
			message = ConstantDataManager.MESSAGE_INATIVAR_SUCESSO;
		} else {
			message = ConstantDataManager.MESSAGE_PROBLEMAS_INATIVAR;
		}
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		return "redirect:entrada?controller=Tipo&acao=Listar";
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
		
		request.setAttribute(ConstantDataManager.OBJETO_COMBO_MODULOS, modulos);
		
		return "forward:tipo/form-tipo-novo.jsp";	
	}

}
