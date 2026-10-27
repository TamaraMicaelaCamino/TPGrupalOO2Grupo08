package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PlatoDTO;

import java.util.List;

public interface IPlatoService {
    List<PlatoDTO> traerPlatoPorResponsableDeUnidad(Long idUnidadDeVenta, String userResponsable);

}
