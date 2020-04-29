/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;
import br.com.homologador.model.Feature;
import br.com.homologador.model.Modulo;
import br.com.homologador.services.FeatureServices;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.impl.FeatureServicesImpl;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.utils.ConstantDataManager;

public class FeatureController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		FeatureServices featureServices = new FeatureServicesImpl(connection);
		List<Feature> lista = null;
		try 
		{
			lista = featureServices.getAll();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_FEATURES, lista);		
		return "forward:feature/lista-features.jsp";
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		boolean isUpdated = false;
		String message = null;
		
		String codigoFeatureParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_FEATURE);
		String descricaoParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_FEATURE);
		String moduloParameter = request.getParameter(ConstantDataManager.PARAMETER_COMBO_CODIGO_MODULO);
		
		
		Feature feature = new Feature();
		
		if(codigoFeatureParameter != null) {
			feature.setCodigoFeature(Integer.valueOf(codigoFeatureParameter));
		}
		
		feature.setDescricaoFeature(descricaoParameter);
		feature.setCodigoModulo(Integer.valueOf(moduloParameter));
		
		FeatureServices featureServices = new FeatureServicesImpl(connection);
		try
		{
			isUpdated = featureServices.adiciona(feature);
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
		
		return "redirect:entrada?controller=Feature&acao=Listar";
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String codigoParamereter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = Integer.valueOf(codigoParamereter); 
		
		FeatureServices featureServices = new FeatureServicesImpl(connection);
		Feature feature = null;
		List<Modulo> modulos = null;
		try 
		{
			feature = featureServices.getFeatureById(codigo);

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
		
		request.setAttribute(ConstantDataManager.CADASTRO_FEATURE_OBJETO_FEATURE, feature);

		return "forward:feature/form-feature-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		boolean isRemoved = false;
		String message = null;
		
		String codigoParamereter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);		
		Integer codigo = Integer.valueOf(codigoParamereter); 
		
		FeatureServices featureServices = new FeatureServicesImpl(connection);
		try 
		{
			isRemoved = featureServices.remove(codigo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		if(isRemoved) {
			message = ConstantDataManager.MESSAGE_INATIVAR_SUCESSO;
		} else {
			message = ConstantDataManager.MESSAGE_PROBLEMAS_INATIVAR;			
		}
	
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		
		return "redirect:entrada?controller=Feature&acao=Listar";
	}

	@Override
	public String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		List<Modulo> modulos = null;
		ModuloServices moduloServices = new ModuloServicesImpl(connection);
		try
		{
			modulos = moduloServices.getComboModulos();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		request.setAttribute(ConstantDataManager.OBJETO_COMBO_MODULOS, modulos);
		
		return "forward:feature/form-feature-novo.jsp";
	}

}
