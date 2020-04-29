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
import javax.servlet.http.HttpSession;

import br.com.homologador.action.Acao;
import br.com.homologador.model.Usuario;
import br.com.homologador.services.LoginServices;
import br.com.homologador.services.impl.LoginServicesImpl;
import br.com.homologador.utils.ConstantDataManager;
import br.com.homologador.utils.StringUtils;

public class LoginController implements Acao {

	@Override
	public String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		return null;
	}

	@Override
	public String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		boolean isValid = false;
		
		String message = ConstantDataManager.BLANK;
		Usuario usuario = new Usuario();
		
		String loginParameter = request.getParameter(ConstantDataManager.PARAMETER_LOGIN);
		String senhaParameter = request.getParameter(ConstantDataManager.PARAMETER_SENHA);
		
		if(StringUtils.isLong(loginParameter) && !StringUtils.isNull(senhaParameter)) {
			usuario.setLogin(Integer.valueOf(loginParameter));
			usuario.setSenha(senhaParameter);
		} else {
			message = ConstantDataManager.MESSAGE_DADOS_OBRIGATORIOS_NAO_INFORMADO;
		}

		LoginServices loginServices = new LoginServicesImpl(connection);
		try 
		{
			isValid = loginServices.validaUsuario(usuario);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		if(isValid)
		{
			message = ConstantDataManager.MESSAGE_USUARIO_LOGADO_SUCESSO;
			request.setAttribute(ConstantDataManager.MESSAGE, message);
			HttpSession sessao = request.getSession();
			sessao.setAttribute(ConstantDataManager.OBJETO_SESSAO_USUARIO_LOGADO, usuario);
			return "redirect:entrada?controller=TesteAtributos&acao=Open&message="+ message +" ";
		}
		else 
		{
			message = ConstantDataManager.MESSAGE_PROBLEMAS_LOGIN;
			request.setAttribute(ConstantDataManager.MESSAGE, message);
			return "redirect:entrada?controller=Login&acao=Open&message="+ message +" ";
		}	
	}

	@Override
	public String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {

		HttpSession sessao = request.getSession();
		sessao.invalidate();
		
		return "redirect:entrada?controller=Login&acao=Open";
	}

	@Override
	public String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection)
			throws ServletException, IOException {
		
		String message = request.getParameter(ConstantDataManager.MESSAGE);
		if(message == null) {
			message = "";
		}
		
		request.setAttribute(ConstantDataManager.MESSAGE, message);
		return "forward:login/form-login.jsp";
	}
}
