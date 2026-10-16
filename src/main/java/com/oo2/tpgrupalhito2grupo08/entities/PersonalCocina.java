package com.oo2.tpgrupalhito2grupo08.entities;

import com.oo2.tpgrupalhito2grupo08.entities.enums.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="personalCocina")
@Setter @Getter @ToString(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
public class PersonalCocina extends Personal{

    @Setter(AccessLevel.NONE)
    @Column(name="vencimientoCarnetDeManipulacion")
    private LocalDate vencimientoCarnetDeManipulacion;

    @Enumerated(EnumType.STRING)
    @Column(name="categoria")
    private Categoria categoria;

    public PersonalCocina(
            String nombre,
            String apellido,
            String email,
            Long dni,
            LocalDate fechaIngreso,
            LocalDate fechaNacimiento,
            Usuario usuario,
            UnidadDeVenta unidadDeVenta,
            LocalDate vencimientoCarnetDeManipulacion,
            Categoria categoria) {

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

        this.vencimientoCarnetDeManipulacion =
                verificarCarnet(vencimientoCarnetDeManipulacion, unidadDeVenta.getFestival().getFechaFin());
        this.categoria = categoria;
    }

    private  LocalDate verificarCarnet(LocalDate vencimientoCarnetDeManipulacion, LocalDate fechaFinFestival){
        if(vencimientoCarnetDeManipulacion == null || fechaIngreso == null){
            throw new IllegalArgumentException("ERROR, LAS FECHAS DEBEN SER VALIDAS");
        }
        if(vencimientoCarnetDeManipulacion.isBefore(fechaFinFestival)){
            throw new IllegalArgumentException("ERROR, LA FECHA DE VENCIMIENTO DEL CARNET NO DEBE SER DURANTE EL FESTIVAL");
        }
        return vencimientoCarnetDeManipulacion;
    }

    @Override
    public String getTipoPersonal() {
        return this.categoria.toString();
    }
}
