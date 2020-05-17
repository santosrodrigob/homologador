//Meu Drop Down - Menu

	$(".dropdown-trigger").dropdown();

//Barra de progresso

	function classeProgresso() {
		
		$('#barraProgresso').addClass('progress');			

		setInterval(function() {
			$('#barraProgresso').removeClass('progress');			
		}, 3000);
	}
	
//Get Combos

	$('form#novo select#modulo').on('change', function() 
	{
		combosConsultarFeatures();
	});

	$('form#novo select#feature').on('change', function() 
	{
		combosConsultarTipos();
	});

	function combosConsultarFeatures() {

		$('form#novo select#feature').empty();
		$('form#novo select#tipo').empty();
		var modulo = $('form#novo select#modulo').val();

		dados = { modulo: modulo};
		
		$.ajax({
            url : "/homologador/ajax",
            type : "POST",
            data : dados,
            dataType : "json"
    	}).done(function(data) {

    		$('form#novo select#feature').append('<option value="0">Selecione...</option>');
    		$('form#novo select#tipo').append('<option value="0">Selecione...</option>');

			$.each(data.features, function(key, value)
			{
				$('form#novo select#feature').append('<option value="'+ value.codigoFeature +'">'+ value.descricaoFeature +'</option>');				
			});
			$('select#feature').formSelect();
			$('select#tipo').formSelect();

    	}).fail(function() {
    		console.log("Falhou");
    	}).always(function() {
    		console.log("Finish");
    	});
	}
	
	function combosConsultarTipos() {

		$('form#novo select#tipo').empty();
		var modulo = $('form#novo select#modulo').val();
		var feature = $('form#novo select#feature').val();

		dados = { modulo: modulo, feature: feature };
		
		$.ajax({
            url : "/homologador/ajax",
            type : "POST",
            data : dados,
            dataType : "json"
    	}).done(function(data) {
    		
			$('form#novo select#tipo').append('<option value="0">Selecione...</option>');

			$.each(data.tipos, function(key, value)
			{
				$('form#novo select#tipo').append('<option value="'+ value.codigoTipo +'">'+ value.descricaoTipo +'</option>');
			});
			$('select#tipo').formSelect();

    	}).fail(function() {
    		console.log("Ajax falhou");
    	}).always(function() {
    		console.log("Ajax finalizou");
    	});
	}

	function filtrar() {

		var filtro = document.querySelector('#pesquisa');

		filtro.addEventListener('input', function(){
			
			var testes = document.querySelectorAll('.testes');
			if(this.value.length > 0)
			{
				testes.forEach(function(teste){
					var codigo = teste.querySelector('.codigo').textContent;
					var descricao = teste.querySelector('.descricao').textContent;
					var feature = teste.querySelector('.testeFeature').textContent;
					var tipos = teste.querySelector('.tipos').textContent;
					var inativo = teste.querySelector('.inativo').textContent;

					var expressao = new RegExp(filtro.value,'i');

					if(!expressao.test(codigo) && !expressao.test(descricao) && !expressao.test(feature)
							&& !expressao.test(tipos) && !expressao.test(inativo))
					{
						teste.classList.add('remove-teste');	
					}
					else
					{
						teste.classList.remove('remove-teste');
					}
				});
			}
			else
			{
				for(var i = 0; i < testes.length; i++) 
				{
					var teste = testes[i];
					teste.classList.remove('remove-teste');			
				}
			}
		});
	}

	function clearMessage() {

		setTimeout(function() {
			$('#message').text('');
		}, 6000);
	}
	
	$('#pesquisa').click(function(){
		filtrar();
	});
	
	$(document).ready(function(){		

		$('select').formSelect();
		clearMessage();
	});
