package com.oo2.tpgrupalhito2grupo08.services;

import com.oo2.tpgrupalhito2grupo08.dtos.UsuarioDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Usuario;
import com.oo2.tpgrupalhito2grupo08.entities.enums.RolEnum;

public interface IUsuarioService {

    Usuario crearUsuario(UsuarioDTO dto, RolEnum rol);


}
