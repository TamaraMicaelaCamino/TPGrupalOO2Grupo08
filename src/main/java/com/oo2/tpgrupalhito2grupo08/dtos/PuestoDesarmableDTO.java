package com.oo2.tpgrupalhito2grupo08.dtos;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PuestoDesarmableDTO extends UnidadDeVentaDTO{


    @Positive(message = "Debe tener al menos una carpa")
    private int cantCarpas;


    @Positive(message = "El tiempo de montaje debe ser mayor a 0")
    private int tiempoDeMontaje;


}
