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
        document.querySelectorAll('.evento-titulo').forEach(function(el) {
            const texto = el.getAttribute('data-text');
            fetch('/api/traducir', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({texto: texto, lang: lang})
            })
            .then(res => res.json())
            .then(data => {
                el.textContent = data.traduccion;
            });
        });
        // Traducir descripciones de eventos
        document.querySelectorAll('.evento-descripcion').forEach(function(el) {
            const texto = el.getAttribute('data-text');
            fetch('/api/traducir', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({texto: texto, lang: lang})
            })
            .then(res => res.json())
            .then(data => {
                el.textContent = data.traduccion;
            });
        });
        // Traducción automática para graficoEncuesta.html
        document.querySelectorAll('.grafico-titulo, .grafico-nivel, .grafico-descripcion, .grafico-consejo').forEach(function(el) {
            const texto = el.getAttribute('data-text');
            if (texto) {
                fetch('/api/traducir', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({texto: texto, lang: lang})
                })
                .then(res => res.json())
                .then(data => {
                    el.textContent = data.traduccion;
                });
            }
        });
        document.querySelectorAll('.encuesta-titulo, .encuesta-descripcion').forEach(function(el) {
            const texto = el.getAttribute('data-text');
            if (texto) {
                fetch('/api/traducir', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({texto: texto, lang: lang})
                })
                .then(res => res.json())
                .then(data => {
                    el.textContent = data.traduccion;
                });
            }
        });
    }
});