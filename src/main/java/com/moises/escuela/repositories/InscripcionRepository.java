package com.moises.escuela.repositories;

import com.moises.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    boolean existsByAlumnoId(Long idAlumno);
    boolean existsByGrupoId(Long idGrupo);
    boolean existsByAlumnoIdAndGrupoId(Long idAlumno, Long idGrupo);
    boolean existsByAlumnoIdAndGrupoIdAndIdNot(Long idAlumno, Long idGrupo, Long idInscripcion);
}
