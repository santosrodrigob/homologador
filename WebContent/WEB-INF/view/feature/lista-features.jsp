<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Feature&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Lista de Features Cadastradas</h4>
		</div>

			<div class="divider"></div>
			<table class="striped centered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Descrição</th>
						<th>Módulo</th>
						<th colspan="2">Ações</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${features }" var="feature">
					<tr>
						<td>
							${feature.codigoFeature }
						</td>
						<td>
							${feature.descricaoFeature }
						</td>
						<td>
							${feature.descricaoModulo }
						</td>
						<td>
							<c:if test="${feature.inativo == 0 }">
								<a href="entrada?controller=Feature&acao=Remover&codigo=${feature.codigoFeature }" id="remover">
								<i class="material-icons">close</i>
								</a>
							</c:if>
							<c:if test="${feature.inativo != 0 }">
								<a href="entrada?controller=Feature&acao=Remover&codigo=${feature.codigoFeature }" id="remover">
								<i class="material-icons">check</i>
								</a>
							</c:if>
						</td>
						<td>
							<a href="entrada?controller=Feature&acao=Mostrar&codigo=${feature.codigoFeature }" id="editar">
							<i class="material-icons">edit</i>
							</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
				
		<input type="hidden" name="controller" value="Feature">
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>