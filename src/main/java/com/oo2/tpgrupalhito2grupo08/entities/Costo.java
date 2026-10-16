package com.oo2.tpgrupalhito2grupo08.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="costo")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Costo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idCosto;

    @Column(name = "nombreCosto", nullable = false, length = 45)
    private String nombreCosto;

    @Column(name="montoCosto", nullable = false)
    private double montoCosto;
  /*
    @Column(name="costoSuperficie")
    private double costoSuperficie;

    @Column(name="costoMontaje")
    private double costoMontaje;

    @Column(name="plusElectricidad")
    private double plusElectricidad;

    @Column(name="sueldoBase")
    private double sueldoBase;

    @Column(name="plusCocinero")
    private double plusCocinero;

    @Column(name="plusAyudante")
    private double plusAyudante;

    @Column(name="plusLavaplatos")
    private double plusLavaplatos;

    @Column(name="plusAntiguedad")
    private double plusAntiguedad;
*/



}
