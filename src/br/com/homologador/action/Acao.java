package br.com.homologador.action;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Acao {

	String listarAction(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException;
	String adicionarAction(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException;
	String mostrarAction(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException;
	String removerAction(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException;
	String openAction(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException;
}
