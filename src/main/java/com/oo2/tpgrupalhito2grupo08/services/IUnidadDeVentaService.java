package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.*;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;

import java.util.List;

public interface IUnidadDeVentaService {

    void registrarFoodTruck(FoodTruckDTO dto);

    void registrarPuestoDesarmable(PuestoDesarmableDTO dto);

    List<UnidadDeVenta> listarUnidadesResponsable(String username);

    UnidadDeVenta buscarUnidadDelResponsable(Long idUnidadDeVenta, String username);

    List<UnidadDeVentaDTO> traerPorFestival(Long idFestival);

    UnidadDeVenta obtenerUnidadPorId(Long id);
    List<RankingUnidadDTO> rankingDeUnidades(Long idFestival);

    List<ReporteMayoresCanonDTO> unidadesConMayorCanon(Long idFestival, int x);

    long cantidadDeUnidades(Long idFestival);

}
