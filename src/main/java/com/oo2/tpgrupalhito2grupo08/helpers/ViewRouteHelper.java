package com.oo2.tpgrupalhito2grupo08.helpers;

public class ViewRouteHelper {

/**** Views ****/

//HOME
public final static String INDEX = "home/index";

//USER
public final static String USER_LOGIN = "user/login";

//REGISTRO
public final static String REGISTRO = "registro/index";

//RESPONSABLE
public final static String RESPONSABLE_PANEL = "responsable/panel";
public final static String RESPONSABLE_SUELDOS = "responsable/sueldos";
//PERSONAL
public final static  String PERSONAL_LISTA= "responsable/lista_empleados";
public final static String PERSONAL_ALTA = "responsable/form_empleados";

//PLATOS
public final static String PLATOS_LISTA = "responsable/lista_platos";
public final static String PLATOS_ALTA = "responsable/form_platos";
//ADMINISTRADOR
public final static String ADMIN_INDEX = "administrador/inicio";
public final static String ADMIN_FESTIVALES = "administrador/festivales";
public final static String ADMIN_UNIDADESVENTA = "administrador/unidades";
public final static String ADMIN_RANKING = "administrador/ranking";
public final static String ADMIN_MAYORCANON = "administrador/mayorcanon";

//EMPLEADO

public final static String EMPLEADO_PANEL= "empleado/panel";
public final static String EMPLEADO_INFO = "empleado/informacion";
public final static String EMPLEADO_ACTUAL = "empleado/actualidad";

/**** Redirects ****/
public final static String HOME_ROOT = "/";
public final static String RESPONSABLE = "redirect:/responsable";
public final static String RESPONSABLE_SUELDO_RD = "redirect:/responsable/sueldos?idUnidadDeVenta=";
}
