package com.oo2.tpgrupalhito2grupo08.controllers;

import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/responsable")
@PreAuthorize("hasRole('RESPONSABLE')")
public class ResponsableController {

    @GetMapping("")
    public String panel(){
        return ViewRouteHelper.RESPONSABLE_PANEL;
    }
}
