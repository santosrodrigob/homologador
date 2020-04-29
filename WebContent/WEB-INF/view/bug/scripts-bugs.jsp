	<script type="text/javascript">
	
		function buttonSend(){
		
			var blur;
			var change = true;
			
			var divButtonSend = $('#divNovoBug');
			divButtonSend.hide();

			var bug = $('#bugNovo');

			blur = validaBlur(bug);
			validate(blur,change,divButtonSend);

			bug.on('focus', function(){
				divButtonSend.show();
			});
		
			bug.on('blur', function(){
				blur = validaBlur(bug);
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
		
		function bloqueaiaBotao() {
			
			var btnAlteraTodos = $('#btnAlteraTodos');
			btnAlteraTodos.addClass('disabled');
			
			bloqueado =  $('#bloqueado');
			console.log(bloqueado.val());
			
			bloqueado.on('change', function(){
				if(bloqueado.val() == 1) {
					btnAlteraTodos.removeClass('disabled');					
				} else {
					btnAlteraTodos.addClass('disabled');					
				}
			});
		}
		
		$(document).ready(function(){
			buttonSend();
			bloqueaiaBotao();
		});
	
	</script>