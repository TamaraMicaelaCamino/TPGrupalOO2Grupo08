package com.oo2.tpgrupalhito2grupo08.services.implementation;
import com.oo2.tpgrupalhito2grupo08.dtos.LiquidacionHaberesDTO;
import com.oo2.tpgrupalhito2grupo08.entities.*;
import com.oo2.tpgrupalhito2grupo08.repositories.ILiquidacionHaberesRepository;
import com.oo2.tpgrupalhito2grupo08.services.ILiquidacionHaberesService;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service("liquidacionService")
public class LiquidacionHaberesService implements ILiquidacionHaberesService{

    private final ILiquidacionHaberesRepository liquidacionRepository;
    private final IUnidadDeVentaService unidadVentaService;

    public LiquidacionHaberesService(ILiquidacionHaberesRepository liquidacionRepository,
                                     IUnidadDeVentaService unidadVentaService) {
        this.liquidacionRepository = liquidacionRepository;
        this.unidadVentaService = unidadVentaService;
    }

    //Calcula el sueldo detodo el personal activo y lo guarda
    @Override
    @Transactional
    public List<LiquidacionHaberesDTO> liquidarUnidad(Long idUnidad) {
        UnidadDeVenta unidad = unidadVentaService.obtenerUnidadPorId(idUnidad);

        List<LiquidacionHaberesDTO> resultado = new ArrayList<>();

        for (Personal p : unidad.getPersonal()) {
            if (p.isActivo()) {
                LiquidacionHaberes liq = liquidar(p, unidad);
                resultado.add(convertirADTO(liq));
            }
        }
        return resultado;
    }
   //Devuelve las liquidaciones ya guardadas de una unidad
    @Override
    @Transactional(readOnly = true)
    public List<LiquidacionHaberesDTO> traerPorUnidad(Long idUnidad) {
        List<LiquidacionHaberesDTO> resultado = new ArrayList<>();
        for (LiquidacionHaberes l : liquidacionRepository.listarPorUnidad(idUnidad)) {
            resultado.add(convertirADTO(l));
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LiquidacionHaberesDTO> consultarPorResponsable(
            Long idUnidadDeVenta, String username) {

        List<UnidadDeVenta> unidades;

        if (idUnidadDeVenta == null) {
            unidades = unidadVentaService.listarUnidadesResponsable(username);
        } else {
            UnidadDeVenta unidad =
                    unidadVentaService.buscarUnidadDelResponsable(
                            idUnidadDeVenta, username);

            unidades = List.of(unidad);
        }

        List<LiquidacionHaberesDTO> resultado = new ArrayList<>();

        for (UnidadDeVenta unidad : unidades) {
            List<LiquidacionHaberesDTO> liquidaciones =
                    traerPorUnidad(unidad.getIdUnidadDeVenta());

            resultado.addAll(liquidaciones);
        }

        return resultado;
    }

    //Devuelve las liquidaciones ya guardadas de un empleado, en todos los festivales pdf
    @Override
    @Transactional(readOnly = true)
    public List<LiquidacionHaberesDTO> traerPorPersonal(Long idPersonal) {
        List<LiquidacionHaberes> lista = liquidacionRepository.listarPorPersonal(idPersonal);
        List<LiquidacionHaberesDTO> resultado = new ArrayList<>();
        for (LiquidacionHaberes l : lista) {
            resultado.add(convertirADTO(l));
        }
        return resultado;
    }

    // calculo de una liquidacion

    private LiquidacionHaberes liquidar(Personal personal, UnidadDeVenta unidad) {
        Festival festival = unidad.getFestival();
        Costo costo = festival.getCosto();

        double bruto = personal.calcularSueldoMensual(costo, festival.getFechaFin());
        double total = bruto * festival.getPorcentajePorDias();

        // si ya estaba liquidado, se actualiza en vez de duplicar
        LiquidacionHaberes liq = liquidacionRepository.buscarPersonalYUnidad(personal, unidad)
                .orElse(new LiquidacionHaberes());

        liq.setPersonal(personal);
        liq.setUnidadDeVenta(unidad);
        liq.setSueldoBase(costo.getSueldoBase());
        liq.setAdicional(bruto - costo.getSueldoBase());
        liq.setTotal(total);

        return liquidacionRepository.save(liq);
    }

    // conversion a DTO
    private LiquidacionHaberesDTO convertirADTO(LiquidacionHaberes liq) {
        Personal p = liq.getPersonal();
        UnidadDeVenta u = liq.getUnidadDeVenta();
        Festival f = u.getFestival();

        return LiquidacionHaberesDTO.builder()
                .idLiquidacion(liq.getIdLiquidacion())
                .nombreCompleto(p.getNombre() + " " + p.getApellido())
                .tipoPersonal(p.getTipoPersonal())
                .nombreUnidad(u.getNombre())
                .nombreFestival(f.getNombre())
                .diasFestival(f.getDuracionEnDias())
                .sueldoBase(liq.getSueldoBase())
                .factor(f.getPorcentajePorDias())
                .adicional(liq.getAdicional())
                .total(liq.getTotal())
                .antiguedad(p.getAntiguedadEnAnios(f.getFechaFin()))
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public List<LiquidacionHaberes> listarFinalizadosxPersonal(Long idPersonal) {
        return liquidacionRepository.listarFinalizadosPorPersonal(idPersonal, LocalDate.now());
    }





}