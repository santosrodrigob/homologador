<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletLeiaMe"></c:url>
<html>
	<head>
		<c:import url="../menu/menu.jsp"></c:import>
	</head>
	<body>
		<div>
			<div class="stratButtons">
				<a class="btn-floating btn-large waves-effect waves-light blue darken-1" href="entrada?controller=LeiaMe&acao=Listar">
					<i class="material-icons">featured_play_list</i>
				</a>
				<a class="btn-floating btn-large waves-effect waves-light red" href="entrada?controller=LeiaMe&acao=Open">
					<i class="material-icons">add</i>
				</a>
			</div>
			
			<h4 class="center">Alteração de Leia-Me</h4>
		</div>

		<div class="divider"></div>
		<div class="container">
			<form action="${linkServletLeiaMe }" method="POST" id="formLeiaMe">
			<section>
				<div class="row" id="rowModulos">
					<div class="col m1">
						<h6>Código: </h6>
						<input id="codigoLeiaMe" name="codigoLeiaMe" type="number" min="1" value="${leiaMe.codigoLeiaMe }" readonly="readonly"/>
					</div>
					<div class="col m5">
						<h6>Modulo</h6>
						<select name="modulo" id="modulo">
							<c:forEach items="${modulos }" var="modulo">
								<option value="${modulo.codigoModulo }" ${modulo.codigoModulo == leiaMe.codigoModulo ? 'selected' : '' }>${modulo.descricaoModulo }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col m5">
						<h6>Feature</h6>
						<select name="feature" id="feature">
							<c:forEach items="${features }" var="feature">
								<option value="${feature.codigoFeature }" ${feature.codigoFeature == leiaMe.codigoFeature ? 'selected' : 'disabled' }>${feature.descricaoFeature }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row" id="rowModulos">
					<div class="col m4">
						<h6>Solicitante: </h6>
						<input id="solicitante" name="solicitante" type="text" min="1" max="50" value="${leiaMe.solicitante } "/>
					</div>
					<div class="col m4">
						<h6>Versão: </h6>
						<input id="versao" name="versao" type="text" min="1" max="15" value="${leiaMe.versao } "/>
					</div>
					<div class="col m4">
						<h6>Inativo</h6>
						<select name="inativo" id="inativo">
							<option value="0" ${leiaMe.inativo == 0 ? 'selected' : '' }>ATIVO</option>
							<option value="1" ${leiaMe.inativo == 1 ? 'selected' : '' }>INATIVO</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col m10">
						<h6>Descrição: </h6>
						<textarea name="descricaoLeiaMe" id="descricaoLeiaMe" class="materialize-textarea">${leiaMe.descricaoLeiaMe }</textarea>

						<input name="modulo" type="hidden" value="${leiaMe.codigoLeiaMe }" readonly="readonly"/>
					</div>
					<div class="col m2" id="divButtonSend">
						<button class="btn waves-effect waves-light black"  id="btnEnviar" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
				<div>
					<input type="hidden" name="acao" value="Adicionar">
					<input type="hidden" name="controller" value="LeiaMe">
				</div>
			</section>
			</form>
			<section>
				<div class="row" id="rowModulos">
					<div class="col m4">
						<h6>Data Criação: </h6>
						<input class="dadosAlteracao" type="text" value="${leiaMe.dataCriacaoFormatada } "/>
					</div>
					<div class="col m4">
						<h6>Data Alteração: </h6>
						<input class="dadosAlteracao" type="text" value="${leiaMe.dataAlteracaoFormatada } "/>
					</div>
					<div class="col m4">
						<h6>Usuario Criação/Alteração: </h6>
						<input class="dadosAlteracao" type="text" value="${leiaMe.usuarioAlteracao } " />
					</div>
				</div>
			</section>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
	<c:import url="scripts-leiame.jsp"></c:import>
</html>