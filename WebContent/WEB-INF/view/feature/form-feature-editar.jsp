<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/entrada" var="linkServletFeature"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=Feature&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Feature&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Alteração de Feature</h4>
		</div>
	
		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletFeature }" method="POST">
			<section>
				<div class="row">
					<div class="col m12">
						<h6>Módulos:</h6>
						<select name="modulo" id="modulo" disabled="disabled">
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }"${modulo.codigoModulo == feature.codigoModulo ? 'selected' : '0'}>${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m5">
						<h6>Codigo Feature:</h6> 
						<input type="number" min="0" value="${feature.codigoFeature }" name="codigoFeature" id="codigoFeature" readonly/>
					</div>
					<div class="col m5">
						<h6>Descricao:</h6>
						<input type="text" min="0" max="30" value="${feature.descricaoFeature }" name="descricaoFeature" id="descricaoFeature" />
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					<input  type="hidden" value="${feature.codigoModulo }" name="modulo" />
					</div>
				</div>
				<div>
					<input name ="acao" type="hidden" value="Adicionar" />
					<input name ="controller" type="hidden" value="Feature" />
				</div>
			</section>
			</form>
		</div>	
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
	<c:import url="scripts-feature.jsp"></c:import>
</html>