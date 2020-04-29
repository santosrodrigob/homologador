<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletComportamneto"></c:url>
<html>
	<head>
		<c:import url="../menu/menu-comportamento.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=Comportamento&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Comportamento&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Alteração de Comportamento</h4>
		</div>

		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletComportamneto }" method="POST" id="novo">
			<section>
				<div class="row" id="rowModulos">
					<div class="col m4">
						<h6>Modulo</h6>
						<select name="modulo" id="modulo" disabled="disabled">
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }" ${modulo.codigoModulo == comportamento.codigoModulo ? 'selected' : '' }>${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Feature</h6>
						<select name="feature" id="feature" disabled="disabled">
							<c:forEach items="${features }" var="feature">
								<option value="${feature.codigoFeature }" ${feature.codigoFeature == comportamento.codigoFeature ? 'selected' : '' }>${feature.descricaoFeature }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Tipo</h6>
						<select name="tipo" id="tipo" disabled="disabled">
							<c:forEach items="${tipos }" var="tipo">
								<option value="${tipo.codigoTipo }" ${tipo.codigoTipo == comportamento.codigoTipo ? 'selected' : '' }>${tipo.descricaoTipo }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m4">
						<h6>Código Comportamento: </h6>
						<input id="codigoComportamento" name="codigoComportamento" type="number" min="1" value="${comportamento.codigoComportamento }" readonly="readonly"/>

						<input name="modulo" type="hidden" min="1" value="${comportamento.codigoModulo }" readonly="readonly"/>
						<input name="feature" type="hidden" min="1" value="${comportamento.codigoFeature }" readonly="readonly"/>
						<input name="tipo" type="hidden" min="1" value="${comportamento.codigoTipo }" readonly="readonly"/>
					</div>
					<div class="col m4">
						<h6>Teste Feature</h6>
						<select name="testeFeature" id="testeFeature">
							<option value="0" ${comportamento.testeFeature == 0 ? 'selected' : '' }>NÃO</option>
							<option value="1" ${comportamento.testeFeature == 1 ? 'selected' : '' }>SIM</option>
						</select>
					</div>
					<div class="col m4">
						<h6>Inativo</h6>
						<select name="inativo" id="inativo">
							<option value="0" ${comportamento.inativo == 0 ? 'selected' : '' }>ATIVO</option>
							<option value="1" ${comportamento.inativo == 1 ? 'selected' : '' }>INATIVO</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col m10">
						<h6>Digite o Comportamento: </h6>
						<textarea name="descricaoComportamento" id="descricaoComportamento" class="materialize-textarea">${comportamento.descricaoComportamento }</textarea>
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
				<div>
					<input type="hidden" name="acao" value="Adicionar">
					<input type="hidden" name="controller" value="Comportamento">
				</div>
			</section>
			</form>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
	<c:import url="scripts-comportamento.jsp"></c:import>
</html>