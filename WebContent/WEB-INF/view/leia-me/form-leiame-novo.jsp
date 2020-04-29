<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/entrada" var="linkServletLeiaMe"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=LeiaMe&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light teal" id="btn-search" href="entrada?controller=LeiaMe&acao=Open&filtrar=1">
					<i class="material-icons">search</i>
				</a>
		</div>
			<h4 class="center">Cadastro de Leia-Me</h4>
		</div>
		
		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletLeiaMe }" method="POST" id="formLeiaMe">
			<section>
				<div class="row">
					<div class="col m4">
						<h6>Código:</h6> 
						<input type="number" name="codigoLeiaMe" id="codigoLeiaMe" disabled/> 
					</div>
					<div class="col m4">
						<h6>Módulos:</h6>
						<select name="modulo" id="modulo">
							<option value="0">Selecione...</option>
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }" ${modulo.codigoModulo == filtro.codigoModulo ? 'selected' : '' }>${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">
						<h6>Versão:</h6>
						<input type="text" min="0" max="12" name="versao" id="versao" value="${filtro.versao }"/>
					</div>
				</div>
				<div class="row">
					<div class="col m10">
						<h6>Descricao</h6>
						<textarea name="descricaoLeiaMe" id="descricaoLeiaMe" class="materialize-textarea"></textarea>
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
				<div>
					<input name ="acao" type="hidden" value="Adicionar" />
					<input name ="controller" type="hidden" value="LeiaMe" />
				</div>
			</section>
			</form>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
	<c:import url="scripts-leiame.jsp"></c:import>
</html>