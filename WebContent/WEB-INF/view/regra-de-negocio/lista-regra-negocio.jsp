<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu-regra-negocio.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=RegraNegocio&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Lista de Regras de Negócio</h4>
		</div>

		<div class="divider"></div>
		<section>
			<div class="col m4 right">
				<input id="pesquisa" name="pesquisa" placeholder="Pesquisar"/>
			</div>
			<table class="striped centered">
				<thead>
					<tr>
						<th>Código Regra</th>
						<th>Descrição</th>
						<th>Teste Feature</th>
						<th>Módulo/Feature/Tipo</th>
						<th colspan="2">Ações</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${listaRegras }" var="regra">
					<tr class="testes">
						<td class="codigo">
							${regra.codigoRegra }
						</td>
						<td class="descricao">
							${regra.descricaoRegraNegocio }
						</td>
						<td class="testeFeature">
						<c:if test="${regra.testeFeature == 0}">
							<i class="material-icons">remove</i>
						</c:if>
						<c:if test="${regra.testeFeature != 0}">
							<i class="material-icons">check</i>
						</c:if>
						</td>
						<td class="tipos">
							${regra.descricaoModulo }/${regra.descricaoFeature }/${regra.descricaoTipo }
						</td>
						<td class="inativo">
						<c:if test="${regra.inativo == 0}">
							<a href="entrada?controller=RegraNegocio&acao=Remover&codigo=${regra.codigoRegra }" id="remover">
							<i class="material-icons">close</i>
							</a>
						</c:if>
						<c:if test="${regra.inativo != 0}">
							<a href="entrada?controller=RegraNegocio&acao=Remover&codigo=${regra.codigoRegra }" id="remover">
							<i class="material-icons">check</i>
							</a>
						</c:if>
						</td>
						<td>
							<a href="entrada?controller=RegraNegocio&acao=Mostrar&codigo=${regra.codigoRegra }" id="editar">
							<i class="material-icons">edit</i>
							</a> 
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</section>
		<input type="hidden" name="controller" value="RegraNegocio">
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>