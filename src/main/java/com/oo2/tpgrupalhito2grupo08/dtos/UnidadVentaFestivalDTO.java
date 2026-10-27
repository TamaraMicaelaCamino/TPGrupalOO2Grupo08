package com.oo2.tpgrupalhito2grupo08.dtos;
import lombok.*;

@Getter
@Builder
public class UnidadVentaFestivalDTO {
    private FestivalDTO festival;
    private String nombreUnidad;
    private String codigoUnidad;
    private String tipoUnidad;
}
