<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletTeste"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div class="row">
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=Teste&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=Teste&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			<h4 class="center">Alteração de Teste</h4>
		</div>
	
		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletTeste }" method="POST">
			<section>
				<div class="row">
					<div class="col m4">
						<h6>Codigo de Teste</h6>
						<input type="number" name="codigoTeste" name="codigoTeste" value="${teste.codigoTeste }" readonly="readonly">
						<input type="hidden" name="modulo" name="modulo" value="${teste.codigoModulo }" readonly="readonly">
						<input type="hidden" name="feature" name="feature" value="${teste.codigoFeature }" readonly="readonly">
						<input type="hidden" name="tipo" name="tipo" value="${teste.codigoTipo }" readonly="readonly">
					</div>
				</div>
				<div class="row">
					<div class="col m4">
						<h6>Modulo</h6>
						<select name="modulo" id="modulo" disabled="disabled">
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }" ${modulo.codigoModulo == teste.codigoModulo ? 'selected' : '0' } >${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Feature</h6>
						<select name="feature" id="feature" disabled="disabled">
							<c:forEach items="${features }" var="feature">
								<option value="${feature.codigoFeature }" ${feature.codigoFeature == teste.codigoFeature ? 'selected' : '0' } >${feature.descricaoFeature }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m4">				
						<h6>Tipo</h6>
						<select name="tipo" id="tipo" disabled="disabled">
							<c:forEach items="${tipos }" var="tipo">
								<option value="${tipo.codigoTipo }" ${tipo.codigoTipo == teste.codigoTipo ? 'selected' : '0' } >${tipo.descricaoTipo }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m12">
						<h6>Observação</h6>
						<textarea name="observacao" id="observacao" class="materialize-textarea">${teste.observacao }</textarea>
					</div>
						<div class="col m12">
						<h6>Conclusão</h6>
						<textarea name="conclusao" id="conclusao" class="materialize-textarea">${teste.conclusao }</textarea>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<h6><b>Situação</b></h6>
					<div class="col m4">
						<h6>Caso de Teste</h6>
						<select name="situacaoCaso" id="situacaoCaso">
							<option value="">Selecione</option>
							<option value="0" ${teste.situacaoCasoTeste == 0 ? 'selected' : '0' } >PENDENTE</option>
							<option value="1" ${teste.situacaoCasoTeste == 1 ? 'selected' : '1' } >HOMOLOGADO</option>
						</select>
					</div>
					<div class="col m4">
						<h6>Regra de Negócio</h6>
						<select name="situacaoRegra" id="situacaoRegra">
							<option value="">Selecione</option>
							<option value="0" ${teste.situacaoRegra == 0 ? 'selected' : '0' } >PENDENTE</option>
							<option value="1" ${teste.situacaoRegra == 1 ? 'selected' : '1' } >HOMOLOGADO</option>
						</select>
					</div>
					<div class="col m4">
						<h6>Comportamento</h6>
						<select name="situacaoComportamento" id="situacaoComportamento">
							<option value="">Selecione</option>
							<option value="0" ${teste.situacaoComportamento == 0 ? 'selected' : '0' } >PENDENTE</option>
							<option value="1" ${teste.situacaoComportamento == 1 ? 'selected' : '1' } >HOMOLOGADO</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col m10">
						<select name="inativo" id="inativo">
							<option value="">Selecione</option>
							<option value="0" ${teste.inativo == 0 ? 'selected' : '0' } >ATIVO</option>
							<option value="1" ${teste.inativo == 1 ? 'selected' : '1' } >INATIVO</option>
						</select>
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
			</section>
				<div>
	 				<input type="hidden" name="acao" value="Adicionar">
	 				<input type="hidden" name="controller" value="Teste">
				</div>
			</form>
		</div>
	</body>
<c:import url="../menu/imports-js.jsp"></c:import>
<c:import url="scripts-teste.jsp"></c:import>
</html>