package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.dtos.PlatoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Plato;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import com.oo2.tpgrupalhito2grupo08.repositories.IPlatoRepository;
import com.oo2.tpgrupalhito2grupo08.services.IPlatoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlatoService implements IPlatoService {

    private final IPlatoRepository platoRepository;
    private final UnidadDeVentaService unidadDeVentaService;
    private final ModelMapper modelMapper;

    public PlatoService(IPlatoRepository platoRepository, UnidadDeVentaService unidadDeVentaService, ModelMapper modelMapper){
        this.platoRepository = platoRepository;
        this.unidadDeVentaService=unidadDeVentaService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PlatoDTO> traerPlatoPorResponsableDeUnidad(Long idUnidadDeVenta, String userResponsable) {
        List<PlatoDTO> platosDTO = new ArrayList<PlatoDTO>();
        for(Plato plato : platoRepository.findByIdUnidadDeVentaAndResponsable_Username(idUnidadDeVenta,userResponsable)){
            PlatoDTO platoDTO = modelMapper.map(plato, PlatoDTO.class);
            platosDTO.add(platoDTO);
        }
        return platosDTO;
    }
}
