document.addEventListener("DOMContentLoaded", function () {
    let idioma = document.getElementById("idioma").value; // Cambia 'const' por 'let'
    const usuarioID = parseInt(document.getElementById("usuarioID").value, 10);
    const comentariosLista = document.getElementById("comentariosLista");

    const idiomaNombres = {
        es: "Español",
        en: "English",
        fr: "Français",
        ar: "العربية"
    };

    function agregarComentario(comentario) {
        const esMio = comentario.usuarioID === usuarioID;
        const div = document.createElement("div");
        div.className = `d-flex ${esMio ? 'justify-content-end' : 'justify-content-start'}`;

        const bubble = document.createElement("div");
        bubble.className = `p-2 rounded ${esMio ? 'text-white' : 'bg-light text-dark'}`;
        bubble.style.maxWidth = "60%";
        bubble.style.wordWrap = "break-word";
        bubble.style.borderRadius = "15px";
        bubble.style.backgroundColor = esMio ? "rgb(140, 207, 240)" : "#f1f1f1";

        bubble.innerHTML = `
            <div><strong>${comentario.nombre}</strong></div>
            <div>${comentario.contenido}</div>
            <div class="text-muted small text-end">${new Date(comentario.fechaHora).toLocaleString()}</div>
        `;

        div.appendChild(bubble);
        comentariosLista.appendChild(div);
        comentariosLista.scrollTop = comentariosLista.scrollHeight;
    }

    let stompClient = null;
    let currentSubscription = null;

    function conectarWebSocket(idioma) {
        if (stompClient && currentSubscription) {
            currentSubscription.unsubscribe();
        }
        currentSubscription = stompClient.subscribe(`/topic/chat/${idioma}`, mensaje => {
            const comentario = JSON.parse(mensaje.body);
            agregarComentario(comentario);
        });
    }

    function cargarMensajes(idioma) {
        comentariosLista.innerHTML = ""; // Limpia mensajes actuales
        fetch(`/api/chat/${idioma}/mensajes`)
            .then(res => res.json())
            .then(mensajes => {
                mensajes.forEach(agregarComentario);
            })
            .catch(err => {
                console.error("Error cargando mensajes previos", err);
            });
    }

    // WebSocket
    const socket = new SockJS("/foro-websocket");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        conectarWebSocket(idioma);
        cargarMensajes(idioma);

        document.getElementById("comentarioForm").addEventListener("submit", function (e) {
            e.preventDefault();
            const contenido = document.getElementById("contenido").value.trim();
            if (!contenido) return;

            stompClient.send(`/app/chat/${idioma}/mensaje`, {}, JSON.stringify({
                usuarioID: usuarioID,
                contenido: contenido
            }));

            document.getElementById("contenido").value = '';
        });

        // Detecta cambio de idioma (ejemplo: click en menú de idioma)
        document.querySelectorAll('.dropdown-menu a.dropdown-item').forEach(item => {
            item.addEventListener('click', function (e) {
                e.preventDefault();
                const newLang = this.textContent.trim().toLowerCase();
                window.location.href = `/chat/${newLang}?lang=${newLang}`;
            });
        });
    });
});