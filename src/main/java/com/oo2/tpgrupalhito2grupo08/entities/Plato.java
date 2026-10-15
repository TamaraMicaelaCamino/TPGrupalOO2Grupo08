package com.oo2.tpgrupalhito2grupo08.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Positive
    @Column(nullable = false)
    private double costoProduccion;

    @Positive
    @Column(nullable = false)
    private double precioVenta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidad_de_venta_id")
    private UnidadDeVenta unidadDeVenta;


}
