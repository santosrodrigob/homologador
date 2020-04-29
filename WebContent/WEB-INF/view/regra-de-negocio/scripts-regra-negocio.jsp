<script type="text/javascript">

function buttonSend(){

	var blur;
	var change;
	
	var divButtonSend = $('#divButtonSend');
	divButtonSend.hide();
	
	var regra = $('#descricaoRegra');
	var comboModulo = $('#modulo');
	var comboFeature = $('#feature');
	var comboTipo = $('#tipo');
	
	blur = validaBlur(regra);
	change = validaChanges(comboModulo,comboFeature,comboTipo);
	validate(blur,change,divButtonSend);
		
	regra.on('focus', function(){
		divButtonSend.show();
	});

	regra.on('blur', function(){
		blur = validaBlur(regra);
		change = validaChanges(comboModulo,comboFeature,comboTipo);
		validate(blur,change,divButtonSend);
	});

	comboModulo.on('change', function(){
		blur = validaBlur(regra);
		change = validaChanges(comboModulo,comboFeature,comboTipo);
		validate(blur,change,divButtonSend);
	});

	comboFeature.on('change', function(){
		blur = validaBlur(regra);
		change = validaChanges(comboModulo,comboFeature,comboTipo);
		validate(blur,change,divButtonSend);
	});

	comboTipo.on('change', function(){
		blur = validaBlur(regra);
		change = validaChanges(comboModulo,comboFeature,comboTipo);
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

function validaChanges(tipo1, tipo2, tipo3) {

	isValid = false;
	
	if(tipo1.val() > 0) {
		isValid = true;
	} 
	
	if(isValid && tipo2.val() > 0) {
		isValid = true;
	} else {
		isValid = false;
	}
	
	if(isValid && tipo3.val() > 0) {
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