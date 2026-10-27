package com.oo2.tpgrupalhito2grupo08.repositories;

import com.oo2.tpgrupalhito2grupo08.entities.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPersonalRepository extends JpaRepository<Personal, Long> {
    @Query("""
    SELECT p
    FROM Personal p
    JOIN p.unidadDeVenta u
    WHERE u.responsable.username = :userResponsable
      AND p.activo = true
      AND (:idUnidadDeVenta IS NULL
           OR u.idUnidadDeVenta = :idUnidadDeVenta)
    """)
    List<Personal> findByIdUnidadDeVentaAndResponsable_Username(@Param("idUnidadDeVenta") Long idUnidadDeVenta,@Param("userResponsable") String userResponsable);

    Optional<Personal> findByUsuarioUsername(String username);

}
