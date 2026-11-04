package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.dtos.FoodTruckResponsableDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PuestoDesarmableResponsableDTO;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import com.oo2.tpgrupalhito2grupo08.services.implementation.FestivalService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
@RequestMapping("/responsable/registro_unidad")
@PreAuthorize("hasRole('RESPONSABLE')")
public class UnidadDeVentaResponsableController {
    private static final String FOODTRUCK_ATTR = "foodTruck";
    private static final String PUESTO_ATTR = "puesto";
    private static final String TIPO_ATTR = "tipo";
    private static final String FESTIVALES_ATTR = "festivales";

    private final IUnidadDeVentaService unidadService;
    private final FestivalService festivalService;
    public UnidadDeVentaResponsableController(IUnidadDeVentaService unidadService, FestivalService festivalService) {
        this.unidadService = unidadService;
        this.festivalService = festivalService;
    }

    @GetMapping("")
    public ModelAndView index() {
        return formulario(new FoodTruckResponsableDTO(), new PuestoDesarmableResponsableDTO(), "foodtruck");
    }

    @PostMapping("/foodtruck")
    public ModelAndView registrarFoodTruck(@Valid @ModelAttribute(FOODTRUCK_ATTR) FoodTruckResponsableDTO dto,
                                           BindingResult result,
                                           RedirectAttributes redirect,
                                           Principal principal) {
        if (!result.hasErrors()) {
            try {
                unidadService.registrarFoodTruckResponsable(dto, principal.getName());
                redirect.addFlashAttribute("mensaje", "Unidad de venta foodtruck creada");
                return new ModelAndView(new RedirectView(ViewRouteHelper.HOME_ROOT));
            } catch (IllegalArgumentException e) {
                result.reject("registro.error", e.getMessage());
            }
        }
        ModelAndView mAV = formulario(dto, new PuestoDesarmableResponsableDTO(), "foodtruck");
        mAV.addAllObjects(result.getModel());
        return mAV;
    }

    @PostMapping("/puesto")
    public ModelAndView registrarPuesto(@Valid @ModelAttribute(PUESTO_ATTR) PuestoDesarmableResponsableDTO dto,
                                        BindingResult result,
                                        RedirectAttributes redirect,
                                        Principal principal) {
        if (!result.hasErrors()) {
            try {
                unidadService.registrarPuestoDesarmableResponsable(dto, principal.getName());
                redirect.addFlashAttribute("mensaje", "Unidad de venta puesto desarmable creada");
                return new ModelAndView(new RedirectView(ViewRouteHelper.HOME_ROOT));
            } catch (IllegalArgumentException e) {
                result.reject("registro.error", e.getMessage());
            }
        }
        ModelAndView mAV = formulario(new FoodTruckResponsableDTO(), dto, "puesto");
        mAV.addAllObjects(result.getModel());
        return mAV;
    }

    private ModelAndView formulario(FoodTruckResponsableDTO foodTruck, PuestoDesarmableResponsableDTO puesto, String tipo) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.RESPONSABLE_REGISTRO);
        mAV.addObject(FOODTRUCK_ATTR, foodTruck);
        mAV.addObject(PUESTO_ATTR, puesto);
        mAV.addObject(TIPO_ATTR, tipo);
        mAV.addObject(FESTIVALES_ATTR, festivalService.traerActivos());
        return mAV;
    }
}
