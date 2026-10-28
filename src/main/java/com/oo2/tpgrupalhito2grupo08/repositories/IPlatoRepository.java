package com.oo2.tpgrupalhito2grupo08.repositories;

import com.oo2.tpgrupalhito2grupo08.entities.Personal;
import com.oo2.tpgrupalhito2grupo08.entities.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPlatoRepository extends JpaRepository<Plato, Long> {
    @Query("""
    SELECT p
    FROM Plato p
    JOIN p.unidadDeVenta u
    WHERE u.responsable.username = :userResponsable
      AND (:idUnidadDeVenta IS NULL
           OR u.idUnidadDeVenta = :idUnidadDeVenta)
    """)
    List<Plato> findByIdUnidadDeVentaAndResponsable_Username(@Param("idUnidadDeVenta") Long idUnidadDeVenta, @Param("userResponsable") String userResponsable);

    List<Plato> findByUnidadDeVenta_IdUnidadDeVenta(Long idUnidadDeVenta);}
