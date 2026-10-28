package com.oo2.tpgrupalhito2grupo08.services.implementation;
import com.oo2.tpgrupalhito2grupo08.dtos.AltaDetallePedidoDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.AltaPedidoDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PedidoDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PlatoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.*;
import com.oo2.tpgrupalhito2grupo08.repositories.IPedidoRepository;
import com.oo2.tpgrupalhito2grupo08.repositories.IPersonalRepository;
import com.oo2.tpgrupalhito2grupo08.repositories.IPlatoRepository;
import com.oo2.tpgrupalhito2grupo08.services.IPedidoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class PedidoService implements IPedidoService {

    private final IPedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;
    private final IPersonalRepository personalRepository;
    private final PlatoService platoService;
    public PedidoService(
            IPedidoRepository pedidoRepository,
            ModelMapper modelMapper,
            IPersonalRepository personalRepository, PlatoService platoService) {

        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
        this.personalRepository = personalRepository;
        this.platoService = platoService;
    }

    @Override
    @Transactional(readOnly = true)
    public double recaudacionPorUnidad(Long idUnidadDeVenta) {
        double recaudacion = 0;
        List<Pedido> pedidos = pedidoRepository.buscarPorUnidad(idUnidadDeVenta);
        for (Pedido p : pedidos) {
            recaudacion += p.calcularTotal();
        }
        return recaudacion;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PedidoDTO> pedidosPorUnidadYResponsable(Long idUnidadDeVenta, String username) {
        List<PedidoDTO> resultado = new ArrayList<>();

        for (Pedido pedido :
                pedidoRepository.buscarPorResponsableYUnidad(idUnidadDeVenta, username)) {

            PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
            dto.setTotal(pedido.calcularTotal());
            resultado.add(dto);
        }

        return resultado;
    }

    @Override
    public PedidoDTO traerPorIdYResponsable(Long idPedido, String username){
        Pedido pedido = pedidoRepository.findByIdPedidoAndUnidadDeVenta_Responsable_Username(idPedido, username)
                .orElseThrow(
                        ()-> new IllegalArgumentException("Pedido no encontrado"));
        PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
        return dto;
    }

    @Override
    public Long altaPedido(AltaPedidoDTO altaPedidoDTO, String username)  {
        LocalDate fechaOperacion = LocalDate.now();
        UnidadDeVenta unidadDeVenta = obtenerUnidadDelCajero(username, fechaOperacion);
        if (altaPedidoDTO.getDetalles() == null
                || altaPedidoDTO.getDetalles().isEmpty()) {
            throw new IllegalArgumentException(
                    "El pedido debe tener al menos un plato"
            );
        }
        Pedido nuevo = new Pedido();
        nuevo.setFechaTransaccion(fechaOperacion);
        nuevo.setUnidadDeVenta(unidadDeVenta);
        for (AltaDetallePedidoDTO detalleDTO : altaPedidoDTO.getDetalles()) {
            Plato plato = obtenerPlato(detalleDTO.getIdPlato());
            if (!plato.getUnidadDeVenta().getIdUnidadDeVenta()
                    .equals(unidadDeVenta.getIdUnidadDeVenta())) {
                throw new IllegalArgumentException(
                        "El plato no pertenece a la unidad del cajero"
                );
            }
            if (detalleDTO.getCantidad() == null
                    || detalleDTO.getCantidad() <= 0) {
                throw new IllegalArgumentException(
                        "La cantidad debe ser mayor que cero"
                );
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPlato(plato);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioVenta(plato.getPrecioVenta());

            nuevo.agregarDetalle(detalle);
        }
        pedidoRepository.save(nuevo);
        return nuevo.getIdPedido();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> traerPlatosDelCajero(String username) {
        UnidadDeVenta unidad = obtenerUnidadDelCajero(username, LocalDate.now());
        return platoService.traerPlatoPorIdUnidadDeVenta(
                unidad.getIdUnidadDeVenta()
        );
    }


    private UnidadDeVenta obtenerUnidadDelCajero(String username, LocalDate fechaOperacion)  {

        Personal personal = personalRepository
                .findByUsuario_UsernameAndActivoTrue(username)
                .orElseThrow(() -> new AccessDeniedException(
                        "No tenés un empleado activo asociado"
                ));

        if (!(personal instanceof Cajero)) {
            throw new AccessDeniedException(
                    "Solo los cajeros pueden registrar pedidos"
            );
        }

        UnidadDeVenta unidad = personal.getUnidadDeVenta();

        if (unidad == null || unidad.getFestival() == null) {
            throw new AccessDeniedException(
                    "No tenés una unidad de venta con festival asignado"
            );
        }

        if (!unidad.getFestival().estaEnCurso(fechaOperacion)) {
            throw new AccessDeniedException(
                    "Solo podés registrar pedidos durante las fechas del festival"
            );
        }

        return unidad;
    }

    private Plato obtenerPlato(Long idPlato){
        return platoService.buscarPorId(idPlato);
    }

}
