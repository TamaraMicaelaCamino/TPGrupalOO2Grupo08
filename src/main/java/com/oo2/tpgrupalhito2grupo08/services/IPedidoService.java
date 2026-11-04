package com.oo2.tpgrupalhito2grupo08.services;


import com.oo2.tpgrupalhito2grupo08.dtos.AltaPedidoDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PedidoDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PlatoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Plato;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface IPedidoService {

    double recaudacionPorUnidad(Long idUnidadDeVenta);

    @Transactional(readOnly = true)
    List<PedidoDTO> pedidosPorUnidadYResponsable(Long idUnidadDeVenta, String username);

    PedidoDTO traerPedidoPorIdYResponsable(Long idPedido, String username);

    PedidoDTO traerPedidoPorIdYCajero(Long idPedido, String username);

    @Transactional
    Long altaPedido(AltaPedidoDTO altaPedidoDTO, String username);

    List<PlatoDTO> traerPlatosDelCajero(String username);

    List<PedidoDTO> pedidosPorUnidadYFecha(Long idUnidadDeVenta, LocalDate fechaOperativa);
}
