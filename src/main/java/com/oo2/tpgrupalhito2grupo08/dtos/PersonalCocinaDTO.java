package com.oo2.tpgrupalhito2grupo08.dtos;

import com.oo2.tpgrupalhito2grupo08.entities.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class PersonalCocinaDTO extends AltaPersonalDTO{

    @NotNull(message = "Elija categoria")
    private Categoria categoria;

    @NotNull(message = "La fecha de vencimiento del carnet es obligatoria")
    private LocalDate vencimientoCarnetDeManipulacion;
}
