package com.oo2.tpgrupalhito2grupo08.dtos;

import com.oo2.tpgrupalhito2grupo08.entities.Usuario;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AltaPersonalDTO {

    @NotNull(message = "Ingrese DNI")
    private Long dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede superar los 45 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 45, message = "El apellido no puede superar los 45 caracteres")
    private String apellido;

    @NotBlank(message ="El email es obligatorio")
    @Email
    private String email;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate fechaIngreso;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    @NotNull(message = "La unidad es obligatoria")
    private Long idUnidadDeVenta;
}
