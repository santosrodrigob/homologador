<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>

		<div class="col m12 center">
			<div id="relatorio" class="table-responsive">
			</div>
		</div>

		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light red" id="btn-open" href="entrada?controller=LeiaMe&acao=Open">
					<i class="material-icons">add</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light teal" id="btn-search" href="entrada?controller=LeiaMe&acao=Open&filtrar=1">
					<i class="material-icons">search</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light blue-grey" id="btn-file" href="entrada?controller=LeiaMe&acao=Listar&relatorio=1">
					<i class="material-icons">insert_drive_file</i>
				</a>
			</div>
			<p id="message">${message }</p>
			<h4 class="center">Lista de Leia-Me</h4>
		</div>

			<div class="divider"></div>
			<div class="col m4 right">
				<input id="pesquisa" name="pesquisa" placeholder="Pesquisar"/>
			</div>

			<table class="striped centered">
				<thead>
					<tr>
						<th>Data Criação/Alteração</th>
						<th>Versão</th>
						<th>Descrição</th>
						<th>Módulo</th>
						<th>Solicitante</th>
						<th colspan="2">Ações</th>
					</tr>
				</thead>
				<tbody>				
				<c:forEach items="${leiames }" var="leiame">
					<tr class="testes">
						<td class="codigo">
							<c:if test="${leiame.dataAlteracaoFormatada != null }">
								${leiame.dataAlteracaoFormatada }
							</c:if>
							<c:if test="${leiame.dataAlteracaoFormatada == null }">
								${leiame.dataCriacaoFormatada }
							</c:if>
						</td>
						<td class="tipos">
							${leiame.versao }
						</td>
						<td class="descricao">
							${leiame.descricaoLeiaMe }
						</td>
						<td class="testeFeature">
							${leiame.descricaoModulo } / ${leiame.descricaoFeature }
						</td>
						<td class="inativo">
							${leiame.solicitante }
						</td>
						<td class="inativo">
							<c:if test="${leiame.inativo == 0 }">
								<a href="entrada?controller=LeiaMe&acao=Remover&codigo=${leiame.codigoLeiaMe }" id="remover">
								<i class="material-icons">close</i>
								</a>
							</c:if>
							<c:if test="${leiame.inativo != 0 }">
								<a href="entrada?controller=LeiaMe&acao=Remover&codigo=${leiame.codigoLeiaMe }" id="remover">
								<i class="material-icons">check</i>
								</a>
							</c:if>
						</td>
						<td>
							<a href="entrada?controller=LeiaMe&acao=Mostrar&codigo=${leiame.codigoLeiaMe }" id="editar">
							<i class="material-icons">edit</i>
							</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>