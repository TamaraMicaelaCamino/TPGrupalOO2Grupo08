package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.dtos.CajeroDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalCocinaDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.UsuarioDTO;
import com.oo2.tpgrupalhito2grupo08.entities.*;
import com.oo2.tpgrupalhito2grupo08.entities.enums.RolEnum;
import com.oo2.tpgrupalhito2grupo08.events.AltaEmpleadoEvent;
import com.oo2.tpgrupalhito2grupo08.repositories.IPersonalRepository;
import com.oo2.tpgrupalhito2grupo08.services.IPersonalService;
import com.oo2.tpgrupalhito2grupo08.components.GeneradorPassword;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalService implements IPersonalService {

    private final EmailService emailService;
    private final UsuarioService usuarioService;
    private final UnidadDeVentaService unidadService;
    private final IPersonalRepository personalRepository;
    private final GeneradorPassword generadorPassword;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PersonalService(EmailService emailService, IPersonalRepository iPersonalRepository,
                           UsuarioService usuarioService,
                           UnidadDeVentaService unidadService,
                           GeneradorPassword generadorPassword,
                           ModelMapper modelMapper, ApplicationEventPublisher applicationEventPublisher){
        this.emailService = emailService;
        this.personalRepository=iPersonalRepository;
        this.unidadService=unidadService;
        this.usuarioService=usuarioService;
        this.generadorPassword=generadorPassword;
        this.modelMapper=modelMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<PersonalDTO> traerPersonalPorResponsableDeUnidad(Long idUnidadDeVenta, String userResponsable){
        List<PersonalDTO> personalDTOS = new ArrayList<>();
        for (Personal personal : personalRepository.findByIdUnidadDeVentaAndResponsable_Username(idUnidadDeVenta,userResponsable)){
            PersonalDTO dto = modelMapper.map(personal, PersonalDTO.class);
            dto.setTipoPersonal(personal.getTipoPersonal());
            personalDTOS.add(dto);
        }
        return personalDTOS;
    }

    @Override
    public PersonalDTO traerPorId(Long id){
        Personal personal = personalRepository
                .findById(id)
                .orElseThrow(
                        ()->new IllegalArgumentException("ERROR, ID INEXISTENTE" + id));
        return modelMapper.map(personal, PersonalDTO.class);
    }

    @Transactional
    @Override
    public void resgistrarCajero(CajeroDTO cajeroDTO, String userResponsable){

        String password= generadorPassword.generar();
        UnidadDeVenta unidadDeVenta= unidadService.buscarUnidadDelResponsable(cajeroDTO.getIdUnidadDeVenta(),userResponsable);
        UsuarioDTO usuarioDTO= new UsuarioDTO(cajeroDTO.getEmail(), password);
        Usuario usuario = usuarioService.crearUsuario(usuarioDTO, RolEnum.EMPLEADO);
        Cajero nuevo = new Cajero(
                cajeroDTO.getNombre(),
                cajeroDTO.getApellido(),
                cajeroDTO.getEmail(),
                cajeroDTO.getDni(),
                cajeroDTO.getFechaIngreso(),
                cajeroDTO.getFechaNacimiento(),
                usuario,
                unidadDeVenta,
                cajeroDTO.getTurno()
        );
        personalRepository.save(nuevo);
        applicationEventPublisher.publishEvent(new AltaEmpleadoEvent(nuevo.getIdPersonal(),cajeroDTO.getEmail(), password));
    }

    @Transactional
    @Override
    public void registrarPersonalCocina(PersonalCocinaDTO personalCocinaDTO, String userResponsable) {
        String password= generadorPassword.generar();
        UnidadDeVenta unidadDeVenta= unidadService.buscarUnidadDelResponsable(personalCocinaDTO.getIdUnidadDeVenta(),userResponsable);
        UsuarioDTO usuarioDTO= new UsuarioDTO(personalCocinaDTO.getEmail(), password);
        Usuario usuario = usuarioService.crearUsuario(usuarioDTO, RolEnum.EMPLEADO);
        PersonalCocina nuevo= new PersonalCocina(
                personalCocinaDTO.getNombre(),
                personalCocinaDTO.getApellido(),
                personalCocinaDTO.getEmail(),
                personalCocinaDTO.getDni(),
                personalCocinaDTO.getFechaIngreso(),
                personalCocinaDTO.getFechaNacimiento(),
                usuario,
                unidadDeVenta,
                personalCocinaDTO.getVencimientoCarnetDeManipulacion(),
                personalCocinaDTO.getCategoria()
        );
        personalRepository.save(nuevo);
        applicationEventPublisher.publishEvent(new AltaEmpleadoEvent(nuevo.getIdPersonal(),personalCocinaDTO.getEmail(), password));
    }

}
