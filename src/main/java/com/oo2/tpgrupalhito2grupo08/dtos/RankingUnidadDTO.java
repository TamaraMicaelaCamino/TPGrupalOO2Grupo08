package com.oo2.tpgrupalhito2grupo08.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankingUnidadDTO {

    private Long idUnidadDeVenta;
    private String nombre;
    private String tipo;
    private String responsable;
    private double recaudacion;
}