package com.oo2.tpgrupalhito2grupo08.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodTruckDTO extends UnidadDeVentaDTO{

    @NotBlank(message = "La patente es obligatoria")
    @Pattern(regexp = "^[A-Z]{2}\\d{3}[A-Z]{2}$",
            message = "Patente inválida. Formato: AB123CD")
    private String patente;

    private boolean requiereElectricidad;

    public void setPatente(String patente) {
        this.patente = patente == null ? null : patente.trim().toUpperCase();
    }


}
