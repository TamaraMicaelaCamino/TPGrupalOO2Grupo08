package com.oo2.tpgrupalhito2grupo08.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMayoresCanonDTO {
    private String nombreComercial;
    private String codigo;
    private String tipoDeUnidad;
    private double canon;

}