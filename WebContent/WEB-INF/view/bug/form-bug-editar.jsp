<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletBug"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div class="divider"></div>
		<div>
			<h4 class="center">Alteração de Bugs</h4>
		</div>
		
		<section>
			<form action="${linkServletBug }" method="POST">
				<div class="row">
					<div class="col m4">
						<h6 class="center">Codigo de Teste</h6>
						<input class="center" type="number" name="codigoTeste" name="codigoTeste" value="${codigoTeste }" readonly="readonly">
					</div>
					<div class="col m4">
						<h6 class="center">Código - Caso/Regra/Comportamento</h6>
						<input class="center" type="number" name="codigo" name="codigo" 
						value="${codigoCasoTeste != null ? codigoCasoTeste : 
						codigoRegraNegocio != null ? codigoRegraNegocio :
						codigoComportamento != null ? codigoComportamento : '' }" readonly="readonly">
					</div>
					<div class="col m4 center">
						<button class="btn waves-effect waves-light blue" type="submit" name="action">Plano de Testes
						  <i class="material-icons right">fast_rewind</i>
						</button>

						<input type="hidden" name="controller" value="TesteAtributos" />
						<input type="hidden" name="acao" value="Listar" />
					</div>
				</div>
				<div id="barraProgresso">
				      <div class="indeterminate"></div>
				</div>
			</form>
		</section>
		<section>
			<form action="${linkServletBug }" method="POST">
				<div class="row">
					<div class="col m2">
						<select id="tipo" name="tipo" class="center">
							<option value="">Selecione...</option>
							<option value="0">INATIVO</option>
							<option value="1">PRONTO</option>
							<option value="2">MELHORIA</option>
							<option value="3" selected="selected">BUG</option>
						</select>
					</div>
					<div class="col m9">
						<textArea name="bug" id="bugNovo" class="materialize-textarea"></textArea>
						
						<input type="hidden" name="codigoTeste" id="codigoTeste" value="${codigoTeste }" />
						<input type="hidden" name="codigoCasoTeste" id="codigoCasoTeste" value="${codigoCasoTeste }" />
						<input type="hidden" name="codigoRegraNegocio" id="codigoRegraNegocio" value="${codigoRegraNegocio }" />
						<input type="hidden" name="codigoComportamento" id="codigoComportamento" value="${codigoComportamento }" />
					</div>
					<div class="col m1" id="divNovoBug">
						<button class="btn waves-effect waves-light blue" type="submit" name="action">Novo
						  <i class="material-icons right">send</i>
						</button>

						<input type="hidden" name="controller" value="Bugs" />
						<input type="hidden" name="acao" value="Adicionar" />
 					</div>
				</div>
			</form>
		</section>
		<section>
			<c:if test="${bugsCasoTeste != null }">
			<section class="center">
				<h6>Bug - Casos de Teste</h6>
				<br />
				<c:forEach items="${bugsCasoTeste }" var="caso">
					<form action="${linkServletBug }" method="POST">
						<div class="row">
							<div class="col m2">
								<select id="tipo" name="tipo" class="center">
									<option value="0" ${caso.tipo == '0' ? 'selected' : '' }>INATIVO</option>
									<option value="1" ${caso.tipo == '1' ? 'selected' : '' }>PRONTO</option>
									<option value="2" ${caso.tipo == '2' ? 'selected' : '' }>MELHORIA</option>
									<option value="3" ${caso.tipo == '3' ? 'selected' : '' }>BUG</option>
								</select>
							</div>
							<div class="col m9">
								<textArea name="bug" id="bug" class="materialize-textarea">${caso.descricaoBug }</textArea>
								<input type="hidden" name="codigoBug" id="codigoBug" value="${caso.codigoBug }" />
								<input type="hidden" name="codigoCasoTeste" id="codigoCasoTeste" value="${codigoCasoTeste }" />
								<input type="hidden" name="codigoTeste" id="codigoTeste" value="${codigoTeste }" />
							</div>
							<div class="col m1">
								<button class="btn waves-effect waves-light rounded" type="submit" name="action" onclick="classeProgresso()">Alterar
								  <i class="material-icons right">send</i>
								</button>

								<input type="hidden" name="controller" value="Bugs" />
								<input type="hidden" name="acao" value="Adicionar" />
	  						</div>
						</div>
  					</form>
				</c:forEach>
			</section>
			</c:if>
			<c:if test="${bugsRegra != null }">
			<section class="center">
				<h6>Bug - Regra de Negócio</h6>
				<br />
				<c:forEach items="${bugsRegra }" var="regra">
					<form action="${linkServletBug }" method="POST">
						<div class="row">
							<div class="col m2">
								<select id="tipo" name="tipo" class="center">
								<option value="">Selecione...</option>
								<option value="0" ${regra.tipo == '0' ? 'selected' : '' }>INATIVO</option>
								<option value="1" ${regra.tipo == '1' ? 'selected' : '' }>PRONTO</option>
								<option value="2" ${regra.tipo == '2' ? 'selected' : '' }>MELHORIA</option>
								<option value="3" ${regra.tipo == '3' ? 'selected' : '' }>BUG</option>
							</select>
							</div>
							<div class="col m9">
								<textArea name="bug" id="bug" class="materialize-textarea">${regra.descricaoBug }</textArea>
								<input type="hidden" name="codigoBug" id="codigoBug" value="${regra.codigoBug }" />
								<input type="hidden" name="codigoRegraNegocio" id="codigoRegraNegocio" value="${codigoRegraNegocio }" />
								<input type="hidden" name="codigoTeste" id="codigoTeste" value="${codigoTeste }" />
							</div>
							<div class="col m1">
								<button class="btn waves-effect waves-light" type="submit" name="action">Alterar
								  <i class="material-icons right">send</i>
								</button>

								<input type="hidden" name="controller" value="Bugs" />
								<input type="hidden" name="acao" value="Adicionar" />
	  						</div>
						</div>
					</form>
				</c:forEach>
			</section>
			</c:if>
			<c:if test="${bugsComportamento != null }">
			<section class="center">
				<h6>Bug - Comportamento</h6>
				<br />
				<c:forEach items="${bugsComportamento }" var="comportamento">
					<form action="${linkServletBug }" method="POST">
						<div class="row">
							<div class="col m2">
								<select id="tipo" name="tipo" class="center">
									<option value="0" ${comportamento.tipo == '0' ? 'selected' : '' }>INATIVO</option>
									<option value="1" ${comportamento.tipo == '1' ? 'selected' : '' }>PRONTO</option>
									<option value="2" ${comportamento.tipo == '2' ? 'selected' : '' }>MELHORIA</option>
									<option value="3" ${comportamento.tipo == '3' ? 'selected' : '' }>BUG</option>
								</select>
							</div>
							<div class="col m9">
								<textArea name="bug" id="bug" class="materialize-textarea">${comportamento.descricaoBug }</textArea>
								<input type="hidden" name="codigoBug" id="codigoBug" value="${comportamento.codigoBug }" />
								<input type="hidden" name="codigoComportamento" id="codigoComportamento" value="${codigoComportamento }" />
								<input type="hidden" name="codigoTeste" id="codigoTeste" value="${codigoTeste }" />
							</div>
							<div class="col m1">
								<button class="btn waves-effect waves-light" type="submit" name="action">Alterar
								  <i class="material-icons right">send</i>
								</button>

								<input type="hidden" name="controller" value="Bugs" />
								<input type="hidden" name="acao" value="Adicionar" />
	  						</div>
						</div>
					</form>
				</c:forEach>
			</section>
			</c:if>
		</section>
		<section>
			<form action="${linkServletBug }" method="POST">
				<div class="row">
					<div class="col m2">
						<select id="tipo" name="tipo" class="center">
							<option value="">Selecione...</option>
							<option value="0">INATIVO</option>
							<option value="1">PRONTO</option>
							<option value="2">MELHORIA</option>
							<option value="3" selected="selected">BUG</option>
						</select>
					</div>
					<div class="col m2">
						<input type="hidden" name="codigoBug" id="codigoBug" value="999999" />
						<input type="hidden" name="codigoTeste" id="codigoTeste" value="${codigoTeste }" />

						<input type="hidden" name="codigoCasoTeste" id="codigoCasoTeste" value="${codigoCasoTeste }" />
						<input type="hidden" name="codigoRegraNegocio" id="codigoRegraNegocio" value="${codigoRegraNegocio }" />
						<input type="hidden" name="codigoComportamento" id="codigoComportamento" value="${codigoComportamento }" />
						
						<div id="divBtnAlteraTodos">
							<button class="btn waves-effect waves-light red" id="btnAlteraTodos" type="submit" name="action">Alterar Todos
							  <i class="material-icons right">send</i>
							</button>
						</div>
					</div>
					<div class="col m2">
						<select id="bloqueado" class="center">
							<option value="0" selected="selected">BLOQUEADO</option>
							<option value="1">ATIVO</option>
						</select>

						<input type="hidden" name="controller" value="Bugs" />
						<input type="hidden" name="acao" value="Adicionar" />
 					</div>
				</div>
			</form>
		</section>
	</body>
<c:import url="../menu/imports-js.jsp"></c:import>
<c:import url="scripts-bugs.jsp"></c:import>
</html>