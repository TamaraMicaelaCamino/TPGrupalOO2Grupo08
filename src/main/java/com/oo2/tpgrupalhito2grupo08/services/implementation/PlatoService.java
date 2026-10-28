package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.dtos.PlatoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Plato;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import com.oo2.tpgrupalhito2grupo08.repositories.IPlatoRepository;
import com.oo2.tpgrupalhito2grupo08.services.IPlatoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlatoService implements IPlatoService {

    private final IPlatoRepository platoRepository;
    private final ModelMapper modelMapper;

    public PlatoService(IPlatoRepository platoRepository, ModelMapper modelMapper){
        this.platoRepository = platoRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Plato buscarPorId(Long idPlato){
        return platoRepository.findById(idPlato)
                .orElseThrow(
                        ()-> new IllegalArgumentException("error, id erroneo"));
    }

    @Override
    public List<PlatoDTO> traerPlatoPorResponsableDeUnidad(Long idUnidadDeVenta, String userResponsable) {
        List<PlatoDTO> platosDTO = new ArrayList<>();
        for(Plato plato : platoRepository.findByIdUnidadDeVentaAndResponsable_Username(idUnidadDeVenta,userResponsable)){
            PlatoDTO platoDTO = modelMapper.map(plato, PlatoDTO.class);
            platosDTO.add(platoDTO);
        }
        return platosDTO;
    }

    @Override
    public List<PlatoDTO> traerPlatoPorIdUnidadDeVenta(Long idUnidadDeVenta) {
        List<PlatoDTO> platosDTO = new ArrayList<>();
        for(Plato plato : platoRepository.findByUnidadDeVenta_IdUnidadDeVenta(idUnidadDeVenta)){
            PlatoDTO platoDTO = modelMapper.map(plato, PlatoDTO.class);
            platosDTO.add(platoDTO);
        }
        return platosDTO;
    }
}
