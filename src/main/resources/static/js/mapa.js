function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        center: recursos.length > 0 ? {lat: recursos[0].latitud, lng: recursos[0].longitud} : {lat: 43.263, lng: -2.935},
        streetViewControl: true
    });

    var infoWindow = new google.maps.InfoWindow();

    recursos.forEach(function(recurso) {
        var marker = new google.maps.Marker({
            position: {lat: recurso.latitud, lng: recurso.longitud},
            map: map,
            title: recurso.nombre
        });

        var content = `
            <strong>${recurso.nombre}</strong><br>
            <b>Dirección:</b> ${recurso.direccion}<br>
            <b>Teléfono:</b> ${recurso.telefono}<br>
            <b>Horario:</b> ${recurso.horaAbierto} - ${recurso.horaCerrado}<br>
            <b>Descripción:</b> ${recurso.descripcion}
        `;

        marker.addListener('click', function() {
            infoWindow.setContent(content);
            infoWindow.open(map, marker);
        });
    });
}
window.initMap = initMap;