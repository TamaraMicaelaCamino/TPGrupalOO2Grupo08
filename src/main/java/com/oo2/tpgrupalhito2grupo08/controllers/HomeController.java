package com.oo2.tpgrupalhito2grupo08.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.IFestivalService;

@Controller
public class HomeController {

    private final IFestivalService festivalService;

    public HomeController(IFestivalService festivalService) {
        this.festivalService = festivalService;
    }


    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.INDEX);
        mAV.addObject("festivales", festivalService.traerActivos());
        return mAV;
    }


}