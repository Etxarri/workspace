package edu.mondragon.webengl.domain.user.service;

import edu.mondragon.webengl.domain.user.model.Encuesta;
import edu.mondragon.webengl.domain.user.repository.EncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EncuestaService
{
    @Autowired
    private EncuestaRepository encuestaRepository;

    public void guardarEncuesta(Encuesta encuesta)
    {
        encuestaRepository.save(encuesta);
    }
}