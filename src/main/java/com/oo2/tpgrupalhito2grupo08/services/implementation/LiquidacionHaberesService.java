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
                LiquidacionHaberes liq = liquidar(p, unidad); // se pasa el empleado (p) y su unidad y se lo guarda en un entidad
                resultado.add(convertirADTO(liq));//recibe la entidad liq como parametro
            }
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
        LiquidacionHaberes liq = liquidacionRepository.buscarPersonalYUnidad(personal, unidad)//Busca si ya existe una liquidación de este empleado en esta unidad
                .orElse(new LiquidacionHaberes());//si tiene algo dame eso, si está vacía dame una liquidación nueva
       // se guarda los datos de cuentas y a quien pertenece y en que unidad
        liq.setPersonal(personal);
        liq.setUnidadDeVenta(unidad);
        liq.setSueldoBase(costo.getSueldoBase());
        liq.setAdicional(bruto - costo.getSueldoBase());//antiguedad o plus
        liq.setTotal(total);

        return liquidacionRepository.save(liq);//Inserta o actualiza en la base
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


}