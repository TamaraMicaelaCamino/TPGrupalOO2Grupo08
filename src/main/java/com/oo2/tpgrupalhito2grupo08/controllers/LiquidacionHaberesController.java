package com.oo2.tpgrupalhito2grupo08.controllers;
import java.security.Principal;
import com.oo2.tpgrupalhito2grupo08.entities.UnidadDeVenta;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.ILiquidacionHaberesService;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;

@Controller
@PreAuthorize("hasRole('RESPONSABLE')")
@RequestMapping("/responsable")
public class LiquidacionHaberesController {

    private static final String SUELDOS_ATTR = "sueldos";
    private static final String UNIDAD_ATTR = "unidad";
    private static final String FESTIVAL_ATTR = "festival";
    private final ILiquidacionHaberesService liquidacionService;
    private final IUnidadDeVentaService unidadDeVentaService;

    public LiquidacionHaberesController(ILiquidacionHaberesService liquidacionService,
                                        IUnidadDeVentaService unidadDeVentaService) {
        this.liquidacionService = liquidacionService;
        this.unidadDeVentaService = unidadDeVentaService;
    }

    @GetMapping("/sueldos")
    public ModelAndView verSueldos(Principal principal) {
        UnidadDeVenta unidad = unidadDeVentaService.buscarResponsableUnidad(principal.getName());
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.RESPONSABLE);

        if (unidad != null) {
            mAV.setViewName(ViewRouteHelper.RESPONSABLE_SUELDOS);
            mAV.addObject(SUELDOS_ATTR, liquidacionService.liquidarUnidad(unidad.getIdUnidadDeVenta()));
            mAV.addObject(UNIDAD_ATTR, unidad.getNombre());
            mAV.addObject(FESTIVAL_ATTR,unidad.getFestival().getNombre());
        }

        return mAV;
    }






}