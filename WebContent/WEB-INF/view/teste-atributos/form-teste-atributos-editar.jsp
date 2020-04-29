<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletTeste"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div class="divider"></div>
		<div class="container">
			<h4 class="center">Alteração de Teste Atributo</h4>
	
			<form action="${linkServletTeste }" method="POST">
			<section>
				<div class="row">
					<div class="col m6">
						<h6 class="center">Codigo de Teste</h6>
						<input class="center" type="number" name="codigoTeste" name="codigoTeste" value="${codigoTeste }" readonly="readonly">
					</div>
					<div class="col m6 center">
						<h6 class="center">Código</h6>
						<input class="center" type="number" name="codigo" name="codigo" 
						value="${testeCasoTeste.codigoCasoTeste != null ? testeCasoTeste.codigoCasoTeste : 
						testeRegra.codigoRegra != null ? testeRegra.codigoRegra : 
						testeComportamento.codigoComportamento != null ? testeComportamento.codigoComportamento : '' }" readonly="readonly">
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m12">
						<h6>Observação</h6>
						<textarea name="observacao" id="observacao" class="materialize-textarea">${testeCasoTeste.observacao != null ? testeCasoTeste.observacao : 
						testeRegra.observacao != null ? testeRegra.observacao : 
						testeComportamento.observacao != null ? testeComportamento.observacao : '' }</textarea>
					</div>
						<div class="col m12">
						<h6>Conclusão</h6>
						<textarea name="conclusao" id="conclusao" class="materialize-textarea">${testeCasoTeste.conclusao != null ? testeCasoTeste.conclusao : 
						testeRegra.conclusao != null ? testeRegra.conclusao : 
						testeComportamento.conclusao != null ? testeComportamento.conclusao : '' }</textarea>
					</div>
				</div>
			</section>
			<section>
				<div class="row">
					<div class="col m6">
						<h6>Situação do Teste</h6>
						<select name="situacao" id="situacao">
							<option value="">Selecione</option>
							<option value="0" ${testeCasoTeste.situacao == 0 ? 'selected' : 
												testeRegra.situacao == 0 ? 'selected' : 
												testeComportamento.situacao == 0 ? 'selected' : 
												'' } >PENDENTE</option>
							<option value="1" ${testeCasoTeste.situacao == 1 ? 'selected' : 
												testeRegra.situacao == 1 ? 'selected' : 
												testeComportamento.situacao == 1 ? 'selected' : 
												'' } >HOMOLOGADO</option>
						</select>
					</div>
					<div class="col m5">
						<h6>Ativo/Inativo</h6>
						<select name="inativo" id="inativo">
							<option value="">Selecione</option>
							<option value="0" ${testeCasoTeste.inativo == 0 ? 'selected' : 
												testeRegra.inativo == 0 ? 'selected' : 
												testeComportamento.inativo == 0 ? 'selected' : 
												'' } >ATIVO</option>
							<option value="1" ${testeCasoTeste.inativo == 1 ? 'selected' : 
												testeRegra.inativo == 1 ? 'selected' : 
												testeComportamento.inativo == 1 ? 'selected' : 
												'' } >INATIVO</option>
						</select>
					</div>
					<div class="col m1">
						<input type="submit" id="btnEnviar" value="Enviar"/>
					</div>
				</div>
			</section>
				<div>
					<input type="hidden" name="codigoCasoTeste" name="codigoCasoTeste" value="${testeCasoTeste.codigoCasoTeste }" />
					<input type="hidden" name="codigoRegraNegocio" name="codigoRegraNegocio" value="${testeRegra.codigoRegra }" />
					<input type="hidden" name="codigoComportamento" name="codigoComportamento" value="${testeComportamento.codigoComportamento }" />
	 				<input type="hidden" name="acao" value="Adicionar">
	 				<input type="hidden" name="controller" value="TesteAtributos">
				</div>
			</form>
		</div>
	</body>
<c:import url="../menu/imports-js.jsp"></c:import>
</html>