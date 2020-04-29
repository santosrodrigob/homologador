<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletFiltreTeste"></c:url>

<c:import url="../menu/imports-css.jsp"></c:import>

	<meta charset="UTF-8">
	<title>Homologador</title>

	<nav>
		<div class="nav-wrapper grey darken-1">
		  <ul class="left hide-on-med-and-down">
		  	<li>
		    	<a href="#"><span id="usuario"><strong>Usuário: ${usuarioLogado.usuario }</strong></span></a>
		    </li>
		  </ul>
		  <ul class="right hide-on-med-and-down">
		    <!-- Dropdown Trigger -->
		    <li><a class="dropdown-trigger" href="#!" data-target="dropdown2"><strong>Manutenção de Teste</strong><i class="material-icons center">arrow_drop_down</i></a></li>
		    <li><a class="dropdown-trigger" href="#!" data-target="dropdown1"><strong>Cadastro - Plano de Teste</strong><i class="material-icons center">arrow_drop_down</i></a></li>
		    <li><a href="entrada?controller=Login&acao=Remover">Sair</a></li>
		  </ul>
		</div>
	</nav>

	<ul id="dropdown2" class="dropdown-content">
		<li><a href="entrada?controller=TesteAtributos&acao=Open">Filtrar</a></li>
		<li class="divider"></li>
		<li><a href="entrada?controller=Teste&acao=Open">Novo</a></li>
		<li><a href="entrada?controller=Teste&acao=Listar">Listar</a></li>
	</ul>

	<!-- Dropdown Structure -->

	<ul id="dropdown1" class="dropdown-content">
		<li><a href="entrada?controller=TesteAtributos&acao=Open">Plano de Teste</a></li>
		<li class="divider"></li>
		<li><a href="entrada?controller=CasoTeste&acao=Open&filtrar=1">Caso de Testes</a></li>
		<li><a href="entrada?controller=RegraNegocio&acao=Open&filtrar=1">Regra de Negócio</a></li>
		<li><a href="entrada?controller=Comportamento&acao=Open&filtrar=1">Comportamento</a></li>
		<li class="divider"></li>
		<li><a href="entrada?controller=Tipo&acao=Open">Tipos</a></li>
		<li><a href="entrada?controller=Feature&acao=Open">Features</a></li>
		<li><a href="entrada?controller=Modulo&acao=Open">Modulos</a></li>
		<li class="divider"></li>
		<li><a href="entrada?controller=LeiaMe&acao=Open&filtrar=1">Leia-Me</a></li>
	</ul>
<br/>
<br/>
