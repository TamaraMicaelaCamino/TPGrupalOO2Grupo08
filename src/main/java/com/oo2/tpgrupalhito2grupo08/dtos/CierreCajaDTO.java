package com.oo2.tpgrupalhito2grupo08.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CierreCajaDTO {

    private LocalDate fechaOperativa;
    private LocalDateTime fechaHoraCierre;
    private int cantPedidos;
    private Double totalRecaudado;
    private String nombreCajero;
}
