package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Personal;
import com.oo2.tpgrupalhito2grupo08.repositories.IPersonalRepository;
import com.oo2.tpgrupalhito2grupo08.services.IPersonalService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalService implements IPersonalService {

    private final IPersonalRepository iPersonalRepository;
    private final ModelMapper modelMapper;
    public PersonalService(IPersonalRepository iPersonalRepository, ModelMapper modelMapper){
        this.iPersonalRepository=iPersonalRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<PersonalDTO> traerTodos(){
        List<PersonalDTO> personalDTOS = new ArrayList<>();
        for (Personal personal : iPersonalRepository.findAll()){
            PersonalDTO dto = modelMapper.map(personal, PersonalDTO.class);
            personalDTOS.add(dto);
        }
        return personalDTOS;
    }

    @Override
    public PersonalDTO traerPorId(Long id){
        Personal personal = iPersonalRepository
                .findById(id)
                .orElseThrow(
                        ()->new IllegalArgumentException("ERROR, ID INEXISTENTE" + id));
        return modelMapper.map(personal, PersonalDTO.class);
    }

}
