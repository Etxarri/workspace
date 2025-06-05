const urlParams = new URLSearchParams(window.location.search);
const hiloID = urlParams.get('hiloID');
document.getElementById('hiloID').textContent = hiloID;

const comentariosLista = document.getElementById('comentariosLista');

function agregarComentario(comentario) {
    const li = document.createElement('li');
    li.className = 'list-group-item';
    li.innerHTML = `<strong>Usuario ${comentario.usuarioID}:</strong> ${comentario.contenido} <br><small>${new Date(comentario.fechaHora).toLocaleString()}</small>`;
    comentariosLista.appendChild(li);
}
/*
// Obtener historial
fetch(`/api/foro/${hiloID}/comentarios`)
    .then(res => res.json())
    .then(comentarios => comentarios.forEach(agregarComentario));
*/
// Conexión WebSocket
const socket = new SockJS('/foro-websocket');
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
    stompClient.subscribe(`/topic/foro/${hiloID}`, (mensaje) => {
        const comentario = JSON.parse(mensaje.body);
        agregarComentario(comentario);
    });
});

// Envío de comentario
document.getElementById('comentarioForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const contenido = document.getElementById('contenido').value;
    const usuarioID = document.getElementById('usuarioID').value;

    stompClient.send(`/app/foro/${hiloID}/comentario`, {}, JSON.stringify({
        usuarioID: parseInt(usuarioID),
        contenido: contenido
    }));

    document.getElementById('contenido').value = '';
});
