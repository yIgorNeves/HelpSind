$('#modalExcluir').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget)
	  var idObj = button.data('idobj') // Lê info dos atributos data-*
	  var obs = button.data('obs')
	  var action = button.data('modal-action');
	  var modal = $(this)
	  modal.find('form #idObj').val(idObj)
	  modal.find('.modal-body span').text(obs)
	  if(action != null){
		  modal.find('form').attr('action',action);  
	  }	  
	});