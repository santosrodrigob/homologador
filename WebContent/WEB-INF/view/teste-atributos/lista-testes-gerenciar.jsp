<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=Teste&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Teste&acao=Open">
					<i class="material-icons">add</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light blue-grey" id="btn-file" href="entrada?controller=TesteAtributos&acao=Listar&relatorio=1&codigoTeste=${codigoTeste }">
					<i class="material-icons">insert_drive_file</i>
				</a>
			</div>
			<p id="message">${message }</p>
			<h4 class="center">Manutenção de Testes</h4>
		</div>

			<div class="row">
				<div class="col m4">
					<h6>Plano de Teste</h6>
					<input class="center" name="casoTeste" id="casoTeste" value="${codigoTeste }" readonly/>
				</div>
				<div class="col m4 right">
					<input id="pesquisa" name="pesquisa" placeholder="Pesquisar"  style="padding: 20px;" />
				</div>
			</div>
		
		<section>
			<div class="row">
			<div class="divider"></div>
				<div class="col m12">
					<h6 class="brown lighten-5">
					<strong>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						Casos de Teste
					</strong></h6>
					<div class="divider"></div>
				</div>
			</div>

			<table class="striped centered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Descrição</th>
						<th>Bugs</th>
						<th>Situação</th>
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
							${caso.qtdeBugs }
						</td>
						<td class="tipos">
							<c:if test="${caso.situacao == 0 }">
								<a href="entrada?controller=TesteAtributos&acao=Remover&codigoTeste=${codigoTeste }&codigoCasoTeste=${caso.codigoCasoTeste }&inativo=${caso.inativo }&situacao=1">
								<i class="material-icons">close</i>
								</a>
							</c:if>
							<c:if test="${caso.situacao != 0 }">
								<a href="entrada?controller=TesteAtributos&acao=Remover&codigoTeste=${codigoTeste }&codigoCasoTeste=${caso.codigoCasoTeste }&inativo=${caso.inativo }&situacao=0">
								<i class="material-icons">check</i>
								</a>
							</c:if>
						</td>
						<td class="inativo">
							<a href="entrada?controller=TesteAtributos&acao=Mostrar&codigoTeste=${codigoTeste }&codigoCasoTeste=${caso.codigoCasoTeste }">
							<i class="material-icons">edit</i>
							</a>
						</td>
						<td>
							<a href="entrada?controller=Bugs&acao=Mostrar&codigoTeste=${codigoTeste }&codigoCasoTeste=${caso.codigoCasoTeste }">
							<i class="material-icons">bug_report</i>
							</a>
						</td>
					</tr>
					<c:if test="${caso.observacao != null && caso.conclusao == null }">
						<tr>
							<td>
								<strong>Observação</strong>: ${caso.observacao }
							</td>
						</tr>
					</c:if>
					<c:if test="${caso.conclusao != null }">
						<tr>
							<td>
								<strong>Conclusão</strong>: ${caso.conclusao }
							</td>
						</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</section>
		<section>
			<div class="row">
			<div class="divider"></div>
				<div class="col m12">
					<h6 class="brown lighten-5"><strong>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						Regras de Negócio
					</strong></h6>
					<div class="divider"></div>
				</div>
			</div>
		
			<table class="striped centered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Descrição</th>
						<th>Bugs</th>
						<th>Situação</th>
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
							${regra.qtdeBugs }
						</td>
						<td class="tipos">
							<c:if test="${regra.situacao == 0 }">
								<a href="entrada?controller=TesteAtributos&acao=Remover&codigoTeste=${codigoTeste }&codigoRegraNegocio=${regra.codigoRegra }&inativo=${regra.inativo }&situacao=1">
									<i class="material-icons">close</i>
								</a>
							</c:if>
							<c:if test="${regra.situacao != 0 }">
								<a href="entrada?controller=TesteAtributos&acao=Remover&codigoTeste=${codigoTeste }&codigoRegraNegocio=${regra.codigoRegra }&inativo=${regra.inativo }&situacao=0">
									<i class="material-icons">check</i>
								</a>
							</c:if>
						</td>
						<td class="inativo">
							<a href="entrada?controller=TesteAtributos&acao=Mostrar&codigoTeste=${codigoTeste }&codigoRegraNegocio=${regra.codigoRegra }">
							<i class="material-icons">edit</i>
							</a>
						</td>
						<td>
							<a href="entrada?controller=Bugs&acao=Mostrar&codigoTeste=${codigoTeste }&codigoRegraNegocio=${regra.codigoRegra }">
							<i class="material-icons">bug_report</i>
							</a>
						</td>
					</tr>
					<c:if test="${regra.observacao != null && regra.conclusao == null }">
						<tr>
							<td>
								<strong>Observação</strong>: ${regra.observacao }
							</td>
						</tr>
					</c:if>
					<c:if test="${regra.conclusao != null }">
						<tr>
							<td>
								<strong>Conclusão</strong>: ${regra.conclusao }
							</td>
						</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</section>
		<section>
			<div class="divider"></div>
			<div class="row">
				<div class="col m12">
					<h6 class="brown lighten-5">
						<strong>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							Comportamentos
						</strong></h6>
					<div class="divider"></div>
				</div>
			</div>
		</section>
		<section>
			<table class="striped centered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Descrição</th>
						<th>Bugs</th>
						<th>Situação</th>
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
							${comportamento.qtdeBugs }
						</td>
						<td class="tipos">
							<c:if test="${comportamento.situacao == 0 }">
								<a href="entrada?controller=TesteAtributos&acao=Remover&codigoTeste=${codigoTeste }&codigoComportamento=${comportamento.codigoComportamento }&inativo=${comportamento.inativo }&situacao=1">
									<i class="material-icons">close</i>
								</a>
							</c:if>
							<c:if test="${comportamento.situacao != 0 }">
								<a href="entrada?controller=TesteAtributos&acao=Remover&codigoTeste=${codigoTeste }&codigoComportamento=${comportamento.codigoComportamento }&inativo=${comportamento.inativo }&situacao=0">
									<i class="material-icons">check</i>
								</a>
							</c:if>
						</td>
						<td class="inativo">
							<a href="entrada?controller=TesteAtributos&acao=Mostrar&codigoTeste=${codigoTeste }&codigoComportamento=${comportamento.codigoComportamento }">
							<i class="material-icons">edit</i>
							</a>
						</td>
						<td>
							<a href="entrada?controller=Bugs&acao=Mostrar&codigoTeste=${codigoTeste }&codigoComportamento=${comportamento.codigoComportamento }">
							<i class="material-icons">bug_report</i>
							</a>
						</td>
					</tr>
					<c:if test="${comportamento.observacao != null && comportamento.conclusao == null }">
						<tr>
							<td>
								<strong>Observação</strong>: ${comportamento.observacao }
							</td>
						</tr>
					</c:if>
					<c:if test="${comportamento.conclusao != null }">
						<tr>
							<td>
								<strong>Conclusão</strong>: ${comportamento.conclusao }
							</td>
						</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</section>

				
		<input type="hidden" name="controller" value="TestesAtributos">
		<input type="hidden" name="acao" value="Adicionar">
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>