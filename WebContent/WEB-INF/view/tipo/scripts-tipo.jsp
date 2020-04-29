<script type="text/javascript">

function buttonSend(){

	var blur;
	var change;
	
	var divButtonSend = $('#divButtonSend');
	divButtonSend.hide();
	
	var tipo = $('#descricaoTipo');
	var comboModulo = $('#modulo');
	var comboFeature = $('#feature');
	
	blur = validaBlur(tipo);
	change = validaChanges(comboModulo,comboFeature);
	validate(blur,change,divButtonSend);
	
	tipo.on('blur', function(){
		blur = validaBlur(tipo);
		change = validaChanges(comboModulo,comboFeature);
		validate(blur,change,divButtonSend);
	});

	comboModulo.on('change', function(){
		blur = validaBlur(tipo);
		change = validaChanges(comboModulo,comboFeature);
		validate(blur,change,divButtonSend);
	});

	comboFeature.on('change', function(){
		blur = validaBlur(tipo);
		change = validaChanges(comboModulo,comboFeature);
		validate(blur,change,divButtonSend);
	});
}

function validaBlur(tipo) {

	if(tipo.val().length > 1) {
		return true;
	} else {
		return false;
	}
}

function validaChanges(tipo1, tipo2) {

	isValid = false;
	
	if(tipo1.val() > 0) {
		isValid = true;
	} 
	
	if(isValid && tipo2.val() > 0) {
		isValid = true;
	} else {
		isValid = false;
	}
	return isValid;
}

function validate(blur,change,div) {
	
	if(blur && change) {
		div.show();
	} else {
		div.hide();
	}
}

$(document).ready(function(){
	
	buttonSend();
});

</script>