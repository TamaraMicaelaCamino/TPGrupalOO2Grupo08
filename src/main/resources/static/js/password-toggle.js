document.addEventListener('click', function (e) {
    var boton = e.target.closest('.toggle-password');
    if (!boton) return;

    var input = boton.parentElement.querySelector('input');
    var icono = boton.querySelector('i');
    var estabaVisible = input.type === 'text';

    input.type = estabaVisible ? 'password' : 'text';
    icono.className = estabaVisible ? 'bi bi-eye' : 'bi bi-eye-slash';
});