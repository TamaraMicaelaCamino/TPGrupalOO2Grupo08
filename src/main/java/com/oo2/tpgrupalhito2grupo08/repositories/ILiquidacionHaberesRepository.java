package com.oo2.tpgrupalhito2grupo08.repositories;
import com.oo2.tpgrupalhito2grupo08.entities.LiquidacionHaberes;
import com.oo2.tpgrupalhito2grupo08.entities.Personal;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ILiquidacionHaberesRepository extends JpaRepository<LiquidacionHaberes, Long> {

    @Query("SELECT l FROM LiquidacionHaberes l WHERE l.personal = :personal AND l.unidadDeVenta = :unidad")
    Optional<LiquidacionHaberes> buscarPersonalYUnidad(@Param("personal") Personal personal,@Param("unidad") UnidadDeVenta unidad);

    @Query("SELECT l FROM LiquidacionHaberes l WHERE l.unidadDeVenta.idUnidadDeVenta = :idUnidad")
    List<LiquidacionHaberes> listarPorUnidad(@Param("idUnidad") Long idUnidad);

    @Query("SELECT l FROM LiquidacionHaberes l WHERE l.personal.idPersonal = :idPersonal")
    List<LiquidacionHaberes> listarPorPersonal(@Param("idPersonal") Long idPersonal);

    @Query("""
    SELECT l FROM LiquidacionHaberes l
    JOIN FETCH l.unidadDeVenta u
    JOIN FETCH u.festival f
    WHERE l.personal.idPersonal = :idPersonal
      AND f.fechaFin < :hoy
""")
    List<LiquidacionHaberes> listarFinalizadosPorPersonal(@Param("idPersonal") Long idPersonal,
                                                          @Param("hoy") LocalDate hoy);






}
