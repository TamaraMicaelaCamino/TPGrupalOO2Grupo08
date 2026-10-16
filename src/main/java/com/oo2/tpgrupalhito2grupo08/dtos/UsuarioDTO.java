package com.oo2.tpgrupalhito2grupo08.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 100, message = "El usuario debe tener entre 4 y 100 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;


}
