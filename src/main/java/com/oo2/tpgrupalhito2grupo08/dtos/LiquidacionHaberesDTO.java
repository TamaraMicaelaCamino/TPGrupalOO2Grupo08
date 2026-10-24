package com.oo2.tpgrupalhito2grupo08.dtos;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiquidacionHaberesDTO {


    private Long idLiquidacion;
    private String nombreCompleto;
    private String tipoPersonal;
    private String nombreUnidad;
    private String nombreFestival;
    private long diasFestival;
    private double sueldoBase;
    private double factor;
    private int antiguedad;
    private double adicional;
    private double total;

}
