package edu.mondragon.webengl.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.mondragon.webengl.domain.foro.model.ComentarioDTO;
import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.service.ComentarioForoService;

@RestController
@RequestMapping("/api/foro")
public class ComentarioRestController {

    @Autowired
    private ComentarioForoService comentarioService;

    @GetMapping("/{hiloID}/comentarios")
    public List<ComentarioDTO> obtenerComentarios(@PathVariable Integer hiloID) {
        List<ComentarioForo> comentarios = comentarioService.obtenerComentariosPorHilo(hiloID);

        return comentarios.stream().map(c -> {
            ComentarioDTO dto = new ComentarioDTO();
            dto.setUsuarioID(c.getUsuario().getUsuarioID());
            dto.setNombre(c.getUsuario().getNombre()); // <-- Añade esta línea
            dto.setContenido(c.getContenido());
            dto.setFechaHora(c.getFechaHora());
            return dto;
        }).collect(Collectors.toList());
    }
}