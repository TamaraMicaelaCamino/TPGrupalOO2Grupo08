package com.oo2.tpgrupalhito2grupo08.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.oo2.tpgrupalhito2grupo08.entities.Usuario;
import java.util.Optional;

@Repository("userRepository")
public interface IUserRepository extends JpaRepository<Usuario,Long > {

    Optional<Usuario> findByUsername(String username);


}