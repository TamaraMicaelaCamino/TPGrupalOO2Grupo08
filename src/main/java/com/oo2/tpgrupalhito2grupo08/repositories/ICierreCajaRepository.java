package com.oo2.tpgrupalhito2grupo08.repositories;

import com.oo2.tpgrupalhito2grupo08.entities.CierreCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ICierreCajaRepository extends JpaRepository<CierreCaja, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
        FROM CierreCaja c
        WHERE c.unidad.idUnidadDeVenta = :idUnidad AND c.fechaOperativa = :fechaOperativa
        """)
    boolean existeCierreDeCaja(@Param("idUnidad") Long idUnidad, @Param("fechaOperativa") LocalDate fechaOperativa);

    @Query("""
    SELECT c
    FROM CierreCaja c
    JOIN c.unidad u
    JOIN u.personal per
    WHERE per.usuario.username = :username
      AND per.activo = true
      AND TYPE(per) = Cajero
      AND c.fechaOperativa = :fechaOperativa
    """)
    CierreCaja buscarUnidadPorCajero(@Param("username")String username, @Param("fechaOperativa") LocalDate fechaOperativa);
}

