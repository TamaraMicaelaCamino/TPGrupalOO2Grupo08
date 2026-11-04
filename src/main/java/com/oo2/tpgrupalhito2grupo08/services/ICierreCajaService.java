package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.CierreCajaDTO;
import com.oo2.tpgrupalhito2grupo08.entities.CierreCaja;

import java.security.Principal;

public interface ICierreCajaService {
    CierreCaja altaCierreCaja(String usuario);

    CierreCajaDTO traerCierreDelDia(String username);
}
