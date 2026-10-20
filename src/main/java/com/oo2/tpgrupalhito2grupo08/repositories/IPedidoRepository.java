package com.oo2.tpgrupalhito2grupo08.repositories;
import com.oo2.tpgrupalhito2grupo08.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p WHERE p.unidadDeVenta.idUnidadDeVenta = :idUnidad")
    List<Pedido> buscarPorUnidad(@Param("idUnidad") Long idUnidad);

}
