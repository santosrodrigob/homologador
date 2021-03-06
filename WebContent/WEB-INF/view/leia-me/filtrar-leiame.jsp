<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletFiltraRegraNegocio"></c:url>

<html>
	<head>	
		<c:import url="../menu/menu-regra-negocio.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=LeiaMe&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=LeiaMe&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Filtro de Leia-Me</h4>
		</div>

		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletFiltraRegraNegocio }" method="POST" id="novo">
				<div class="row">
					<div class="col m4">
						<h6 >Modulos</h6>
						<select name="modulo" id="modulo">
							<option value="0">Selecione...</option>
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }">${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col m8">
						<h6 >Descri��o</h6>
						<textarea name="descricaoLeiaMe" id="descricaoLeiaMe" class="materialize-textarea"></textarea>
					</div>
					<div class="col m2" id="inativo">
						<h6 > - </h6>
						<select name="inativo" id="inativo">
							<option value="0" selected >ATIVOS</option>
							<option value="1">INATIVOS</option>
							<option value="2">TODOS</option>
						</select>
					</div>
					<div class="col m2">
						<button class="btn waves-effect waves-light black"  id="btnBuscar" type="submit" name="action">
							Buscar
						</button>
					</div>
				</div>
				<div>
					<input type="hidden" id="controller" name="controller" value="LeiaMe">
					<input type="hidden" id="acao" name="acao" value="Listar">
				</div>
			</form>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>