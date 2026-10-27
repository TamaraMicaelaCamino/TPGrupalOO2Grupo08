package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.UnidadVentaFestivalDTO;

import java.util.List;

public interface IEmpleadoService {

    List<UnidadVentaFestivalDTO> misFestivalesYUnidades(String username);

    boolean esCajero(String username);


    UnidadVentaFestivalDTO miTrabajoActual(String username);

    String obtenerTipoPersonal(String username);

}
