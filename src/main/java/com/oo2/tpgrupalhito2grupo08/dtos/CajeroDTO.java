package com.oo2.tpgrupalhito2grupo08.dtos;

import com.oo2.tpgrupalhito2grupo08.entities.enums.TurnoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CajeroDTO extends AltaPersonalDTO {

    @NotNull(message ="Elija turno")
    private TurnoEnum turno;
}
