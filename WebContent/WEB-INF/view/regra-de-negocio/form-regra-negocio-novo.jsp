<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletRegraNegocio"></c:url>
<html>
	<head>
		<c:import url="../menu/menu-regra-negocio.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=RegraNegocio&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
			</div>
			<h4 class="center">Cadastro de Regra de Negocio</h4>
		</div>

		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletRegraNegocio }" method="POST" id="novo">
			<section>
				<div class="row" id="rowModulos">
					<div class="col m4">
						<h6>Modulo</h6>
						<select name="modulo" id="modulo">
							<option value="0">Selecione...</option>
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }" ${modulo.codigoModulo == filtro.codigoModulo ? 'selected' : '' }>${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Feature</h6>
						<select name="feature" id="feature">
							<c:forEach items="${features }" var="feature">
								<option value="${feature.codigoFeature }" ${feature.codigoFeature == filtro.codigoFeature ? 'selected' : '' }>${feature.descricaoFeature }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Tipo</h6>
						<select name="tipo" id="tipo">
							<c:forEach items="${tipos }" var="tipo">
								<option value="${tipo.codigoTipo }" ${tipo.codigoTipo == filtro.codigoTipo ? 'selected' : '' }>${tipo.descricaoTipo }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m4">
						<h6>Código Regra Negócio: </h6>
						<input id="codigoRegraNegocio" id="codigoRegraNegocio" type="number" min="1" readonly="readonly"/>
					</div>
					<div class="col m8">
						<h6>Teste Feature</h6>
						<select name="testeFeature" id="testeFeature">
							<option value="0">NÃO</option>
							<option value="1">SIM</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col m10" id="observacao">
						<h6>Digite a Regra de Negócio: </h6>
						<textarea name="descricaoRegra" id="descricaoRegra" class="materialize-textarea"></textarea>
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
				<div>
					<input type="hidden" name="acao" value="Adicionar">
					<input type="hidden" name="controller" value="RegraNegocio">
				</div>
			</section>
			</form>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
	<c:import url="scripts-regra-negocio.jsp"></c:import>
</html>