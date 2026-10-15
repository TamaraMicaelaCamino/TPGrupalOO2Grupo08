package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;

import java.util.List;

public interface IPersonalService {
    List<PersonalDTO> traerTodos();

    PersonalDTO traerPorId(Long idPersonal);
}
