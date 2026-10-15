package com.oo2.tpgrupalhito2grupo08.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PuestoDesarmable extends UnidadDeVenta{

    @Positive
    @Column(nullable = false)
    private int cantCarpas;

    @Positive
    @Column(nullable = false)
    private int tiempoDeMontaje;


    public PuestoDesarmable(String nombre, double superficieOcupada, String codigo, Festival festival, Usuario responsable, int cantCarpas, int tiempoDeMontaje) {
        super(nombre, superficieOcupada, codigo, festival, responsable);
        this.cantCarpas = cantCarpas;
        this.tiempoDeMontaje = tiempoDeMontaje;
    }





}
