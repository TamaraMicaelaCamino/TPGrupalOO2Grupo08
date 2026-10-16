package com.oo2.tpgrupalhito2grupo08.dtos;

import com.oo2.tpgrupalhito2grupo08.entities.enums.Categoria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class PersonalCocinaDTO extends PersonalDTO{

    private Categoria categoria;

    private LocalDate vencimientoCarnetDeManipulacion;
}
