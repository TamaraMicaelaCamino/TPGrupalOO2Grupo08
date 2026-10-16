package com.oo2.tpgrupalhito2grupo08.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;

@Controller
public class UsuarioController {

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(required=false) String error,
                        @RequestParam(required=false) String logout) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return ViewRouteHelper.USER_LOGIN;
    }


//  Despues de iniciar sesion, loginSuccess solo manda al usuario a una direccion segun su rol: /admin, /responsable o /empleado
//  Esas direcciones tienen que existir. Para que existan, hay que crear tres clases controller, una por rol, y cada una declara su direccion con @RequestMapping
//  Ejemplo:@RequestMapping("/empleado") o @RequestMapping("/admin") y etc
    @GetMapping("/loginsuccess")
    public String loginSuccess(Authentication authentication) {
        for (GrantedAuthority rol : authentication.getAuthorities()) {
            switch (rol.getAuthority()) {
                case "ROLE_ADMINISTRADOR":
                    return "redirect:/admin";
                case "ROLE_RESPONSABLE":
                    return "redirect:/responsable";
                case "ROLE_EMPLEADO":
                    return "redirect:/empleado";
            }
        }
        return "redirect:/";
    }
}
