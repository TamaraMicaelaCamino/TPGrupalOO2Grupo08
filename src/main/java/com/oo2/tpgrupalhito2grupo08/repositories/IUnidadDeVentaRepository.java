package com.oo2.tpgrupalhito2grupo08.repositories;

import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUnidadDeVentaRepository extends JpaRepository<UnidadDeVenta, Long> {

    boolean existsByCodigoIgnoreCase(String codigo);

    @Query("SELECT COUNT(f) > 0 FROM FoodTruck f WHERE f.patente = :patente")
    boolean existsFoodTruckByPatente(@Param("patente") String patente);

    @Query("SELECT u FROM UnidadDeVenta u WHERE u.festival.idFestival = :idFestival")
    List<UnidadDeVenta> buscarPorFestival(@Param("idFestival") Long idFestival);

    @Query("SELECT COUNT(u) FROM UnidadDeVenta u WHERE u.festival.idFestival = :idFestival")
    long contarPorFestival(@Param("idFestival") Long idFestival);


}
