package com.moises.escuela.entities;

import com.moises.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CURSOS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 50)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    public void actualizar(String nombre, String descripcion,
                              Integer creditos) {
        StringCustomUtils.validarTamanio(nombre, 1, 100,
                "El nombre es requerido y debe tener entre 1 y 100 caracteres");
        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.creditos = creditos;
    }

}