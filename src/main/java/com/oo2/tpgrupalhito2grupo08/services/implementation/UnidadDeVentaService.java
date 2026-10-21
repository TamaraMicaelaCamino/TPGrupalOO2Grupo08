package com.oo2.tpgrupalhito2grupo08.services.implementation;
import com.oo2.tpgrupalhito2grupo08.entities.*;
import com.oo2.tpgrupalhito2grupo08.services.IFestivalService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oo2.tpgrupalhito2grupo08.dtos.FoodTruckDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PuestoDesarmableDTO;
import com.oo2.tpgrupalhito2grupo08.entities.enums.RolEnum;
import com.oo2.tpgrupalhito2grupo08.repositories.IUnidadDeVentaRepository;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import com.oo2.tpgrupalhito2grupo08.services.IUsuarioService;

@Service("unidadDeVentaService")
public class UnidadDeVentaService implements IUnidadDeVentaService {

    private final IUnidadDeVentaRepository unidadRepository;
    private final IFestivalService festivalService;
    private final IUsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public UnidadDeVentaService(IUnidadDeVentaRepository unidadRepository,
                                IFestivalService festivalService,
                                IUsuarioService usuarioService,
                                ModelMapper modelMapper) {
        this.unidadRepository = unidadRepository;
        this.festivalService = festivalService;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void registrarFoodTruck(FoodTruckDTO dto) {

        validarCodigo(dto.getCodigo());
        validarPatente(dto.getPatente());

        Festival festival = festivalService.buscarFestival(dto.getIdFestival());
        Usuario responsable = usuarioService.crearUsuario(dto.getUsuario(), RolEnum.RESPONSABLE);

        FoodTruck unidad = modelMapper.map(dto, FoodTruck.class);
        unidad.setFestival(festival);
        unidad.setResponsable(responsable);
        unidadRepository.save(unidad);
    }

    @Override
    @Transactional
    public void registrarPuestoDesarmable(PuestoDesarmableDTO dto) {

        validarCodigo(dto.getCodigo());

        Festival festival = festivalService.buscarFestival(dto.getIdFestival());
        Usuario responsable = usuarioService.crearUsuario(dto.getUsuario(), RolEnum.RESPONSABLE);

        PuestoDesarmable unidad = modelMapper.map(dto, PuestoDesarmable.class);
        unidad.setFestival(festival);
        unidad.setResponsable(responsable);
        unidadRepository.save(unidad);
    }

   @Override
    public UnidadDeVenta buscarResponsableUnidad(String username) {
        return unidadRepository.findByResponsable_Username(username);
    }

    // validaciones

    private void validarCodigo(String codigo) {
        if (unidadRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ese codigo ya esta en uso");
        }
    }

    private void validarPatente(String patente) {
        if (unidadRepository.existsFoodTruckByPatente(patente)) {
            throw new IllegalArgumentException("Esa patente ya esta registrada");
        }
    }
}

