package com.oo2.tpgrupalhito2grupo08.repositories;
import com.oo2.tpgrupalhito2grupo08.entities.Rol;
import com.oo2.tpgrupalhito2grupo08.entities.enums.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepository extends JpaRepository <Rol, Long> {

    Optional<Rol> findByTipo(RolEnum tipo);

}
