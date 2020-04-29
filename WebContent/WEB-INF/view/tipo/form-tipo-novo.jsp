<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/entrada" var="linkServletTipo"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=Tipo&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
			</div>
			<h4 class="center">Cadastro de Tipo</h4>
		</div>
		
		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletTipo }" method="POST" id="novo">
			<section>
				<div class="row">
					<div class="col m6">
						<h6>Módulos:</h6>
						<select name="modulo" id="modulo">
							<option value="0">Selecione...</option>
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }">${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m6">
						<h6>Features:</h6>
						<select name="feature" id="feature">
							<option value="0">Selecione...</option>
							<c:forEach items="${features }" var="feature">
								<option value="${feature.codigoFeature }">${feature.descricaoFeature }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m5">
						<h6>Codigo Tipo:</h6> 
						<input type="number" min="0" name="codigoTipo" id="codigoTipo" disabled/> 
					</div>
					<div class="col m5">
						<h6>Descricao:</h6>
						<input type="text" min="0" max="30" name="descricaoTipo" id="descricaoTipo" />
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
				<div>
					<input name ="acao" type="hidden" value="Adicionar" />
					<input name ="controller" type="hidden" value="Tipo" />
				</div>
			</section>
			</form>
		</div>	
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
	<c:import url="scripts-tipo.jsp"></c:import>
</html>