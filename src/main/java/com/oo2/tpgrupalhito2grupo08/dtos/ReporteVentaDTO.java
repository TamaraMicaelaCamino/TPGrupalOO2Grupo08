package com.oo2.tpgrupalhito2grupo08.dtos;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteVentaDTO {
    private Long idFestival;
    private String nombreFestival;
    private double recaudacionTotal;
    private List<String> unidades;
    private List<Double> recaudaciones;

}
