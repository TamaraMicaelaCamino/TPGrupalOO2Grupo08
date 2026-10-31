package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/responsable")
@PreAuthorize("hasRole('RESPONSABLE')")
public class ResponsableController {

    public static final String REPORTE_FESTIVAL_RECAUDACION = "reporte";
    private final IUnidadDeVentaService unidadService;

    public ResponsableController(IUnidadDeVentaService unidadService) {
        this.unidadService = unidadService;
    }

    @GetMapping("")
    public String panel() {
        return ViewRouteHelper.RESPONSABLE_PANEL;
    }



    @GetMapping("/reporte")
    public ModelAndView reporte(@RequestParam(required = false) Long idFestival,
                                Principal principal) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.RESPONSABLE_REPORTE);
        mAV.addObject("festivales", unidadService.festivalesDelResponsable(principal.getName()));
        mAV.addObject("idFestival", idFestival);
        if (idFestival != null) {
            mAV.addObject(REPORTE_FESTIVAL_RECAUDACION,
                    unidadService.reporteRecaudacion(idFestival, principal.getName()));
        }
        return mAV;
    }


}