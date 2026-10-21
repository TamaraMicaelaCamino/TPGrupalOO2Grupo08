package com.oo2.tpgrupalhito2grupo08.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AltaEmpleadoEvent {
    private Long idEmpleado;

    private String usuario;

    private String password;

}
