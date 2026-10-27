package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.LiquidacionHaberesDTO;
import com.oo2.tpgrupalhito2grupo08.entities.LiquidacionHaberes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ILiquidacionHaberesService {

    List<LiquidacionHaberesDTO> liquidarUnidad(Long idUnidad);

    List<LiquidacionHaberesDTO> traerPorPersonal(Long idPersonal);

    List<LiquidacionHaberes> listarFinalizadosxPersonal(Long idPersonal);
}
