package com.oo2.tpgrupalhito2grupo08.entities;

import java.time.LocalDateTime;

import com.oo2.tpgrupalhito2grupo08.entities.enums.UsuarioRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name="username", unique=true, nullable=false, length=45)
    private String username;

    @Column(name="password", nullable=false, length=60)
    private String password;

    private boolean habilitado;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UsuarioRol userRol;


}