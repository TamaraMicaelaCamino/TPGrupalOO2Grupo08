package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.FestivalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Festival;

import java.util.List;

public interface IFestivalService {

    List<FestivalDTO> traerTodos();

    FestivalDTO traerPorId(Long idFestival);

    Festival buscarFestival(Long idFestival);

    List<FestivalDTO> traerActivos();
}
