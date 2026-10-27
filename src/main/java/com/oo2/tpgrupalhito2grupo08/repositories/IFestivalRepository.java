package com.oo2.tpgrupalhito2grupo08.repositories;

import com.oo2.tpgrupalhito2grupo08.entities.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFestivalRepository extends JpaRepository<Festival,Long > {

    @Query("SELECT f FROM Festival f WHERE f.activo = true")
    List<Festival> buscarActivos();


}
