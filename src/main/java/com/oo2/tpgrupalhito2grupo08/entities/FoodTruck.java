package com.oo2.tpgrupalhito2grupo08.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FoodTruck extends UnidadDeVenta{

    @Column(nullable = false, length = 7, unique = true)
    private String patente;

    @Column(nullable = false)
    private boolean requiereElectricidad;

}
