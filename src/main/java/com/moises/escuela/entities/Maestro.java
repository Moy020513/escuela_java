package com.moises.escuela.entities;

import com.moises.escuela.utils.StringCustonUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MAESTROS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Maestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAESTRO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "TELEFONO", nullable = false, length = 10, unique = true)
    private String telefono;

    @OneToMany(mappedBy = "maestro", fetch = FetchType.LAZY)
    private List<Grupo> grupos = new ArrayList<>();

    private void validarDatos(String nombre, String apellidoPaterno,
                              String apellidoMaterno, String email, String telefono) {
        StringCustonUtils.validarTamanio(nombre, 1, 50,
                "El nombre es requerido y debe tener entre 1 y 50 caracteres");

        StringCustonUtils.validarTamanio(apellidoPaterno, 1, 50,
                "El apelido paterno es requerido y debe tener entre 1 y 50 caracteres");

        StringCustonUtils.validarTamanio(apellidoMaterno, 1, 50,
                "El apelido Materno es requerido y debe tener entre 1 y 50 caracteres");

        StringCustonUtils.validarTamanio(email, 1, 100,
                "El email es requerido y debe tener entre 1 y 100 caracteres");

        StringCustonUtils.validarTamanio(telefono, 10, 10,
                "La matrícula es requerida y debe tener exactamente 10 caracteres");


    }

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno,
                           String email, String telefono){
        validarDatos(nombre, apellidoPaterno, apellidoMaterno, email, telefono);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email = email.toLowerCase().trim();
        this.telefono = telefono.trim();
    }
}