package com.oo2.tpgrupalhito2grupo08.dtos;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class ReciboSueldoPdfDTO {

    private LiquidacionHaberesDTO liquidacion;
    private long dni;
    private LocalDate fechaIngreso;
    private LocalDate fechaInicioFestival;
    private LocalDate fechaFinFestival;
    private String tipoUnidad;


}
