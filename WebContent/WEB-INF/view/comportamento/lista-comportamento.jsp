<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu-comportamento.jsp"></c:import>
	</head>
	<body>
		<div class="col m12 stratButtons">
			<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Comportamento&acao=Open">
				<i class="material-icons">add</i>
			</a>
			<h4 class="center">Lista de Comportamentos</h4>
		</div>

		<div class="divider"></div>
		<section>
			<div class="col m4 right">
				<input id="pesquisa" name="pesquisa" placeholder="Pesquisar"/>
			</div>
			<table class="striped centered">
				<thead>
					<tr>
						<th>Código Comportamento</th>
						<th>Descrição</th>
						<th>Teste Feature</th>
						<th>Modulo/Feature/Tipo</th>
						<th colspan="2">Ações</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${listaComportamentos }" var="comportamento">
					<tr class="testes">
						<td class="codigo">
							${comportamento.codigoComportamento }
						</td>
						<td class="descricao">
							${comportamento.descricaoComportamento }
						</td>
						<td class="testeFeature">
						<c:if test="${comportamento.testeFeature == 0}">
							<i class="material-icons">remove</i>
						</c:if>
						<c:if test="${comportamento.testeFeature != 0}">
							<i class="material-icons">check</i>
						</c:if>
						</td>
						<td class="tipos">
							${comportamento.descricaoModulo }/${comportamento.descricaoFeature }/${comportamento.descricaoTipo }
						</td>
						<td class="inativo">
						<c:if test="${comportamento.inativo == 0}">
							<a href="entrada?controller=Comportamento&acao=Remover&codigo=${comportamento.codigoComportamento }" id="remover">
							<i class="material-icons">close</i>
							</a>
						</c:if>
						<c:if test="${comportamento.inativo != 0}">
							<a href="entrada?controller=Comportamento&acao=Remover&codigo=${comportamento.codigoComportamento }" id="remover">
							<i class="material-icons">check</i>
							</a>
						</c:if>
						</td>
						<td class="edicao">
							<a href="entrada?controller=Comportamento&acao=Mostrar&codigo=${comportamento.codigoComportamento }" id="editar">
							<i class="material-icons">edit</i>
							</a> 
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</section>
		<input type="hidden" name="controller" value="Comportamento">
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>