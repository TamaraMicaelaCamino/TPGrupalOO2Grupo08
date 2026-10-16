package com.oo2.tpgrupalhito2grupo08.services.implementation;
import java.util.List;
import com.oo2.tpgrupalhito2grupo08.dtos.UsuarioDTO;
import com.oo2.tpgrupalhito2grupo08.entities.Usuario;
import com.oo2.tpgrupalhito2grupo08.entities.enums.RolEnum;
import com.oo2.tpgrupalhito2grupo08.repositories.IRolRepository;
import com.oo2.tpgrupalhito2grupo08.services.IUsuarioService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.oo2.tpgrupalhito2grupo08.repositories.IUsuarioRepository;

@Service("usuarioService")
public class UsuarioService implements UserDetailsService, IUsuarioService {

    private final IUsuarioRepository userRepository;
    private final IRolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(IUsuarioRepository userRepository,
                          IRolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario crearUsuario(UsuarioDTO dto, RolEnum rol) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Ese usuario ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setHabilitado(true);
        usuario.setRol(rolRepository.findByTipo(rol)
                .orElseThrow(() -> new IllegalStateException("Rol no cargado: " + rol)));
        return userRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return buildUser(user, buildGrantedAuthorities(user.getRol().getTipo()));
    }

    private User buildUser(Usuario user, List<GrantedAuthority> grantedAuthorities) {
        return new User(user.getUsername(), user.getPassword(), user.isHabilitado(),
                true, true, true, //accountNonExpired, credentialsNonExpired, accountNonLocked,
                grantedAuthorities);
    }


    private List<GrantedAuthority> buildGrantedAuthorities(RolEnum rol) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }



}

