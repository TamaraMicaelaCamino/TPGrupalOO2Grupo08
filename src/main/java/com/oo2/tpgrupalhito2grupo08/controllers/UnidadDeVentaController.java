package com.oo2.tpgrupalhito2grupo08.controllers;
import com.oo2.tpgrupalhito2grupo08.dtos.FoodTruckDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PuestoDesarmableDTO;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.IFestivalService;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/registro")
public class UnidadDeVentaController {

    private static final String FOODTRUCK_ATTR = "foodTruck";
    private static final String PUESTO_ATTR = "puesto";
    private static final String TIPO_ATTR = "tipo";
    private static final String FESTIVALES_ATTR = "festivales";

    private final IUnidadDeVentaService unidadService;
    private final IFestivalService festivalService;

    public UnidadDeVentaController(IUnidadDeVentaService unidadService,
                                   IFestivalService festivalService) {
        this.unidadService = unidadService;
        this.festivalService = festivalService;
    }

    @GetMapping("")
    public ModelAndView index() {
        return formulario(new FoodTruckDTO(), new PuestoDesarmableDTO(), "foodtruck");
    }

    @PostMapping("/foodtruck")
    public ModelAndView registrarFoodTruck(@Valid @ModelAttribute(FOODTRUCK_ATTR) FoodTruckDTO dto,
                                           BindingResult result,
                                           RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            try {
                unidadService.registrarFoodTruck(dto);
                redirect.addFlashAttribute("mensaje", "Unidad de venta foodtruck creada con su responsable");
                return new ModelAndView(new RedirectView(ViewRouteHelper.HOME_ROOT));
            } catch (IllegalArgumentException e) {
                result.reject("registro.error", e.getMessage());
            }
        }
        ModelAndView mAV = formulario(dto, new PuestoDesarmableDTO(), "foodtruck");
        mAV.addAllObjects(result.getModel());
        return mAV;
    }

    @PostMapping("/puesto")
    public ModelAndView registrarPuesto(@Valid @ModelAttribute(PUESTO_ATTR) PuestoDesarmableDTO dto,
                                        BindingResult result,
                                        RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            try {
                unidadService.registrarPuestoDesarmable(dto);
                redirect.addFlashAttribute("mensaje", "Unidad de venta puesto desarmable creada con su responsable");
                return new ModelAndView(new RedirectView(ViewRouteHelper.HOME_ROOT));
            } catch (IllegalArgumentException e) {
                result.reject("registro.error", e.getMessage());
            }
        }
        ModelAndView mAV = formulario(new FoodTruckDTO(), dto, "puesto");
        mAV.addAllObjects(result.getModel());
        return mAV;
    }

    private ModelAndView formulario(FoodTruckDTO foodTruck, PuestoDesarmableDTO puesto, String tipo) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.REGISTRO);
        mAV.addObject(FOODTRUCK_ATTR, foodTruck);
        mAV.addObject(PUESTO_ATTR, puesto);
        mAV.addObject(TIPO_ATTR, tipo);
        mAV.addObject(FESTIVALES_ATTR, festivalService.traerTodos());
        return mAV;
    }
}
