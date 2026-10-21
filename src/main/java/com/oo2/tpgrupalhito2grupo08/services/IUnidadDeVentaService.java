package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.FoodTruckDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PuestoDesarmableDTO;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;

import java.util.List;

public interface IUnidadDeVentaService {

    void registrarFoodTruck(FoodTruckDTO dto);

    void registrarPuestoDesarmable(PuestoDesarmableDTO dto);

    UnidadDeVenta buscarResponsableUnidad(String username);
}
