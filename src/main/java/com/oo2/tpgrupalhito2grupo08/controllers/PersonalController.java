package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.dtos.CajeroDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalCocinaDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.PersonalDTO;
import com.oo2.tpgrupalhito2grupo08.entities.enums.Categoria;
import com.oo2.tpgrupalhito2grupo08.entities.enums.TurnoEnum;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.implementation.PersonalService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@PreAuthorize("hasRole('RESPONSABLE')")
@Controller
@RequestMapping("/responsable/empleados")
public class PersonalController {
    private final PersonalService personalService;

    public PersonalController(PersonalService personalService){
        this.personalService=personalService;
    }
    @GetMapping
    public ModelAndView verEmpleados(Principal principal){
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.PERSONAL_LISTA);
        mAV.addObject("empleados", personalService.traerPersonalPorResponsableDeUnidad(principal.getName()));
        return mAV;
    }

    @GetMapping({"/nuevo", "/nuevo/cajero"})
    public ModelAndView formularioCajero() {
        ModelAndView mAV =
                new ModelAndView(ViewRouteHelper.PERSONAL_ALTA);

        mAV.addObject("empleado", new CajeroDTO());
        mAV.addObject("tipo", "cajero");
        mAV.addObject("turnos", TurnoEnum.values());

        return mAV;
    }

    @GetMapping("/nuevo/cocina")
    public ModelAndView formularioCocina() {
        ModelAndView mAV =
                new ModelAndView(ViewRouteHelper.PERSONAL_ALTA);

        mAV.addObject("empleado", new PersonalCocinaDTO());
        mAV.addObject("tipo", "cocina");
        mAV.addObject("categorias", Categoria.values());
        return mAV;
    }

    @PostMapping("/cajero")
    public ModelAndView registrarCajero (@Valid @ModelAttribute("empleado") CajeroDTO dto,
                                         BindingResult result,
                                         RedirectAttributes redirectAttributes,
                                         Principal principal){
        if(!result.hasErrors()){
            try {
                personalService.resgistrarCajero(dto, principal.getName());
                redirectAttributes.addFlashAttribute("mensaje","Cajero registrado correctamente");
                return new ModelAndView("redirect:/responsable/empleados");
            }catch (IllegalArgumentException e){
                result.reject("registro.error", e.getMessage());
            }
        }
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.PERSONAL_ALTA);
        mAV.addObject("empleado", dto);
        mAV.addObject("tipo", "cajero");
        mAV.addObject("turnos", TurnoEnum.values());
        return mAV;
    }

    @PostMapping("/cocina")
    public ModelAndView registrarCocina (@Valid @ModelAttribute("empleado") PersonalCocinaDTO dto,
                                         BindingResult result,
                                         RedirectAttributes redirectAttributes,
                                         Principal principal){
        if(!result.hasErrors()){
            try {
                personalService.registrarPersonalCocina(dto, principal.getName());
                redirectAttributes.addFlashAttribute("mensaje","Personal de Cocina registrado correctamente");
                return new ModelAndView("redirect:/responsable/empleados");
            }catch (IllegalArgumentException e){
                result.reject("registro.error", e.getMessage());
            }
        }
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.PERSONAL_ALTA);
        mAV.addObject("empleado", dto);
        mAV.addObject("tipo", "cocina");
        mAV.addObject("categorias", Categoria.values());
        return mAV;
    }
}
