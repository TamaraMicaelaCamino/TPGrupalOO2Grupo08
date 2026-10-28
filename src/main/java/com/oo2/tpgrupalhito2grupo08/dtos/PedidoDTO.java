package com.oo2.tpgrupalhito2grupo08.dtos;

import com.oo2.tpgrupalhito2grupo08.entities.DetallePedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDTO {

    private Long idPedido;

    private LocalDate fechaTransaccion;

    private List<DetallePedidoDTO> detalles = new ArrayList<>();

    private String nombreUnidad;

    private double total;
}
