<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletFiltreTeste"></c:url>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Homologador</title>

	<link href="assets/css/principal.css" rel="stylesheet" type="text/css">
	<link href="assets/css/libs/materialize.min.css" rel="stylesheet" type="text/css">
</head>
<body>
	<ul class="menu">
	    <li><a href="#">Teste</a>
	        <ul class="submenu-1"> <!-- Esse é o 2 nivel ou o primeiro Drop Down -->
	            <li><a href="entrada?controller=Teste&acao=Open">Novo</a></li>
	            <li><a href="entrada?controller=Teste&acao=Listar">Listar</a></li>
	            <li><a href="entrada?controller=TesteAtributos&acao=Open">Filtrar</a></li>
			</ul>
	   	</li>
	    <li><a href="#">Regra de Negocio</a>
	        <ul class="submenu-1">
	            <li><a href="entrada?controller=RegraNegocio&acao=Open">Novo</a></li>
	            <li><a href="entrada?controller=RegraNegocio&acao=Listar">Filtrar</a></li>
			</ul>
	   	</li>
	    <li><a href="#">Caso de Teste</a>
	        <ul class="submenu-1">
	            <li><a href="entrada?controller=CasoTeste&acao=Open">Novo</a></li>
	            <li><a href="entrada?controller=CasoTeste&acao=Listar">Filtrar</a></li>
			</ul>
	   	</li>
	    <li><a href="#">Comportamento</a>
	        <ul class="submenu-1">
	            <li><a href="entrada?controller=Comportamento&acao=Open">Novo</a></li>
	            <li><a href="entrada?controller=Comportamento&acao=Listar">Filtrar</a></li>
			</ul>
	   	</li>
	    <li><a href="#">Modulo</a>
	        <ul class="submenu-1">
	            <li><a href="entrada?controller=Modulo&acao=Open">Novo</a></li>
	            <li><a href="entrada?controller=Modulo&acao=Listar">Filtrar</a></li>
			</ul>
	   	</li>
	    <li><a href="#">Feature</a>
	        <ul class="submenu-1">
	            <li><a href="entrada?controller=Feature&acao=Open">Novo</a></li>
	            <li><a href="entrada?controller=Feature&acao=Listar">Filtrar</a></li>
			</ul>
	   	</li>
	    <li><a href="#">Tipo</a>
	        <ul class="submenu-1">
	            <li><a href="entrada?controller=Tipo&acao=Open">Novo</a></li>
	            <li><a href="entrada?controller=Tipo&acao=Listar">Filtrar</a></li>
			</ul>
	   	</li>
	</ul>
</body>
</html>
	