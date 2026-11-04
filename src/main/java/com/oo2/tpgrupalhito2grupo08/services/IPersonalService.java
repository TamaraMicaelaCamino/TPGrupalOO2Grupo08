package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.CajeroDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalCocinaDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Cajero;
import com.oo2.tpgrupalhito2grupo08.entities.Personal;

import java.util.List;

public interface IPersonalService {

    List<PersonalDTO> traerPersonalPorResponsableDeUnidad(Long idUnidaDeVenta, String userResponsable);

    PersonalDTO traerPorId(Long idPersonal);

    void resgistrarCajero(CajeroDTO cajeroDTO, String userResponsable);

    void registrarPersonalCocina(PersonalCocinaDTO personalCocinaDTO, String userResponsable);

    Personal buscarPersonalActivo(String username);

    Cajero buscarCajeroActivo(String username);
}
