package com.oo2.tpgrupalhito2grupo08.services.implementation;
import com.oo2.tpgrupalhito2grupo08.entities.Pedido;
import com.oo2.tpgrupalhito2grupo08.repositories.IPedidoRepository;
import com.oo2.tpgrupalhito2grupo08.services.IPedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class PedidoService implements IPedidoService {

    private final IPedidoRepository pedidoRepository;

    public PedidoService(IPedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
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




}
