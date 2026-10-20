var tablaFestivales = document.getElementById('tablaFestivales');

if (tablaFestivales) {
  var filas = tablaFestivales.querySelectorAll('tbody tr');

  filas.forEach(function (fila) {
    fila.addEventListener('click', function () {
      window.location = fila.dataset.href + '#detalle';
    });
  });

  document.getElementById('buscador').addEventListener('input', function () {
    var texto = this.value.toLowerCase();
    var coincidencias = 0;

    filas.forEach(function (fila) {
      var nombre = fila.cells[0].textContent.toLowerCase();
      var coincide = nombre.includes(texto);
      fila.style.display = coincide ? '' : 'none';
      if (coincide) coincidencias++;
    });

    document.getElementById('sinResultados').style.display = coincidencias === 0 ? '' : 'none';

    var detalle = document.getElementById('detalle');
    if (detalle) {
      detalle.style.display = texto === '' ? '' : 'none';
    }
  });
}