package com.moises.escuela.entities;

import com.moises.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint(
        name = "GRUPO_CU_MA_AU_PE_UK",
        columnNames = {"ID_CURSO", "ID_MAESTRO", "ID_AULA", "PERDIODO"}
))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @Column(name = "PERIODO",length = 20, nullable = false)
    private String periodo;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo",
            orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Horario> horarios = new ArrayList<>();

    public void actualizar(Curso curso, Maestro maestro, Aula aula, String periodo){
        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo;
    }

    public void asignarCurso(Curso curso) {
        this.curso = curso;
    }
}
