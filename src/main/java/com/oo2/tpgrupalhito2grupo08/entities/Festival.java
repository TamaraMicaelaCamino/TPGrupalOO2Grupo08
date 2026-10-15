package com.oo2.tpgrupalhito2grupo08.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="festival")
@Getter @Setter @ToString
@NoArgsConstructor
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFestival;

    @Column(name="nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name="temporada", nullable = false, length = 45)
    private String temporada;

    @Setter(AccessLevel.NONE)
    @Column(name="fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Setter(AccessLevel.NONE)
    @Column(name="fechaFin", nullable = false)
    private LocalDate fechaFin;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name="festival_id",
            nullable = false
    )
    @ToString.Exclude
    private Set<Costo> costos = new HashSet<>();

    @OneToMany(
            mappedBy = "festival",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @ToString.Exclude
    private Set<UnidadDeVenta> unidades = new HashSet<>();

    public Festival(String nombre, String temporada, LocalDate fechaInicio, LocalDate fechaFin){
        this.nombre=nombre;
        this.temporada=temporada;
        esFechaValida(fechaInicio,fechaFin);
    }

    public void esFechaValida(LocalDate fechaInicio, LocalDate fechaFin){
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("error, las fechas son obligatorias");
        }
        if(fechaInicio.isBefore(fechaFin)){
             throw new IllegalArgumentException("error, la fecha de fin no debe ser antes que la fecha de inicio");
         }
         this.fechaInicio=fechaInicio;
         this.fechaFin=fechaFin;
    }
}
