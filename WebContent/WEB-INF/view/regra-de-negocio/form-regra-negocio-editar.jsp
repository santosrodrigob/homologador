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
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=RegraNegocio&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Altera��o de Regra de Negocio</h4>
		</div>

		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletRegraNegocio }" method="POST" id="novo">
			<section>
				<div class="row" id="rowModulos">
					<div class="col m4">
						<h6>Modulo</h6>
						<select name="modulo" id="modulo" disabled="disabled">
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }" ${modulo.codigoModulo == regraNegocio.codigoModulo ? 'selected' : '' }>${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Feature</h6>
						<select name="feature" id="feature" disabled="disabled">
							<c:forEach items="${features }" var="feature">
								<option value="${feature.codigoFeature }" ${feature.codigoFeature == regraNegocio.codigoFeature ? 'selected' : '' }>${feature.descricaoFeature }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Tipo</h6>
						<select name="tipo" id="tipo" disabled="disabled">
							<c:forEach items="${tipos }" var="tipo">
								<option value="${tipo.codigoTipo }" ${tipo.codigoTipo == regraNegocio.codigoTipo ? 'selected' : '' }>${tipo.descricaoTipo }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m4">
						<h6>C�digo Regra Neg�cio: </h6>
						<input id="codigoRegraNegocio" name="codigoRegraNegocio" type="number" min="1" value="${regraNegocio.codigoRegra }" readonly="readonly"/>

						<input name="modulo" type="hidden" min="1" value="${regraNegocio.codigoModulo }" readonly="readonly"/>
						<input name="feature" type="hidden" min="1" value="${regraNegocio.codigoFeature }" readonly="readonly"/>
						<input name="tipo" type="hidden" min="1" value="${regraNegocio.codigoTipo }" readonly="readonly"/>
					</div>
					<div class="col m4">
						<h6>Teste Feature</h6>
						<select name="testeFeature" id="testeFeature">
							<option value="0" ${regraNegocio.testeFeature == 0 ? 'selected' : '' }>N�O</option>
							<option value="1" ${regraNegocio.testeFeature == 1 ? 'selected' : '' }>SIM</option>
						</select>
					</div>
					<div class="col m4">
						<h6>Inativo</h6>
						<select name="inativo" id="inativo">
							<option value="0" ${regraNegocio.inativo == 0 ? 'selected' : '' }>ATIVO</option>
							<option value="1" ${regraNegocio.inativo == 1 ? 'selected' : '' }>INATIVO</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col m10">
						<h6>Digite a Regra de Neg�cio: </h6>
						<textarea name="descricaoRegra" id="descricaoRegra" class="materialize-textarea">${regraNegocio.descricaoRegraNegocio }</textarea>
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