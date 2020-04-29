<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div class="col m12 stratButtons">
			<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Teste&acao=Open">
				<i class="material-icons">add</i>
			</a>
			<h4 class="center">Lista de Plano de Testes</h4>
		</div>

		<div class="divider"></div>
		<section>
			<div class="col m4 right">
				<input id="pesquisa" name="pesquisa" placeholder="Pesquisar"/>
			</div>
			<table class="striped centered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Módulo</th>
						<th>Feature</th>
						<th>Tipo</th>
						<th>Data Criação</th>
						<th>Caso de Teste</th>
						<th>Regra de Negócio</th>
						<th>Comportamento</th>
						<th>Qtde. Bugs</th>
						<th colspan="2">Ações</th>
						<th>Gerenciar</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${testes }" var="teste">
					<tr class="testes">
						<td>
							${teste.codigoTeste }
						</td>
						<td class="descricao">
							${teste.descricaoModulo }
						</td>
						<td class="testeFeature">
							${teste.descricaoFeature }
						</td>
						<td class="tipos">
							${teste.descricaoTipo }
						</td>
						<td class="codigo">
							${teste.dataCriacao }
						</td>
						<td>
							<c:if test="${teste.situacaoCasoTeste == 0}">
								<i class="material-icons">close</i>
							</c:if>
							<c:if test="${teste.situacaoCasoTeste != 0}">
								<i class="material-icons">check</i>
							</c:if>
						</td>
						<td>
							<c:if test="${teste.situacaoRegra == 0}">
								<i class="material-icons">close</i>
							</c:if>
							<c:if test="${teste.situacaoRegra != 0}">
								<i class="material-icons">check</i>
							</c:if>
						</td>
						<td>
							<c:if test="${teste.situacaoComportamento == 0}">
								<i class="material-icons">close</i>
							</c:if>
							<c:if test="${teste.situacaoComportamento != 0}">
								<i class="material-icons">check</i>
							</c:if>
						</td>
						<td class="inativo">
							${teste.qtdeBugs }
						</td>
						<td>
						<c:if test="${teste.inativo == 0}">
							<a href="entrada?controller=Teste&acao=Remover&codigo=${teste.codigoTeste }" id="remover">
							<i class="material-icons">close</i>
							</a>
						</c:if>
						<c:if test="${teste.inativo != 0}">
							<a href="entrada?controller=Teste&acao=Remover&codigo=${teste.codigoTeste }" id="remover">
							<i class="material-icons">check</i>
							</a>
						</c:if>
						</td>
						<td>
							<a href="entrada?controller=Teste&acao=Mostrar&codigo=${teste.codigoTeste }" id="editar">
							<i class="material-icons">edit</i>
							</a> 
						</td>
						<td>
							<a href="entrada?controller=TesteAtributos&acao=Listar&codigoTeste=${teste.codigoTeste }" id="editar">
							<i class="material-icons">build</i>
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