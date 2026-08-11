package com.moises.escuela.repositories;

import com.moises.escuela.entities.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaestroRepository extends JpaRepository<Maestro, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByTelefono(String telefono);

    boolean existsByemailIgnoreCaseAndIdNot(String email, Long id);

    boolean existsByTelefonoAndIdNot(String telefono, Long id);
}
