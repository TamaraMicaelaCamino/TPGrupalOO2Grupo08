package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.dtos.CierreCajaDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PedidoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.*;
import com.oo2.tpgrupalhito2grupo08.repositories.ICierreCajaRepository;
import com.oo2.tpgrupalhito2grupo08.services.ICierreCajaService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CierreCajaService implements ICierreCajaService {

    private final PersonalService personalService;
    private final PedidoService pedidoService;
    private final ICierreCajaRepository cierreCajaRepository;
    private final ModelMapper modelMapper;
    public CierreCajaService(PersonalService personalService, PedidoService pedidoService, ICierreCajaRepository cierreCajaRepository, ModelMapper modelMapper){
        this.personalService = personalService;
        this.pedidoService = pedidoService;
        this.cierreCajaRepository = cierreCajaRepository;
        this.modelMapper = modelMapper;
    }
    @Transactional
    @Override
    public CierreCaja altaCierreCaja(String usuario) {
        Cajero personal = personalService.buscarCajeroActivo(usuario);

        UnidadDeVenta unidad = personal.getUnidadDeVenta();

        if (unidad == null) {
            throw new AccessDeniedException(
                    "No tenés una unidad de venta asignada"
            );
        }

        LocalDateTime momentoCierre = LocalDateTime.now();
        LocalDate fechaOperativa = momentoCierre.toLocalDate();

        if(cierreCajaRepository.existeCierreDeCaja(personal.getUnidadDeVenta().getIdUnidadDeVenta(), fechaOperativa)){
            throw new IllegalArgumentException("La caja ya ha sido cerrada");
        }

        List<PedidoDTO> pedidos = pedidoService.pedidosPorUnidadYFecha(personal.getUnidadDeVenta().getIdUnidadDeVenta(), fechaOperativa);
        double total = 0;
        for(PedidoDTO pedidoDTO : pedidos){
            total += pedidoDTO.getTotal();
        }
        CierreCaja nuevo = new CierreCaja();
        nuevo.setCajero(personal);
        nuevo.setCantPedidos(pedidos.size());
        nuevo.setFechaOperativa(fechaOperativa);
        nuevo.setFechaHoraCierre(momentoCierre);
        nuevo.setTotalRecaudado(total);
        nuevo.setUnidad(unidad);
        return cierreCajaRepository.save(nuevo);
    }

    @Override
    public CierreCajaDTO traerCierreDelDia(String username) {
        Personal personal = personalService.buscarCajeroActivo(username);

        UnidadDeVenta unidad = personal.getUnidadDeVenta();
        if (unidad == null) {
            throw new AccessDeniedException(
                    "No tenés una unidad de venta asignada"
            );
        }
        CierreCaja cierreCaja= cierreCajaRepository.buscarUnidadPorCajero(username, LocalDate.now() );
        if (cierreCaja==null){
            return null;
        }
        return modelMapper.map(cierreCaja, CierreCajaDTO.class);
    }
}
