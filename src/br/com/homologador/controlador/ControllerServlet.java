/**
 * @author Rodrigo Borges 
 * @date 01/03/2020
 */

package br.com.homologador.controlador;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.homologador.action.Acao;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet(name = "entrada", urlPatterns = { "/entrada" })
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ControllerServlet() {
        super();
    }

	protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {	
		
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = null;
		
		try
		{
			connection = connectionFactory.getConnectionMySQL();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String acaoParameter = request.getParameter("acao");
		String controllerParameter = request.getParameter("controller");

		String nome = null;
		String nomeDaClasse = "br.com.homologador.controller." + controllerParameter + "Controller";
		
		try 
		{
			Class classe = Class.forName(nomeDaClasse);
			Acao acao = (Acao) classe.newInstance();

			if(acaoParameter.equalsIgnoreCase("Open")) {				
				
				nome = acao.openAction(request, response, connection);
			
			} else if(acaoParameter.equalsIgnoreCase("Adicionar")) {

				nome = acao.adicionarAction(request, response, connection);

			} else if(acaoParameter.equalsIgnoreCase("Listar")) {
				
				nome = acao.listarAction(request, response, connection);
			
			} else if(acaoParameter.equalsIgnoreCase("Mostrar")) {				
				
				nome = acao.mostrarAction(request, response, connection);
			
			} else if(acaoParameter.equalsIgnoreCase("Remover")) {
			
				nome = acao.removerAction(request, response, connection);
			}
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) 
		{
			throw new ServletException(e);
		}
		
		String[] tipoEEndereco = nome.split(":");

		if(tipoEEndereco[0].equalsIgnoreCase("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/"+ tipoEEndereco[1]);
			rd.forward(request, response); 
		} else if(tipoEEndereco[0].equalsIgnoreCase("json")) {
			response.setContentType("appication/json");
			response.getWriter().print(tipoEEndereco[1]);
		} else {
			response.sendRedirect(tipoEEndereco[1]);
		}

/*		
		if(controllerParameter.equalsIgnoreCase("RegraNegocio")) {

			if(acaoParameter.equalsIgnoreCase("Login")) {
				
				RegraNegocioController regraNegocioController = new RegraNegocioController();
				nome = regraNegocioController.loginAction(request, response, connection);
				
			} else if(acaoParameter.equalsIgnoreCase("AdicionaRegra")) {
				
				RegraNegocioController regraNegocioController = new RegraNegocioController();
				nome = regraNegocioController.adicionarAction(request, response, connection);

			} else if(acaoParameter.equalsIgnoreCase("Listar")) {

				RegraNegocioController regraNegocioController = new RegraNegocioController();
				nome = regraNegocioController.listarAction(request, response, connection);
			
			} else if(acaoParameter.equalsIgnoreCase("MostraRegra")) {
				
				RegraNegocioController regraNegocioController = new RegraNegocioController();
				nome = regraNegocioController.mostrarAction(request, response, connection);
			
			} else if(acaoParameter.equalsIgnoreCase("RemoveRegra")) {
				
				RegraNegocioController regraNegocioController = new RegraNegocioController();
				nome = regraNegocioController.removerAction(request, response, connection);
			}
		}
*/
/*
		if(acaoParameter.equalsIgnoreCase("RegraNegocio")) {
			
			RegraNegocio regraNegocio = new RegraNegocio();
			nome = regraNegocio.executaAcao(request, response, connection);
			
		} else if(acaoParameter.equalsIgnoreCase("AdicionaRegra")) {
			
			AdicionaRegraNegocio adicionaRegraNegocio = new AdicionaRegraNegocio();
			nome = adicionaRegraNegocio.executaAcao(request, response, connection);
		
		} else if(acaoParameter.equalsIgnoreCase("Listar")) {

			Listar listar = new Listar();
			nome = listar.executaAcao(request, response, connection);
		
		} else if(acaoParameter.equalsIgnoreCase("MostraRegra")) {
			
			MostraRegra mostraRegra = new MostraRegra();
			nome = mostraRegra.executaAcao(request, response, connection);
		
		} else if(acaoParameter.equalsIgnoreCase("RemoveRegra")) {
			
			RemoveRegra removeRegra = new RemoveRegra();
			nome = removeRegra.executaAcao(request, response, connection);
		} 
*/
	}
}
