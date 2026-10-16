package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.dtos.FestivalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Festival;
import com.oo2.tpgrupalhito2grupo08.repositories.IFestivalRepository;
import com.oo2.tpgrupalhito2grupo08.services.IFestivalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FestivalService implements IFestivalService {

    private final IFestivalRepository festivalRepository;

    public FestivalService(IFestivalRepository festivalRepository){
        this.festivalRepository=festivalRepository;
    }

    @Override
    public List<FestivalDTO> traerTodos() {
        List<FestivalDTO> festivalDTOS = new ArrayList<>();
        for (Festival festival : festivalRepository.findAll()){
            FestivalDTO dto = mapeoFestival(festival);
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
        return mapeoFestival(festival);
    }

    private FestivalDTO mapeoFestival(Festival festival){
        FestivalDTO dto = new FestivalDTO(
                festival.getNombre(),
                festival.getTemporada(),
                festival.getFechaInicio(),
                festival.getFechaFin()
        );
        return dto;
    }

}
