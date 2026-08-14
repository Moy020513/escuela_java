package com.moises.escuela.repositories;


import com.moises.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    boolean existsByInscripcionId(Long idInscripcion);
    boolean existsByInscripcionIdAndIdNot(Long idInscripcion, Long idCalificaion);
}
