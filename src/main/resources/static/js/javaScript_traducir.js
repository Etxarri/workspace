

function traducirPregunta(btn, texto, lang) {
    const div = btn.closest('.traduccion-container').querySelector('.traduccion');
    div.textContent = 'Traduciendo...';
    fetch('/api/traducir', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({texto: texto, lang: lang})
    })
    .then(res => res.json())
    .then(data => {
        div.textContent = data.traduccion;
    })
    .catch(() => {
        div.textContent = 'Error al traducir';
    });
}


// NUEVO: Traducción automática de todas las preguntas según el lang de la URL
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const lang = urlParams.get('lang');
    if (lang && lang !== 'es') {
        // Selecciona todos los elementos de pregunta (ajusta el selector según tu HTML)
        document.querySelectorAll('.list-group-item .pregunta-original').forEach(function(span) {
            const texto = span.textContent;
            fetch('/api/traducir', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({texto: texto, lang: lang})
            })
            .then(res => res.json())
            .then(data => {
                span.textContent = data.traduccion;
            });
        });
    }
});