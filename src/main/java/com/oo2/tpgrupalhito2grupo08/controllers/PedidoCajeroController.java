package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.dtos.AltaDetallePedidoDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.AltaPedidoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.implementation.EmpleadoService;
import com.oo2.tpgrupalhito2grupo08.services.implementation.PedidoService;
import com.oo2.tpgrupalhito2grupo08.services.implementation.PlatoService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@PreAuthorize("hasRole('EMPLEADO')")
@Controller
@RequestMapping("/empleado/")
public class PedidoCajeroController {

    private final PedidoService pedidoService;
    private final EmpleadoService empleadoService;
    public PedidoCajeroController(PedidoService pedidoService, EmpleadoService empleadoService) {
        this.pedidoService = pedidoService;
        this.empleadoService = empleadoService;
    }
/*
    @GetMapping
    public ModelAndView panel(Principal principal) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.EMPLEADO_PANEL);
        mAV.addObject("esCajero", empleadoService.esCajero(principal.getName()));
        mAV.addObject("tipoPersonal", empleadoService.obtenerTipoPersonal(principal.getName()));
        return mAV;
    }
*/
    @GetMapping("/nuevoPedido")
    public ModelAndView formPedido(Principal principal){
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.EMPLEADO_FORM_PEDIDO);
        AltaPedidoDTO pedido = new AltaPedidoDTO();
        pedido.getDetalles().add(new AltaDetallePedidoDTO());

        mAV.addObject("pedido", pedido);
        mAV.addObject("platos",pedidoService.traerPlatosDelCajero(principal.getName()));
        return mAV;
    }

    @PostMapping("/pedidos/agregar-detalle")
    public ModelAndView agregarDetalle(
            @ModelAttribute("pedido") AltaPedidoDTO pedido,
            BindingResult result,
            Principal principal) {

        pedido.getDetalles().add(new AltaDetallePedidoDTO());

        ModelAndView mAV =
                new ModelAndView(ViewRouteHelper.EMPLEADO_FORM_PEDIDO);

        mAV.addAllObjects(result.getModel());

        mAV.addObject(
                "platos",
                pedidoService.traerPlatosDelCajero(principal.getName())
        );

        return mAV;
    }

    @PostMapping("/pedidos/quitar-detalle")
    public ModelAndView quitarDetalle(
            @ModelAttribute("pedido") AltaPedidoDTO pedido,
            BindingResult result,
            @RequestParam("indice") int indice,
            Principal principal) {

        if (indice < 0 || indice >= pedido.getDetalles().size()) {
            result.reject(
                    "detalle.inexistente",
                    "El detalle seleccionado no existe"
            );
        } else if (!result.hasErrors()) {
            pedido.getDetalles().remove(indice);

            if (pedido.getDetalles().isEmpty()) {
                pedido.getDetalles().add(new AltaDetallePedidoDTO());
            }
        }

        ModelAndView mAV =
                new ModelAndView(ViewRouteHelper.EMPLEADO_FORM_PEDIDO);

        mAV.addAllObjects(result.getModel());

        mAV.addObject(
                "platos",
                pedidoService.traerPlatosDelCajero(principal.getName())
        );

        return mAV;
    }
    @PostMapping("/pedidos")
    public ModelAndView registrarPedido(@Valid @ModelAttribute("pedido") AltaPedidoDTO altaPedidoDTO,
                                        BindingResult result,
                                        RedirectAttributes redirectAttributes,
                                        Principal principal){
        if(!result.hasErrors()){
            try{
                Long idPedido = pedidoService.altaPedido(altaPedidoDTO, principal.getName());
                redirectAttributes.addFlashAttribute("mensaje","Pedido numero " + idPedido + " registrado");
                return new ModelAndView("redirect:/empleado");
            }catch (IllegalArgumentException e){
            result.reject("registro.error", e.getMessage());
            }
        }
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.EMPLEADO_FORM_PEDIDO);
        mAV.addObject("pedido", altaPedidoDTO);
        mAV.addObject("platos", pedidoService.traerPlatosDelCajero(principal.getName()));
        return mAV;
    }
}
