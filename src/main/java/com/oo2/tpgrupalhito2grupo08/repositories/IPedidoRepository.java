package com.oo2.tpgrupalhito2grupo08.repositories;
import com.oo2.tpgrupalhito2grupo08.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p WHERE p.unidadDeVenta.idUnidadDeVenta = :idUnidad")
    List<Pedido> buscarPorUnidad(@Param("idUnidad") Long idUnidad);

    @Query("""
    SELECT p
    FROM Pedido p
    JOIN p.unidadDeVenta u
    WHERE u.responsable.username = :username
      AND (:idUnidadDeVenta IS NULL
           OR u.idUnidadDeVenta = :idUnidadDeVenta)
    """)
    List<Pedido> buscarPorResponsableYUnidad(
            @Param("idUnidadDeVenta") Long idUnidadDeVenta,
            @Param("username") String username
    );

    Optional<Pedido> findByIdPedidoAndUnidadDeVenta_Responsable_Username(@Param("idPedido") Long idPedido, @Param("username") String username);


    @Query("""
    SELECT p
    FROM Pedido p
    JOIN p.unidadDeVenta u
    WHERE u.idUnidadDeVenta = :idUnidadDeVenta AND p.fechaTransaccion = :fecha
    """)
    List<Pedido> pedidosPorUnidadYFecha(@Param("idUnidadDeVenta")Long idUnidadDeVenta,
                                                   @Param("fecha") LocalDate fecha);


    @Query("""
    SELECT p
    FROM Pedido p
    JOIN p.unidadDeVenta u
    JOIN u.personal per
    WHERE per.usuario.username = :username
      AND per.activo = true
      AND TYPE(per) = Cajero
      AND p.idPedido = :idPedido
    """)
    Optional<Pedido> buscarPorCajeroYUnidad(
            @Param("idPedido") Long idPedido,
            @Param("username") String username
    );
}
