package com.oo2.tpgrupalhito2grupo08.dtos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnidadDeVentaDTO {

    @NotNull(message = "Selecciona un festival")
    private Long idFestival;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede superar los 45 caracteres")
    private String nombre;

    @Positive(message = "La superficie debe ser mayor a 0")
    private double superficieOcupada;

    @NotBlank(message = "El codigo es obligatorio")
    @Size(min = 10, max = 10, message = "El código debe tener 10 caracteres")
    private String codigo;

    @Valid
    @NotNull(message = "Los datos del responsable son obligatorios")
    private UsuarioDTO usuario = new UsuarioDTO();   // datos del responsable

}
