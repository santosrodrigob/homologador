<script type="text/javascript">

function buttonSend(){

	var blur;
	var change;
	
	var divButtonSend = $('#divButtonSend');
	divButtonSend.hide();
	
	var comboModulo = $('#modulo');
	var feature = $('#descricaoFeature');
	
	blur = validaBlur(feature);
	change = validaChange(comboModulo);

	validate(blur,change,divButtonSend);
	
	feature.on('blur', function(){
		blur = validaBlur(feature);
		change = validaChange(comboModulo);
		validate(blur,change,divButtonSend);
	});

	comboModulo.on('change', function(){
		blur = validaBlur(feature);
		change = validaChange(comboModulo);
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
	
	buttonSend();
});

</script>