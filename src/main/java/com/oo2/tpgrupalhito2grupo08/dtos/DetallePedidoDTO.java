package com.oo2.tpgrupalhito2grupo08.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class DetallePedidoDTO {

    private String nombrePlato;

    private Integer cantidad;

    private double precioVenta;
}
