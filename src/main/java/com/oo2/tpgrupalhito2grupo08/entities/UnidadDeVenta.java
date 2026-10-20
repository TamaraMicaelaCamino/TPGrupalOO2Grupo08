package com.oo2.tpgrupalhito2grupo08.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UnidadDeVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idUnidadDeVenta;

    @Column(nullable = false, length = 45)
    protected String nombre;

    @Positive
    @Column(nullable = false)
    protected double superficieOcupada;

    @Column(nullable = false, unique = true, length = 10)
    protected String codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name= "festival_id")
    protected Festival festival;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "responsable_id", nullable = false)
    protected Usuario responsable;

    @OneToMany(mappedBy = "unidadDeVenta", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<Plato> platos=new HashSet<>();

    @OneToMany(mappedBy = "unidadDeVenta")
    protected Set<Personal> personal=new HashSet<>();


    protected UnidadDeVenta(String nombre, double superficieOcupada, String codigo, Festival festival, Usuario responsable) {
        this.nombre = nombre;
        this.superficieOcupada = superficieOcupada;
        this.codigo = codigo;
        this.festival = festival;
        this.responsable = responsable;
    }


    public abstract double calcularCanon(Costo costo);

}
