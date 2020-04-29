<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletFiltraRegraNegocio"></c:url>

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
			<h4 class="center">Filtro de Regra de Negócio</h4>
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
					<div class="col m4">
						<h6 >Features</h6>
						<select name="feature" id="feature">
							<option value="0">Selecione...</option>
						</select>					
					</div>
					<div class="col m4">
						<h6 >Tipos</h6>
						<select name="tipo" id="tipo">
							<option value="0">Selecione...</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col m5">
						<h6 >Codigo Regra de Negócio</h6>
						<input name="codigoRegraNegocio" id="codigoRegraNegocio" max="9999" min="1" type="number"/>
					</div>
					<div class="col m5" id="inativo">
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
					<input type="hidden" id="controller" name="controller" value="RegraNegocio">
					<input type="hidden" id="acao" name="acao" value="Listar">
				</div>
			</form>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>