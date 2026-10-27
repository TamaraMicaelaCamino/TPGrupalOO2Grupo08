package com.oo2.tpgrupalhito2grupo08.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="personal")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @ToString
@NoArgsConstructor
public abstract class Personal {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long idPersonal;

    @Column(name="nombre", nullable = false, length = 45)
    protected String nombre;

    @Column(name="apellido", nullable = false, length = 45)
    protected String apellido;

    @NotBlank
    @Email
    @Column(name="email", nullable = false)
    protected String email;

    @Column(name="dni", nullable = false, length = 8)
    protected Long dni;

    @Setter(AccessLevel.NONE)
    @Column(name = "fechaIngreso", nullable = false)
    protected LocalDate fechaIngreso;

    @Setter(AccessLevel.NONE)
    @Column(name="fechaNacimiento", nullable = false)
    protected LocalDate fechaNacimiento;

    @Column(name="activo")
    protected boolean activo;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            unique = true
    )
    @ToString.Exclude
    protected Usuario usuario;

    @ManyToOne(
          fetch = FetchType.LAZY,
          optional = false
       )
    @JoinColumn(
           name = "unidadDeVenta_id",
           nullable = false
       )
    @ToString.Exclude
    protected UnidadDeVenta unidadDeVenta;

    protected Personal(String nombre, String apellido, String email, Long dni, LocalDate fechaIngreso, LocalDate fechaNacimiento, Usuario usuario, UnidadDeVenta unidadDeVenta){
        this.nombre=nombre;
        this.apellido=apellido;
        this.email=email;
        this.dni=dni;
        this.fechaIngreso=fechaIngreso;
        this.fechaNacimiento=verificarNacimiento(fechaNacimiento, fechaIngreso);
        this.unidadDeVenta=unidadDeVenta;
        this.usuario=usuario;
        this.activo=true;
    }

    public abstract String getTipoPersonal();

    private LocalDate verificarNacimiento(LocalDate fechaNacimiento, LocalDate fechaIngreso) {
        if (fechaNacimiento == null || fechaIngreso == null) {
            throw new IllegalArgumentException(
                    "Las fechas de nacimiento e ingreso son obligatorias"
            );
        }
        LocalDate mayor= fechaIngreso.minusYears(18);
        if(fechaNacimiento.isAfter(mayor)){
            throw new IllegalArgumentException("DEBE SER MAYOR DE EDAD");
        }
        return fechaNacimiento;
    }


    public abstract double calcularSueldoMensual(Costo costo, LocalDate fechaReferencia);

    public int getAntiguedadEnAnios(LocalDate fechaReferencia) {
       if (fechaReferencia.isBefore(fechaIngreso)) {
           return 0;
       }
       return Period.between(fechaIngreso, fechaReferencia).getYears();
   }



}
