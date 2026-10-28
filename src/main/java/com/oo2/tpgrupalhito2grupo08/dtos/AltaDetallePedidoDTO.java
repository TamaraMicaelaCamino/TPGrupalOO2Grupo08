package com.oo2.tpgrupalhito2grupo08.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AltaDetallePedidoDTO {

    @NotNull(message="el plato debe ser seleccionado")
    private Long idPlato;

    @Positive
    @NotNull(message="Ingrese cantidad valida")
    private Integer cantidad;
}
