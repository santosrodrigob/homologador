<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Tipo&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Lista de Tipos Cadastradas</h4>
		</div>

			<div class="divider"></div>
			<table class="striped centered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Descrição</th>
						<th>Módulo</th>
						<th>Feature</th>
						<th colspan="2">Ações</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${tipos }" var="tipo">
					<tr>
						<td>
							${tipo.codigoTipo }
						</td>
						<td>
							${tipo.descricaoTipo }
						</td>
						<td>
							${tipo.descricaoModulo }
						</td>
						<td>
							${tipo.descricaoFeature }
						</td>
						<td>
						<c:if test="${tipo.inativo == 0 }">
							<a href="entrada?controller=Tipo&acao=Remover&codigo=${tipo.codigoTipo }" id="remover">
							<i class="material-icons">close</i>
							</a>
						</c:if>
						<c:if test="${tipo.inativo != 0 }">
							<a href="entrada?controller=Tipo&acao=Remover&codigo=${tipo.codigoTipo }" id="remover">
							<i class="material-icons">check</i>
							</a>
						</c:if>
						</td>
						<td>
							<a href="entrada?controller=Tipo&acao=Mostrar&codigo=${tipo.codigoTipo }" id="editar">
							<i class="material-icons">edit</i>
							</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		<input type="hidden" name="controller" value="Tipo">
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>