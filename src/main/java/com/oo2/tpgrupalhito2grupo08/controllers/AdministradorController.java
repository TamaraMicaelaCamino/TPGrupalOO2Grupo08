package com.oo2.tpgrupalhito2grupo08.controllers;
import com.oo2.tpgrupalhito2grupo08.dtos.FestivalDTO;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.IFestivalService;
import com.oo2.tpgrupalhito2grupo08.services.IUnidadDeVentaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMINISTRADOR')")
@RequestMapping("/admin")
public class AdministradorController {

    private final IFestivalService festivalService;
    private final IUnidadDeVentaService unidadDeVentaService;

    public AdministradorController(IFestivalService festivalService, IUnidadDeVentaService unidadDeVentaService) {
        this.festivalService = festivalService;
        this.unidadDeVentaService = unidadDeVentaService;
    }

    @GetMapping("")
    public ModelAndView inicio() {
        return new ModelAndView(ViewRouteHelper.ADMIN_INDEX);
    }

    @GetMapping("/festivales")
    public ModelAndView festivales() {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.ADMIN_FESTIVALES);
        mAV.addObject("festivales", festivalService.traerTodos());
        return mAV;
    }


    @GetMapping("/unidades")
    public ModelAndView unidades(@RequestParam(required = false) Long idFestival) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.ADMIN_UNIDADESVENTA);
        mAV.addObject("festivales", festivalService.traerTodos());
        mAV.addObject("idFestival", idFestival);
        if (idFestival != null) {
            mAV.addObject("unidades", unidadDeVentaService.traerPorFestival(idFestival));
        }
        return mAV;
    }


    @GetMapping("/ranking")
    public ModelAndView ranking(@RequestParam(required = false) Long idFestival) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.ADMIN_RANKING);
        mAV.addObject("festivales", festivalService.traerTodos());
        mAV.addObject("idFestival", idFestival);
        if (idFestival != null) {
            mAV.addObject("ranking", unidadDeVentaService.rankingDeUnidades(idFestival));
        }
        return mAV;
    }


    @GetMapping("/mayorcanon")
    public ModelAndView mayorCanon(@RequestParam(required = false) Long idFestival,
                                   @RequestParam(defaultValue = "1") int x) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.ADMIN_MAYORCANON);

        List<FestivalDTO> festivales = festivalService.traerTodos();
        List<Long> cantidades = new ArrayList<>();
        for (FestivalDTO f : festivales) {
            cantidades.add(unidadDeVentaService.cantidadDeUnidades(f.getIdFestival()));
        }

        mAV.addObject("festivales", festivales);
        mAV.addObject("cantidades", cantidades);
        mAV.addObject("idFestival", idFestival);
        mAV.addObject("x", x);
        if (idFestival != null) {
            mAV.addObject("reporte", unidadDeVentaService.unidadesConMayorCanon(idFestival, x));
        }
        return mAV;
    }




}
