<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/entrada" var="linkServletEntradaLogin"></c:url>
<html>
	<head>
		<c:import url="../menu/menu-login.jsp"></c:import>
	</head>
	<body>
		<div class="container right">
			<form action="${linkServletEntradaLogin }" method="POST">
				<div class="row center">
					<div class="col m-6">
						<h6>Login: </h6>
						<input type="text" name="login"/>
					</div>
				</div>
				<div class="row center">
					<div class="col m-6">
						<h6>Senha: </h6>
						<input type="password" name="senha" />
					</div>
				</div>
				<div class="row">
					<div class="col m-6">
						<button class="btn waves-effect waves-light black center" type="submit" name="action">Enviar
						    <i class="material-icons right">send</i>
						</button>
					</div>
			
					<input type="hidden" name="acao" value="Adicionar"/>
					<input type="hidden" name="controller" value="Login"/>
				</div>
				<div class="row">
					<p><strong>${message }</strong></p>
				</div>
			</form>
		</div>
	</body>
	<c:import url="../menu/imports-js.jsp"></c:import>
</html>
