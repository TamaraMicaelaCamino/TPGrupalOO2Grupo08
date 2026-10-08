package com.oo2.tpgrupalhito2grupo08.services.implementation;

import java.util.List;

import com.oo2.tpgrupalhito2grupo08.entities.Usuario;
import com.oo2.tpgrupalhito2grupo08.entities.enums.UsuarioRol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oo2.tpgrupalhito2grupo08.repositories.IUserRepository;

@Service("userService")
public class UserService implements UserDetailsService {

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return buildUser(user, buildGrantedAuthorities(user.getUserRol()));
    }

    private User buildUser(Usuario user, List<GrantedAuthority> grantedAuthorities) {
        return new User(user.getUsername(), user.getPassword(), user.isHabilitado(),
                true, true, true, //accountNonExpired, credentialsNonExpired, accountNonLocked,
                grantedAuthorities);
    }


    private List<GrantedAuthority> buildGrantedAuthorities(UsuarioRol rol) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }



}
