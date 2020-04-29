<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletTeste"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=Teste&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
			</div>
			<h4 class="center">Cadastro de Teste</h4>
		</div>
		
		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletTeste }" method="POST" id="novo">
			<section>
				<div class="row" id="rowModulos">
					<div class="col m4">
						<h6>Modulo</h6>
						<select name="modulo" id="modulo">
							<option value="">Selecione...</option>
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }">${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Feature</h6>
						<select name="feature" id="feature">
							<option value="0">Selecione...</option>
						</select>
					</div>
					<div class="col m4">				
						<h6>Tipo</h6>
						<select name="tipo" id="tipo">
							<option value="0">Selecione...</option>
						</select>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m10" id="observacao">
						<h6>Observação</h6>
						<textarea name="observacao" id="observacao" class="materialize-textarea"></textarea>
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
				<div>
					<input type="hidden" name="acao" value="Adicionar">
					<input type="hidden" name="controller" value="Teste">
				</div>
			</section>
			</form>
		</div>
	</body>
<c:import url="../menu/imports-js.jsp"></c:import>
<c:import url="scripts-teste.jsp"></c:import>
</html>