package com.oo2.tpgrupalhito2grupo08.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlatoDTO {
    private Long idPlato;
    private String nombre;
    private double costoProduccion;
    private double precioVenta;
    private Long idUnidadDeVenta;
}
