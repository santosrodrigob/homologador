<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu-caso-teste.jsp"></c:import>
	</head>
	<body>
		<div class="col m12 stratButtons">
			<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=CasoTeste&acao=Open">
				<i class="material-icons">add</i>
			</a>
			<h4 class="center">Lista de Casos de Testes</h4>
		</div>
		
		<div class="divider"></div>
		<section>
			<div class="col m4 right">
				<input id="pesquisa" name="pesquisa" placeholder="Pesquisar"/>
			</div>
			<table id="tablesorter" class="striped centered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Descrição</th>
						<th>Teste Feature</th>
						<th>Módulo/Feature/Tipo</th>
						<th colspan="2">Ações</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${listaCasos }" var="caso">
					<tr class="testes">
						<td class="codigo">
							${caso.codigoCasoTeste }
						</td>
						<td class="descricao">
							${caso.descricaoCasoTeste }
						</td>
						<td class="testeFeature">
						<c:if test="${caso.testeFeature == 0}">
							<i class="material-icons">remove</i>
						</c:if>
						<c:if test="${caso.testeFeature != 0}">
							<i class="material-icons">check</i>
						</c:if>
						</td>
						<td class="tipos">
							${caso.descricaoModulo }/${caso.descricaoFeature }/${caso.descricaoTipo }
						</td>
						<td class="inativo">
						<c:if test="${caso.inativo == 0}">
							<a href="entrada?controller=CasoTeste&acao=Remover&codigo=${caso.codigoCasoTeste }" id="remover">
							<i class="material-icons">close</i>
							</a>
						</c:if>
						<c:if test="${caso.inativo != 0}">
							<a href="entrada?controller=CasoTeste&acao=Remover&codigo=${caso.codigoCasoTeste }" id="remover">
							<i class="material-icons">check</i>
							</a>
						</c:if>
						</td>
						<td>
							<a href="entrada?controller=CasoTeste&acao=Mostrar&codigo=${caso.codigoCasoTeste }" id="editar">
							<i class="material-icons">edit</i>
							</a> 
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</section>
		<input type="hidden" name="controller" value="CasoTeste">
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>