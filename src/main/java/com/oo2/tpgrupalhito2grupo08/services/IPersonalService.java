package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.CajeroDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalCocinaDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;

import java.util.List;

public interface IPersonalService {

    List<PersonalDTO> traerPersonalPorResponsableDeUnidad(Long idUnidaDeVenta, String userResponsable);

    PersonalDTO traerPorId(Long idPersonal);

    void resgistrarCajero(CajeroDTO cajeroDTO, String userResponsable);

    void registrarPersonalCocina(PersonalCocinaDTO personalCocinaDTO, String userResponsable);
}
