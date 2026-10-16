package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.dtos.FestivalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Festival;
import com.oo2.tpgrupalhito2grupo08.repositories.IFestivalRepository;
import com.oo2.tpgrupalhito2grupo08.services.IFestivalService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FestivalService implements IFestivalService {

    private final ModelMapper modelMapper;
    private final IFestivalRepository festivalRepository;

    public FestivalService(IFestivalRepository festivalRepository, ModelMapper modelMapper){
        this.festivalRepository=festivalRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<FestivalDTO> traerTodos() {
        List<FestivalDTO> festivalDTOS = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()){
            FestivalDTO dto = modelMapper.map(festival, FestivalDTO.class);
            festivalDTOS.add(dto);
        }
        return festivalDTOS;
    }

    @Override
    public FestivalDTO traerPorId(Long idFestival) {
        Festival festival = festivalRepository
                .findById(idFestival)
                .orElseThrow(()
                        -> new IllegalArgumentException("ERROR, ID INEXISTENTE"+ idFestival));
        return modelMapper.map(festival, FestivalDTO.class);
    }

    @Override
    public Festival buscarFestival(Long idFestival) {
        return festivalRepository.findById(idFestival)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El festival no existe, id: " + idFestival));
    }




}
