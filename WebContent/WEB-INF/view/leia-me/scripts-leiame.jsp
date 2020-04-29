<script type="text/javascript">

function clear() {
	
	var comboModulo = $('form#formLeiaMe #modulo');
	var versao = $('form#formLeiaMe #versao');

	comboModulo.on('change', function(){
		versao.val('');
	});
}

function buttonSend(){

	var blur;
	var change;
	
	var divButtonSend = $('#divButtonSend');
	divButtonSend.hide();
	
	var comboModulo = $('#modulo');
	var versao = $('#versao');
	var leia = $('#descricaoLeiaMe');
	
	blur = validaBlurs(versao, leia);
	change = validaChange(comboModulo);

	validate(blur,change,divButtonSend);

	leia.on('focus', function(){
		divButtonSend.show();
	});
	
	leia.on('blur', function(){
		blur = validaBlurs(leia, versao);
		change = validaChange(comboModulo);
		validate(blur,change,divButtonSend);
	});

	versao.on('blur', function(){
		blur = validaBlurs(leia, versao);
		change = validaChange(comboModulo);
		validate(blur,change,divButtonSend);
	});

	comboModulo.on('change', function(){
		blur = validaBlurs(leia, versao);
		change = validaChange(comboModulo);
		validate(blur,change,divButtonSend);
	});
}

function validaBlurs(tipo1, tipo2) {

	isValid = false;
	
	if(tipo1.val().length > 1) {
		isValid = true;
	} else {
		isValid = false;
	}
	if(isValid && tipo2.val().length > 1) {
		isValid = true;
	} else {
		isValid = false;
	}
	return isValid;
}

function validaChange(tipo) {

	if(tipo.val() > 0) {
		return true;
	} else {
		return false;
	}
}

function validate(blur,change,div) {
	
	if(blur && change) {
		div.show();
	} else {
		div.hide();
	}
}

$(document).ready(function(){
	
	clear();
	buttonSend();
});

</script>