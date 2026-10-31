package com.oo2.tpgrupalhito2grupo08.controllers;
import java.security.Principal;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.ILiquidacionHaberesService;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasRole('RESPONSABLE')")
@RequestMapping("/responsable")
public class LiquidacionHaberesController {

    private static final String SUELDOS_ATTR = "sueldos";
    private final ILiquidacionHaberesService liquidacionService;
    private final IUnidadDeVentaService unidadDeVentaService;

    public LiquidacionHaberesController(ILiquidacionHaberesService liquidacionService,
                                        IUnidadDeVentaService unidadDeVentaService) {
        this.liquidacionService = liquidacionService;
        this.unidadDeVentaService = unidadDeVentaService;
    }

    @GetMapping("/sueldos")
    public ModelAndView verSueldos(
            @RequestParam(required = false) Long idUnidadDeVenta,
            Principal principal) {

        String username = principal.getName();

        ModelAndView mAV =
                new ModelAndView(ViewRouteHelper.RESPONSABLE_SUELDOS);

        mAV.addObject(
                "unidades",
                unidadDeVentaService.listarUnidadesResponsable(username));

        mAV.addObject("idUnidadDeVenta", idUnidadDeVenta);

        mAV.addObject(
                SUELDOS_ATTR,
                liquidacionService.consultarPorResponsable(
                        idUnidadDeVenta, username));

        return mAV;
    }

    @PostMapping("/sueldos/liquidar")
    public ModelAndView liquidarUnidad(@RequestParam Long idUnidadDeVenta, Principal principal, RedirectAttributes redirect) {

        UnidadDeVenta unidad = unidadDeVentaService.buscarUnidadDelResponsable(idUnidadDeVenta, principal.getName());
        try {
            liquidacionService.liquidarUnidad(unidad.getIdUnidadDeVenta());
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return new ModelAndView(ViewRouteHelper.RESPONSABLE_SUELDO_RD + idUnidadDeVenta);
    }






}