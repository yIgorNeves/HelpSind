$('#modalExcluir').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget)
	var idObj = button.data('idobj') // Lê info dos atributos data-*
	var obs = button.data('obs')
	var action = button.data('modal-action');
	var modal = $(this)
	modal.find('form #idObj').val(idObj)
	modal.find('.modal-body span').text(obs)
	if (action != null) {
		modal.find('form').attr('action', action);
	}
});

const setDay = () => {
	const day = document.getElementById('day');
	if (!day) {
		return;
	}

	const now = new Intl.DateTimeFormat('pt-BR', { dateStyle: 'full' }).format(new Date());
	day.textContent = now;
}

setDay();

function popup(data) {
	var myWindow = window.open('', '_blank');
	myWindow.document.write('<html><head><title>Relatório</title>');
	myWindow.document.write('</head><body >');
	myWindow.document.write('<div id="printable" class="table-responsive mt-1">'+ data + '</div>');
	myWindow.document.write('</body></html>');
	myWindow.document.close();

	setTimeout(() => {
		myWindow.focus();
		myWindow.print();
		myWindow.close();
	})

}

function print() {
	var element = document.getElementById('printable').innerHTML;
	popup(element);
}


