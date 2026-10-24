package com.oo2.tpgrupalhito2grupo08.entities;

import com.oo2.tpgrupalhito2grupo08.entities.enums.TurnoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="cajero")
@Getter @Setter @ToString
@NoArgsConstructor
public class Cajero extends Personal {

    @Enumerated(EnumType.STRING)
    @Column(name="turno", nullable = false)
    private TurnoEnum turno;

    public Cajero(
            String nombre,
            String apellido,
            String email,
            Long dni,
            LocalDate fechaIngreso,
            LocalDate fechaNacimiento,
            Usuario usuario,
            UnidadDeVenta unidadDeVenta,
            TurnoEnum turno) {

        super(
                nombre,
                apellido,
                email,
                dni,
                fechaIngreso,
                fechaNacimiento,
                usuario,
                unidadDeVenta
        );
        this.turno=turno;
    }

    @Override
    public String getTipoPersonal() {
        return "CAJERO";
    }



    @Override
    public double calcularSueldoMensual(Costo costo, LocalDate fechaReferencia) {
        return costo.getSueldoBase() + getAntiguedadEnAnios(fechaReferencia) * costo.getPlusAntiguedad();
    }

/// 100.000+3*5000
/// 100.000+ 15000
/// 115.000 ->SUELDO MENSUAL -->100%(?)




}
