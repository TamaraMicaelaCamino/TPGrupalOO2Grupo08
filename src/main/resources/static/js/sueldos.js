document.addEventListener('DOMContentLoaded', function () {
    const select = document.getElementById('idUnidadDeVenta');
    const btnConsultar = document.getElementById('btn-consultar');
    const formLiquidar = document.getElementById('form-liquidar');

    if (!select) return;

    select.addEventListener('change', function () {
        // Se vuelve a mostrar Consultar para ver la unidad nueva
        if (btnConsultar) btnConsultar.classList.remove('d-none');

        // Se oculta Liquidar hasta volver a consultar
        if (formLiquidar) formLiquidar.classList.add('d-none');
    });
});