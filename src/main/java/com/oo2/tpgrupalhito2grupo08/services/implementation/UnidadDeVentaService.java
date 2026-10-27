package com.oo2.tpgrupalhito2grupo08.services.implementation;
import com.oo2.tpgrupalhito2grupo08.dtos.*;
import com.oo2.tpgrupalhito2grupo08.entities.*;
import com.oo2.tpgrupalhito2grupo08.services.IFestivalService;
import com.oo2.tpgrupalhito2grupo08.services.IPedidoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oo2.tpgrupalhito2grupo08.entities.enums.RolEnum;
import com.oo2.tpgrupalhito2grupo08.repositories.IUnidadDeVentaRepository;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import com.oo2.tpgrupalhito2grupo08.services.IUsuarioService;

import java.util.*;

@Service("unidadDeVentaService")
public class UnidadDeVentaService implements IUnidadDeVentaService {

    private final IUnidadDeVentaRepository unidadRepository;
    private final IFestivalService festivalService;
    private final IUsuarioService usuarioService;
    private final ModelMapper modelMapper;
    private final IPedidoService pedidoService;
    private IllegalArgumentException illegalArgumentException;

    public UnidadDeVentaService(IUnidadDeVentaRepository unidadRepository,
                                IFestivalService festivalService,
                                IUsuarioService usuarioService,IPedidoService pedidosService,
                                ModelMapper modelMapper) {
        this.unidadRepository = unidadRepository;
        this.festivalService = festivalService;
        this.usuarioService = usuarioService;
        this.pedidoService=pedidosService;
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
    public List<UnidadDeVenta> listarUnidadesResponsable(String username) {
        return unidadRepository.findByResponsable_Username(username);
    }

    @Override
    public UnidadDeVenta buscarUnidadDelResponsable(Long idUnidadDeVenta, String username) {
        return unidadRepository.findByIdUnidadDeVentaAndResponsable_Username(idUnidadDeVenta,username)
                .orElseThrow(
                        ()-> new IllegalArgumentException("La unidad no existe o no pertenece al responsable"));
    }

    // validaciones

    private void validarCodigo(String codigo) {
        if (unidadRepository.existsByCodigoIgnoreCase(codigo)) {
            throw new IllegalArgumentException("Ese codigo ya esta en uso");
        }
    }

    private void validarPatente(String patente) {
        if (unidadRepository.existsFoodTruckByPatente(patente)) {
            throw new IllegalArgumentException("Esa patente ya esta registrada");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<UnidadDeVentaDTO> traerPorFestival(Long idFestival) {
        List<UnidadDeVentaDTO> resultado = new ArrayList<>();

        for (UnidadDeVenta u : unidadRepository.buscarPorFestival(idFestival)) {
            UnidadDeVentaDTO dto;
            if (u instanceof FoodTruck) {
                dto = modelMapper.map(u, FoodTruckDTO.class);
            } else {
                dto = modelMapper.map(u, PuestoDesarmableDTO.class);
            }

            //se copia a mano
            dto.getUsuario().setUsername(u.getResponsable().getUsername());

            resultado.add(dto);
        }
        return resultado;
    }



    @Override
    @Transactional(readOnly = true)
    public List<RankingUnidadDTO> rankingDeUnidades(Long idFestival) {
        List<UnidadDeVenta> unidades = unidadRepository.buscarPorFestival(idFestival);
        List<RankingUnidadDTO> ranking = new ArrayList<>();

        for (UnidadDeVenta u : unidades) {
            String tipo;
            if (u instanceof FoodTruck) {
                tipo = "Food Truck";
            } else {
                tipo = "Puesto Desarmable";
            }
            double recaudacion = pedidoService.recaudacionPorUnidad(u.getIdUnidadDeVenta());
            ranking.add(new RankingUnidadDTO(u.getIdUnidadDeVenta(), u.getNombre(),
                    tipo, u.getResponsable().getUsername(), recaudacion));
        }

        // Ordenar de mayor a menor recaudacion

        ranking.sort((a, b) -> Double.compare(b.getRecaudacion(), a.getRecaudacion()));

        return ranking;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteMayoresCanonDTO> unidadesConMayorCanon(Long idFestival, int x) {
        List<ReporteMayoresCanonDTO> reporte = new ArrayList<>();

        for (UnidadDeVenta u : unidadRepository.buscarPorFestival(idFestival)) {
            String tipo;
            if (u instanceof FoodTruck) {
                tipo = "Food Truck";
            } else {
                tipo = "Puesto Desarmable";
            }
            double canon = u.calcularCanon(u.getFestival().getCosto());
            reporte.add(new ReporteMayoresCanonDTO(u.getNombre(), u.getCodigo(), tipo, canon));
        }

        // Ordenar de mayor a menor canon
        reporte.sort((a, b) -> Double.compare(b.getCanon(), a.getCanon()));

        // Quedarse con las primeras (lo que ingreso por parametro)
        List<ReporteMayoresCanonDTO> primeras = new ArrayList<>();
        for (int i = 0; i < reporte.size() && i < x; i++) {
            primeras.add(reporte.get(i));
        }
        return primeras;
    }

    @Override
    @Transactional(readOnly = true)
    public long cantidadDeUnidades(Long idFestival) {
        return unidadRepository.contarPorFestival(idFestival);
    }




}

