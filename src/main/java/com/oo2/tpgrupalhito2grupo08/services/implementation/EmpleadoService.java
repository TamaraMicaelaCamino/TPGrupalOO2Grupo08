package com.oo2.tpgrupalhito2grupo08.services.implementation;
import com.oo2.tpgrupalhito2grupo08.dtos.FestivalDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.UnidadVentaFestivalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.LiquidacionHaberes;
import com.oo2.tpgrupalhito2grupo08.entities.Personal;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import com.oo2.tpgrupalhito2grupo08.repositories.IPersonalRepository;
import com.oo2.tpgrupalhito2grupo08.services.IEmpleadoService;
import com.oo2.tpgrupalhito2grupo08.services.ILiquidacionHaberesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("empleadoService")
public class EmpleadoService implements IEmpleadoService {

    private final IPersonalRepository personalRepository;
    private final ILiquidacionHaberesService liquidacionService;
    private final ModelMapper modelMapper;

    public EmpleadoService(IPersonalRepository personalRepository,
                           ILiquidacionHaberesService liquidacionHaberesService,
                           ModelMapper modelMapper) {
        this.personalRepository = personalRepository;
        this.liquidacionService = liquidacionHaberesService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadVentaFestivalDTO> misFestivalesYUnidades(String username) {
        Personal personal = buscarPersonalActivo(username);

        List<LiquidacionHaberes> liquidaciones =
                liquidacionService.listarFinalizadosxPersonal(personal.getIdPersonal());

        List<UnidadVentaFestivalDTO> resultado = new ArrayList<>();
        for (LiquidacionHaberes l : liquidaciones) {
            resultado.add(toDTO(l.getUnidadDeVenta()));
        }
        return resultado;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean esCajero(String username) {
        Personal personal = buscarPersonalActivo(username);
        return "CAJERO".equalsIgnoreCase(personal.getTipoPersonal());
    }

    @Override
    @Transactional(readOnly = true)
    public UnidadVentaFestivalDTO miTrabajoActual(String username) {
        Personal personal = buscarPersonalActivo(username);
        UnidadDeVenta unidad = personal.getUnidadDeVenta();

        if (unidad == null || !unidad.getFestival().estaEnCurso(LocalDate.now())) {
            return null;
        }
        return toDTO(unidad);
    }
    private Personal buscarPersonalActivo(String username) {
        Personal personal = personalRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new IllegalStateException(
                        "No hay personal asociado al usuario " + username));

        if (!personal.isActivo()) {
            throw new IllegalStateException(
                    "El personal asociado al usuario " + username + " está dado de baja");
        }
        return personal;
    }


    private UnidadVentaFestivalDTO toDTO(UnidadDeVenta unidad) {
        return UnidadVentaFestivalDTO.builder()
                .festival(modelMapper.map(unidad.getFestival(), FestivalDTO.class))
                .nombreUnidad(unidad.getNombre())
                .codigoUnidad(unidad.getCodigo())
                .tipoUnidad(unidad.getTipoUnidad())
                .build();
    }

    @Override
    public String obtenerTipoPersonal(String username) {
        Optional<Personal> personal = personalRepository.findByUsuarioUsername(username);
        if (personal.isPresent()) {
            return personal.get().getTipoPersonal();
        }
        return null;
    }




}
