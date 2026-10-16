package com.oo2.tpgrupalhito2grupo08.repositories;

import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUnidadDeVentaRepository extends JpaRepository<UnidadDeVenta, Long> {

    boolean existsByCodigo(String codigo);

    @Query("SELECT COUNT(f) > 0 FROM FoodTruck f WHERE f.patente = :patente")
    boolean existsFoodTruckByPatente(@Param("patente") String patente);
}
