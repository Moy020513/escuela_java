package com.moises.escuela.entities;

import com.moises.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    public void actualizar(String nombre, Integer capacidad){
        StringCustomUtils.validarTamanio(nombre, 1, 100,
                "El nombre es requerido y debe tener entre 1 y 100 caracteres");
        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }
}