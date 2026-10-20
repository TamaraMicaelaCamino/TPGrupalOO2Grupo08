var selector = document.getElementById('idFestival');
var campoX = document.getElementById('x');
var aviso = document.getElementById('avisoMax');

function ajustarMaximo() {
  var opcion = selector.options[selector.selectedIndex];
  var max = parseInt(opcion.getAttribute('data-max'));

  if (isNaN(max)) {
    campoX.removeAttribute('max');
    aviso.textContent = '';
    return;
  }

  campoX.max = Math.max(max, 1);
  if (parseInt(campoX.value) > max) {
    campoX.value = Math.max(max, 1);
  }
  aviso.textContent = max === 0
      ? 'Este festival no tiene unidades de venta'
      : 'Este festival tiene ' + max + ' unidades de venta';
}

selector.addEventListener('change', ajustarMaximo);
campoX.addEventListener('input', ajustarMaximo);
ajustarMaximo();