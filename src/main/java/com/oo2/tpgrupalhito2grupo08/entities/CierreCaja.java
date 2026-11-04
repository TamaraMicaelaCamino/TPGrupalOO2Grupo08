package com.oo2.tpgrupalhito2grupo08.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cierreCaja",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_cierre_unidad_fecha",
                columnNames = {"unidadDeVenta_id", "fechaOperativa"}
        )
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CierreCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCierreCaja;

    @Column(name="fechaOperativa", nullable = false)
    private LocalDate fechaOperativa;

    @Column(name="fechaHoraCierre", nullable = false)
    private LocalDateTime fechaHoraCierre;

    @Column(name="cantPedidos", nullable = false)
    private int cantPedidos;

    @Column(name="totalRecaudado", nullable = false)
    private Double totalRecaudado;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "unidadDeVenta_id",
            nullable = false
    )
    @ToString.Exclude
    private UnidadDeVenta unidad;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "cajero_id",
            nullable = false
    )
    @ToString.Exclude
    private Cajero cajero;

}
