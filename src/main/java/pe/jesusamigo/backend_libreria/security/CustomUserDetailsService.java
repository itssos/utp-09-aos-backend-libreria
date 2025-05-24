package pe.jesusamigo.backend_libreria.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.jesusamigo.backend_libreria.role.entity.Role;
import pe.jesusamigo.backend_libreria.user.entity.User;
import pe.jesusamigo.backend_libreria.user.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 1) Role-based authorities
        Role role = user.getRole();

        Set<GrantedAuthority> authorities = Stream.concat(
                Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName())),
                role.getPermissions().stream()
                        .map(perm -> new SimpleGrantedAuthority(perm.getName()))
        ).collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }
}