(function () {
    var selectores = document.querySelectorAll('.selector-tipo');
    var foodTruck = document.getElementById('form-foodtruck');
    var puesto = document.getElementById('form-puesto');
    var inicial = document.getElementById('registro').dataset.tipo || 'foodtruck';

    function mostrar(tipo) {
        foodTruck.style.display = tipo === 'foodtruck' ? 'block' : 'none';
        puesto.style.display = tipo === 'puesto' ? 'block' : 'none';
        selectores.forEach(function (s) { s.value = tipo; });
    }

    selectores.forEach(function (s) {
        s.addEventListener('change', function () { mostrar(s.value); });
    });

    mostrar(inicial);
})();