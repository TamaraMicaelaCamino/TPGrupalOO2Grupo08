package com.oo2.tpgrupalhito2grupo08.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FestivalDTO {

    @NotBlank(message ="El nombre es obligatorio")
    private String nombre;

    @NotBlank(message ="La temporada es obligatoria")
    private String temporada;

    @NotBlank(message = "La fecha debe ser ingresada")
    private LocalDate fechaInicio;

    @NotBlank(message = "La fecha debe ser ingresada")
    private LocalDate fechaFin;

}
