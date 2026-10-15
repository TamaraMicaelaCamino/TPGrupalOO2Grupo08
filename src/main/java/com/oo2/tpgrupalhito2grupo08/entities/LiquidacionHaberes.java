package com.oo2.tpgrupalhito2grupo08.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"personal_id", "unidad_de_venta_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionHaberes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLiquidacion;

    @PositiveOrZero
    @Column(nullable = false)
    private double sueldoBase;

    @PositiveOrZero
    @Column(nullable = false)
    private double adicional;

    @PositiveOrZero
    @Column(nullable = false)
    private double total;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personal_id", nullable = false)
    private Personal personal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidad_de_venta_id", nullable = false)
    private UnidadDeVenta unidadDeVenta;


}
