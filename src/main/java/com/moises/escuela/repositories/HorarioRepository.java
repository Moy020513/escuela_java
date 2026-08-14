package com.moises.escuela.repositories;

import com.moises.escuela.entities.Horario;
import com.moises.escuela.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByGrupoId(Long idGrupo);

    @Query("""
    SELECT h
    FROM Horario h
    WHERE h.diaSemana = :dia
      AND h.grupo.id = :idGrupo
      AND h.grupo.aula.id = :idAula
      AND h.grupo.periodo = :idPeriodo
""")
    List<Horario> obtenerHorariosConflicto(
            @Param("dia") DiaSemana dia,
            @Param("idGrupo") Long idGrupo,
            @Param("idAula") Long idAula,
            @Param("idPeriodo") String periodo
    );

    @Query("""
    SELECT h
    FROM Horario h
    WHERE h.diaSemana = :dia
      AND h.grupo.id = :idGrupo
      AND h.grupo.aula.id = :idAula
      AND h.grupo.periodo = :idPeriodo
      AND h.id <> :idHorario
""")
    List<Horario> obtenerHorariosConflicto(
            @Param("dia") DiaSemana dia,
            @Param("idGrupo") Long idGrupo,
            @Param("idAula") Long idAula,
            @Param("idPeriodo") String periodo,
            @Param("idHorario") Long idHorario
    );
}