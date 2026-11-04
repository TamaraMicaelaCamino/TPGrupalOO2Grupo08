package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.dtos.AltaDetallePedidoDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.AltaPedidoDTO;
import com.oo2.tpgrupalhito2grupo08.entities.*;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.implementation.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@PreAuthorize("hasRole('EMPLEADO')")
@Controller
@RequestMapping("/empleado/")
public class PedidoCajeroController {

    private final PedidoService pedidoService;
    private final UnidadDeVentaService unidadDeVentaService;
    private final UsuarioService usuarioService;
    private final PersonalService personalService;
    private final CierreCajaService cierreCajaService;
    public PedidoCajeroController(PedidoService pedidoService, UnidadDeVentaService unidadDeVentaService, UsuarioService usuarioService, PersonalService personalService, EmpleadoService empleadoService, PersonalService personalService1, CierreCajaService cierreCajaService) {
        this.pedidoService = pedidoService;
        this.unidadDeVentaService = unidadDeVentaService;
        this.usuarioService = usuarioService;
        this.personalService = personalService1;
        this.cierreCajaService = cierreCajaService;
    }

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

    @GetMapping("/caja")
    public ModelAndView cierreCaja(
            @RequestParam(required = false) Long idPedido,
            Principal principal) {

        Personal personal = personalService.buscarCajeroActivo(principal.getName());

        ModelAndView mAV =
                new ModelAndView(ViewRouteHelper.EMPLEADO_CAJA);

        mAV.addObject("pedidos",
                pedidoService.pedidosPorUnidadYFecha(personal.getUnidadDeVenta().getIdUnidadDeVenta(), LocalDate.now())
        );

        mAV.addObject("idPedido", idPedido);

        if (idPedido != null) {
            mAV.addObject(
                    "pedidoSeleccionado",
                    pedidoService.traerPedidoPorIdYCajero(idPedido, principal.getName())
            );
        }
        mAV.addObject("cierre", cierreCajaService.traerCierreDelDia(principal.getName()));
        return mAV;
    }

    @PostMapping("/caja")
    public ModelAndView realizarCierreCaja(
            RedirectAttributes redirectAttributes,
            Principal principal) {

        try {
            CierreCaja cierre =
                    cierreCajaService.altaCierreCaja(principal.getName());

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Cierre de caja " + cierre.getIdCierreCaja()
                            + " registrado correctamente"
            );
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new ModelAndView("redirect:/empleado/caja");
    }
}
