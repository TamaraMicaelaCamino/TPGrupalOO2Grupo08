package com.oo2.tpgrupalhito2grupo08.repositories;

import com.oo2.tpgrupalhito2grupo08.entities.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPersonalRepository extends JpaRepository<Personal, Long> {
    @Query("""
    SELECT p
    FROM Personal p
    JOIN p.unidadDeVenta u 
    WHERE u.responsable.username =:userResponsable AND p.activo = true      
""")
    List<Personal> traerPersonalPorResponsableDeUnidad(@Param("userResponsable") String userResponsable);

}
