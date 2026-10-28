package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.implementation.PedidoService;
import com.oo2.tpgrupalhito2grupo08.services.implementation.UnidadDeVentaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@PreAuthorize("hasRole('RESPONSABLE')")
@Controller
@RequestMapping("responsable/pedidos")
public class PedidoResponsableController {

    private final PedidoService pedidoService;
    private final UnidadDeVentaService unidadDeVentaService;

    public PedidoResponsableController(PedidoService pedidoService, UnidadDeVentaService unidadDeVentaService) {
        this.pedidoService = pedidoService;
        this.unidadDeVentaService = unidadDeVentaService;
    }

    @GetMapping("")
    public ModelAndView verPedidos(@RequestParam(required = false) Long idPedido, Long idUnidadDeVenta, Principal principal) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.RESPONSABLE_PEDIDOS);
        mAV.addObject("pedidos", pedidoService.pedidosPorUnidadYResponsable(idUnidadDeVenta, principal.getName()));
        mAV.addObject("unidades", unidadDeVentaService.listarUnidadesResponsable(principal.getName()));
        mAV.addObject("idUnidadDeVenta", idUnidadDeVenta);
        mAV.addObject("idPedido", idPedido);
        if (idPedido != null) {
            mAV.addObject("pedidoSeleccionado", pedidoService.traerPorIdYResponsable(idPedido, principal.getName()));
        }
        return mAV;
    }
}