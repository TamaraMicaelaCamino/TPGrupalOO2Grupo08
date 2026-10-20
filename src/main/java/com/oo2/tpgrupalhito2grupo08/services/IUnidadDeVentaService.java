package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.*;

import java.util.List;

public interface IUnidadDeVentaService {

    void registrarFoodTruck(FoodTruckDTO dto);

    void registrarPuestoDesarmable(PuestoDesarmableDTO dto);

    List<UnidadDeVentaDTO> traerPorFestival(Long idFestival);


    List<RankingUnidadDTO> rankingDeUnidades(Long idFestival);

    List<ReporteMayoresCanonDTO> unidadesConMayorCanon(Long idFestival, int x);

    long cantidadDeUnidades(Long idFestival);

}
