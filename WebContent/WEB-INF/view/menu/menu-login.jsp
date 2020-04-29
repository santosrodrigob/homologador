<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletFiltreTeste"></c:url>

<c:import url="../menu/imports-css.jsp"></c:import>

	<meta charset="ISO-8859-1">
	<title>Homologador</title>

	<nav>
		<div class="nav-wrapper grey darken-1">
		  <ul class="left hide-on-med-and-down">
		  	<li>
		    	<a href="#"><span id="usuario"><strong>Usuário: ${usuarioLogado.usuario }</strong></span></a>
		    </li>
		  </ul>
		  <ul class="right hide-on-med-and-down">
		    <li><strong><a href="entrada?controller=LeiaMe&acao=Open&filtrar=1">Leia-Me - Listar</a></strong></li>
		  </ul>
		</div>
	</nav>
<br/>
<br/>
