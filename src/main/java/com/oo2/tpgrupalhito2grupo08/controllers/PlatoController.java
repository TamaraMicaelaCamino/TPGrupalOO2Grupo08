package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.implementation.PlatoService;
import com.oo2.tpgrupalhito2grupo08.services.implementation.UnidadDeVentaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@PreAuthorize("hasRole('RESPONSABLE')")
@RequestMapping("/responsable/platos")
@Controller
public class PlatoController {

    private final PlatoService platoService;
    private final UnidadDeVentaService unidadDeVentaService;

    public PlatoController( PlatoService platoService, UnidadDeVentaService unidadDeVentaService){
        this.platoService = platoService;
        this.unidadDeVentaService = unidadDeVentaService;
    }
    @GetMapping
    public ModelAndView traerPlatosDeUnidad(@RequestParam(required = false) Long idUnidadDeVenta, Principal principal){
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.PLATOS_LISTA);
        mAV.addObject("platos",platoService.traerPlatoPorResponsableDeUnidad(idUnidadDeVenta,principal.getName()));
        mAV.addObject("unidades", unidadDeVentaService.listarUnidadesResponsable(principal.getName()));
        mAV.addObject("idUnidadDeVenta", idUnidadDeVenta);
        return mAV;
    }
    
}
