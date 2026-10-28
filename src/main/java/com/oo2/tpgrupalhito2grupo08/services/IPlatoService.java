package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PlatoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Plato;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPlatoService {
    List<PlatoDTO> traerPlatoPorResponsableDeUnidad(Long idUnidadDeVenta, String userResponsable);

    List<PlatoDTO> traerPlatoPorIdUnidadDeVenta(Long idUnidadDeVenta);

    @Transactional
    Plato buscarPorId(Long idPlato);
}
