package com.oo2.tpgrupalhito2grupo08.entities;
import com.oo2.tpgrupalhito2grupo08.entities.enums.RolEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolEnum tipo;


}