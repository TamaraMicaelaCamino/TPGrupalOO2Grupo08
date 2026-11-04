package com.oo2.tpgrupalhito2grupo08.controllers;
import com.oo2.tpgrupalhito2grupo08.helpers.ViewRouteHelper;
import com.oo2.tpgrupalhito2grupo08.services.IEmpleadoService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;

@Controller
@PreAuthorize("hasRole('EMPLEADO')")
@RequestMapping("/empleado")
public class EmpleadoController {

    private static final String FESTIVALES_UNIDADES_ATTR = "festivales";
    private static final String ACTUAL_ATTR = "actual";
    private static final String CAJERO_ATTR = "esCajero";
    private static final String TIPO_ATTR = "tipoPersonal";
    private final IEmpleadoService empleadoService;

    public EmpleadoController(IEmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ModelAndView panel(Principal principal) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.EMPLEADO_PANEL);
        mAV.addObject(CAJERO_ATTR, empleadoService.esCajero(principal.getName()));
        mAV.addObject(TIPO_ATTR, empleadoService.obtenerTipoPersonal(principal.getName()));
        mAV.addObject("puedeRegistrarPedidos", empleadoService.puedeRegistrarPedidos(principal.getName())
        );
        return mAV;
    }

    @GetMapping("/informacion")
    public ModelAndView miInformacion(Principal principal) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.EMPLEADO_INFO);
        mAV.addObject(FESTIVALES_UNIDADES_ATTR, empleadoService.misFestivalesYUnidades(principal.getName()));
        return mAV;
    }

    @GetMapping("/actualidad")
    public ModelAndView miTrabajoActual(Principal principal) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.EMPLEADO_ACTUAL);
        mAV.addObject(ACTUAL_ATTR, empleadoService.miTrabajoActual(principal.getName()));
        return mAV;
    }

    @GetMapping("/sueldos")
    public ModelAndView misSueldos(Principal principal) {
        ModelAndView mAV = new ModelAndView(ViewRouteHelper.EMPLEADO_SUELDOS);
        mAV.addObject("sueldos", empleadoService.miSueldo(principal.getName()));
        return mAV;
    }

    @GetMapping("/sueldos/{idLiquidacion}/recibo")
    public ResponseEntity<byte[]> descargarRecibo(@PathVariable Long idLiquidacion, Principal principal) {
        byte[] pdf = empleadoService.generarRecibo(principal.getName(), idLiquidacion);

        if (pdf == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("recibo-sueldo-" + idLiquidacion + ".pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }





}