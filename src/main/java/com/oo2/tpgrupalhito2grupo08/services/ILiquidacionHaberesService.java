package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.LiquidacionHaberesDTO;
import java.util.List;

public interface ILiquidacionHaberesService {

    List<LiquidacionHaberesDTO> liquidarUnidad(Long idUnidad);

    List<LiquidacionHaberesDTO> traerPorUnidad(Long idUnidad);

    List<LiquidacionHaberesDTO> consultarPorResponsable(Long idUnidadDeVenta, String username);

    List<LiquidacionHaberesDTO> traerPorPersonal(Long idPersonal);
}
