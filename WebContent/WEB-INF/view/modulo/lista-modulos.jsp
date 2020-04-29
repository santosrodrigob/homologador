<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Modulo&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Lista de Módulos Cadastrados</h4>
		</div>

		<div class="divider"></div>
		<div class="container">
			<section>
				<table class="striped centered">
					<thead>
						<tr>
							<th>Código</th>
							<th colspan="2">Descrição</th>
							<th colspan="2">Ações</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${modulos }" var="modulo">
						<tr>
							<td>
								${modulo.codigoModulo }
							</td>
							<td colspan="2">
								${modulo.descricaoModulo }
							</td>
							<td>
								<c:if test="${modulo.inativo == 0 }">
									<a href="entrada?controller=Modulo&acao=Remover&codigo=${modulo.codigoModulo }" id="remover">
									<i class="material-icons">close</i>
									</a> 
								</c:if>
								<c:if test="${modulo.inativo != 0 }">
									<a href="entrada?controller=Modulo&acao=Remover&codigo=${modulo.codigoModulo }" id="remover" >
									<i class="material-icons">check</i>
									</a> 
								</c:if>
							</td>
							<td>
								<a href="entrada?controller=Modulo&acao=Mostrar&codigo=${modulo.codigoModulo }" id="editar">
								<i class="material-icons">edit</i>
								</a> 
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<input type="hidden" name="controller" value="Modulo">
			</section>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>