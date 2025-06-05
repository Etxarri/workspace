

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