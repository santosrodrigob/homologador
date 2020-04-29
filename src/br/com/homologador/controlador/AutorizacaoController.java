/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controlador;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AutorizacaoController
 */
@WebFilter("/entrada")
public class AutorizacaoController implements Filter {

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		boolean isNotLogin = false;
		boolean isNotActionLogin = false;
		
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		String controller = request.getParameter("controller");
		String action = request.getParameter("acao");
		String filtrar = request.getParameter("filtrar");

		HttpSession sessao = request.getSession();
		isNotLogin = sessao.getAttribute("usuarioLogado") == null;

		if(request.getParameter("controller") == null) {
			response.sendRedirect("entrada?controller=Login&acao=Open");
			return;			
		} else {
			isNotActionLogin = controller.equalsIgnoreCase("Login") || controller.equalsIgnoreCase("LeiaMe");
			if(isNotActionLogin && controller.equalsIgnoreCase("LeiaMe")) {
				isNotActionLogin = action.equalsIgnoreCase("Open") || action.equalsIgnoreCase("Listar");
				if(action.equalsIgnoreCase("Open") && null == filtrar) {
					isNotActionLogin = false;
				}
			}
		}
		
//Nao esta Logado
//E a controller/acao nao eh uma dessas de cima, entao vai ficar redirecionando para o Login novamente.

		if(isNotLogin && !isNotActionLogin) {
			response.sendRedirect("entrada?controller=Login&acao=Open");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
