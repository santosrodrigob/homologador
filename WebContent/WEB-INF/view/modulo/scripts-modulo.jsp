<script type="text/javascript">

function buttonSend(){

	var divButtonSend = $('#divButtonSend');
	divButtonSend.hide();
	
	var modulo = $('#descricaoModulo');

	validaBlur(divButtonSend,modulo);

	modulo.on('blur', function(){
		validaBlur(divButtonSend,modulo);
	});
}

function validaBlur(div,tipo) {

	if(tipo.val().length > 1) {
		div.show();
	} else {
		div.hide();
	}
}

function validaChange(div,tipo) {

	if(tipo.val() > 0) {
		div.show();
	} else {
		div.hide();
	}
}

$(document).ready(function(){
	
	buttonSend();
});

</script>