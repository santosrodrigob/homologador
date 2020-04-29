<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/entrada" var="linkServletModulo"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=Modulo&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Modulo&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Alteração de Modulo</h4>
		</div>
	
		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletModulo }" method="POST">
			<section>
				<div class="row">
					<div class="col m5">
						<h6>Codigo Módulo:</h6> 
						<input type="number" min="0" value="${modulo.codigoModulo }" name="codigoModulo" id="codigoModulo" readonly/> 
					</div>
					<div class="col m5">
						<h6>Descricao:</h6> 
						<input type="text" min="0" max="30" value="${modulo.descricaoModulo }" name="descricaoModulo" id="descricaoModulo" />
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
					<div>
						<input name ="acao" type="hidden" value="Adicionar" />
						<input name ="controller" type="hidden" value="Modulo" />
					</div>
				</div>
			</section>
			</form>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
	<c:import url="scripts-modulo.jsp"></c:import>
</html>