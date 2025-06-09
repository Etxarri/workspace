package edu.mondragon.webengl.domain.foro.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.repository.ComentarioForoRepository;

@Service
public class ComentarioForoService {

    @Autowired
    private ComentarioForoRepository comentarioRepo;

    public ComentarioForo guardarComentario(ComentarioForo comentario) {
        comentario.setFechaHora(LocalDateTime.now());
        return comentarioRepo.save(comentario);
    }

    public List<ComentarioForo> obtenerComentariosPorHilo(Integer hiloID) {
        return comentarioRepo.findByHilo_HiloIDOrderByFechaHoraAsc(hiloID);
    }

    public List<ComentarioForo> obtenerComentariosOrdenadosPorVotos(int hiloID) {
    return comentarioRepo.findByHilo_HiloIDOrderByVotosDescFechaHoraAsc(hiloID);
    }

    public ComentarioForo actualizarVoto(int comentarioID, boolean positivo) {
    ComentarioForo comentario = comentarioRepo.findById(comentarioID).orElseThrow();

    int nuevosVotos = comentario.getVotos() + (positivo ? 1 : -1);
    if (nuevosVotos < 0) nuevosVotos = 0;

    comentario.setVotos(nuevosVotos);
    return comentarioRepo.save(comentario);
    }
}