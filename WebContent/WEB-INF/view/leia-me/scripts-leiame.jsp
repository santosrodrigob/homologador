<script src="assets/scripts/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="https://cdn.datatables.net/rowgroup/1.0.2/js/dataTables.rowGroup.min.js"></script>

<script type="text/javascript">

	$('form#formLeiaMe select#modulo').on('change', function() 
	{
		combosConsultarFeatures();
	});
	
	function combosConsultarFeatures() {

		$('form#formLeiaMe select#feature').empty();
		var modulo = $('form#formLeiaMe select#modulo').val();

		dados = {modulo: modulo};
		
		$.ajax({
            url : "/homologador/ajax",
            type : "POST",
            data : dados,
            dataType : "json"
    	}).done(function(data) {

    		$('form#formLeiaMe select#feature').append('<option value="0">Selecione...</option>');

			$.each(data.features, function(key, value)
			{
				$('form#formLeiaMe select#feature').append('<option value="'+ value.codigoFeature +'">'+ value.descricaoFeature +'</option>');
			});
			$('select#feature').formSelect();

    	}).fail(function() {
    		console.log("Falhou");
    	}).always(function() {
    		console.log("Finish");
    	});
	}

	
	function clear() {
		
		var comboModulo = $('form#formLeiaMe #modulo');
		var versao = $('form#formLeiaMe #versao');
		var solicitante = $('form#formLeiaMe #solicitante');
	
		comboModulo.on('change', function(){
			versao.val('');
			solicitante.val('');
		});
	}

	function buttonSend(){
	
		var blur;
		var change;
		
		var divButtonSend = $('form#formLeiaMe #divButtonSend');
		divButtonSend.hide();
		
		var comboModulo = $('form#formLeiaMe #modulo');
		var versao = $('form#formLeiaMe #versao');
		var leia = $('form#formLeiaMe #descricaoLeiaMe');
		
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
		
		open();
		clear();
		buttonSend();
	});

</script>