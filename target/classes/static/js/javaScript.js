window.onload = function () {
    const tipoSelect = document.getElementById('tipo');
    tipoSelect.addEventListener('change', function () {registrarEventosTipoUsuario(tipoSelect.value);});

};

function registrarEventosTipoUsuario(tipo) {
    // Oculta todos los campos primero
    document.querySelectorAll('.voluntario').forEach(el => el.classList.add('borrar'));
    document.querySelectorAll('.recienllegado').forEach(el => el.classList.add('borrar'));

    // Muestra solo los del tipo seleccionado
    if (tipo === 'voluntario') {
        document.querySelectorAll('.voluntario').forEach(el => el.classList.remove('borrar'));
    } else if (tipo === 'recienllegado') {
        document.querySelectorAll('.recienllegado').forEach(el => el.classList.remove('borrar'));
    }
}

 