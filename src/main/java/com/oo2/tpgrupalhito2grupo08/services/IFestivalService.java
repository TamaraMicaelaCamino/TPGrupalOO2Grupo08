package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.FestivalDTO;

import java.util.List;

public interface IFestivalService {
    List<FestivalDTO> traerTodos();

    FestivalDTO traerPorId(Long idFestival);

}
