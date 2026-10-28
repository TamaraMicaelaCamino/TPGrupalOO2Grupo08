package com.oo2.tpgrupalhito2grupo08.dtos;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AltaPedidoDTO {

    @Valid
    @NotEmpty(message = "El pedido debe tener al menos un plato")
    private List<AltaDetallePedidoDTO> detalles = new ArrayList<>();

}
