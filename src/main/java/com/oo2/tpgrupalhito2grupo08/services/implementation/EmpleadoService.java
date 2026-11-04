package com.oo2.tpgrupalhito2grupo08.services.implementation;
import com.oo2.tpgrupalhito2grupo08.components.ReciboSueldoPdf;
import com.oo2.tpgrupalhito2grupo08.dtos.FestivalDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.LiquidacionHaberesDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.ReciboSueldoPdfDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.UnidadVentaFestivalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Cajero;
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
    private final PersonalService personalService;
    private final ILiquidacionHaberesService liquidacionService;
    private final CierreCajaService cierreCajaService;                      
    private final ReciboSueldoPdf reciboPdf;
    private final ModelMapper modelMapper;

    public EmpleadoService(IPersonalRepository personalRepository, PersonalService personalService,
                           ILiquidacionHaberesService liquidacionHaberesService, ReciboSueldoPdf reciboPdf, CierreCajaService cierreCajaService,
                           ModelMapper modelMapper) {
        this.personalRepository = personalRepository;
        this.personalService = personalService;
        this.liquidacionService = liquidacionHaberesService;
        this.cierreCajaService = cierreCajaService;
        this.reciboPdf = reciboPdf;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadVentaFestivalDTO> misFestivalesYUnidades(String username) {
        Personal personal = personalService.buscarPersonalActivo(username);

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
        Personal personal = personalService.buscarPersonalActivo(username);
        return "CAJERO".equalsIgnoreCase(personal.getTipoPersonal());
    }

    @Override
    @Transactional(readOnly = true)
    public UnidadVentaFestivalDTO miTrabajoActual(String username) {
        Personal personal = personalService.buscarPersonalActivo(username);
        UnidadDeVenta unidad = personal.getUnidadDeVenta();

        if (!tieneFestivalVigente(unidad)) {
            return null;
        }
        return toDTO(unidad);
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

    @Override
    @Transactional(readOnly = true)
    public boolean puedeRegistrarPedidos(String username) {
        Personal personal = personalService.buscarPersonalActivo(username);

        return personal instanceof Cajero && tieneFestivalVigente(personal.getUnidadDeVenta()) && cajaAbierta(username);
    }
    @Override
    public List<LiquidacionHaberesDTO> miSueldo(String username) {
        Optional<Personal> personal = personalRepository.findByUsuarioUsername(username);
        if (personal.isEmpty()) {
            return new ArrayList<>();
        }
        return liquidacionService.traerPorPersonal(personal.get().getIdPersonal());
    }

    @Override
    public byte[] generarRecibo(String username, Long idLiquidacion) {
        ReciboSueldoPdfDTO recibo = liquidacionService.armarRecibo(idLiquidacion, username);
        if (recibo == null) {
            return null;
        }
        return reciboPdf.generar(recibo);
    }

    private boolean tieneFestivalVigente(UnidadDeVenta unidad) {
        return unidad != null
                && unidad.getFestival() != null
                && unidad.getFestival().estaEnCurso(LocalDate.now());
    }

    private boolean cajaAbierta(String username){
        return cierreCajaService.traerCierreDelDia(username) == null;
    }
}
