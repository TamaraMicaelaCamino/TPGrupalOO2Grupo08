package com.oo2.tpgrupalhito2grupo08.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Column(nullable = false)
    private boolean activo;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name="costo_id",
            nullable = false
    )
    @ToString.Exclude
    private Costo costo;

    @OneToMany(
            mappedBy = "festival",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @ToString.Exclude
    private Set<UnidadDeVenta> unidades = new HashSet<>();

    public Festival(String nombre, String temporada, LocalDate fechaInicio, LocalDate fechaFin, Costo costo){
        this.nombre=nombre;
        this.temporada=temporada;
        this.costo=costo;
        esFechaValida(fechaInicio,fechaFin);
    }

    public void esFechaValida(LocalDate fechaInicio, LocalDate fechaFin){
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("error, las fechas son obligatorias");
        }
        if(!fechaInicio.isBefore(fechaFin)){
             throw new IllegalArgumentException("error, la fecha de fin no debe ser antes que la fecha de inicio");
         }
         this.fechaInicio=fechaInicio;
         this.fechaFin=fechaFin;
    }


     /// calcula cuantos dias dura el festival
    public long getDuracionEnDias() {
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
    } //cuenta los días entre las dos fechas, sin incluir el ultimo.
    // El + 1 hace que el rango sea inclusivo (cuenta tanto el dia de inicio como el de fin).

    public double getPorcentajePorDias() {
        return Math.ceil(getDuracionEnDias() / 15.0) * 0.5; //0.5 -->es fijo para cada tramo(se lo multiplica despues del ceil)
    }//devuelve el porcentaje en forma DECIMAL con respecto a la cant de dias del festival
    //15.0 es la cantidad de días que tiene cada tramo

}
