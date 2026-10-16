package com.oo2.tpgrupalhito2grupo08.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PersonalDTO {

    private String nombre;

    private String apellido;

    private String email;

    private LocalDate fechaIngreso;

    private LocalDate fechaNacimiento;

    private String tipoPersonal;
}
