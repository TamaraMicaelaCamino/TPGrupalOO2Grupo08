package com.oo2.tpgrupalhito2grupo08.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oo2.tpgrupalhito2grupo08.entities.Usuario;
import java.util.Optional;


public interface IUsuarioRepository extends JpaRepository<Usuario,Long > {

    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);

}