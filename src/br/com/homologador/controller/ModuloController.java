/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;
import br.com.homologador.model.Modulo;
import br.com.homologador.services.ModuloServices;
import br.com.homologador.services.impl.ModuloServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class ModuloController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		List<Modulo> modulos = null;
		ModuloServices moduloservices = new ModuloServicesImpl(connection);
		try 
		{
			modulos = moduloservices.getAll();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		request.setAttribute(ConstantDataManager.OBJETO_LISTA_MODULOS, modulos);
		
		return "forward:modulo/lista-modulos.jsp";
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		boolean isUpdated = false;
		String message = null;
		
		String codigoModuloParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO_MODULO);
		String descricaoParameter = request.getParameter(ConstantDataManager.PARAMETER_DESCRICAO_MODULO);
		
		Modulo modulo = new Modulo();
		if(codigoModuloParameter != null) {
			modulo.setCodigoModulo(Integer.valueOf(codigoModuloParameter));
		}
		
		if(!StringUtils.isNull(descricaoParameter)) {
			modulo.setDescricaoModulo(descricaoParameter);			
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
			throw new RuntimeException();
		}
		
		ModuloServices moduloServices = new ModuloServicesImpl(connection);
		try
		{
			isUpdated = moduloServices.adiciona(modulo);
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
		
		return "redirect:entrada?controller=Modulo&acao=Listar";
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String codigoParamer = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = Integer.valueOf(codigoParamer); 
		
		ModuloServices moduloservices = new ModuloServicesImpl(connection);
		Modulo modulo = null;
		try 
		{
			modulo = moduloservices.getModuloById(codigo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		request.setAttribute(ConstantDataManager.CADASTRO_MODULO_OBJETO_MODULO, modulo);

		return "forward:modulo/form-modulo-editar.jsp";
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		boolean isRemoved = false;
		String message = null;
		
		String codigoParameter = request.getParameter(ConstantDataManager.PARAMETER_CODIGO);
		Integer codigo = Integer.valueOf(codigoParameter); 
		
		ModuloServices moduloservices = new ModuloServicesImpl(connection);
		try 
		{
			isRemoved = moduloservices.remove(codigo);
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
		
		return "redirect:entrada?controller=Modulo&acao=Listar";
	}

	@Override
	public String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
				
		return "forward:modulo/form-modulo-novo.jsp";
	}

}
