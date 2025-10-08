package com.persa.PERSA.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.persa.PERSA.models.User;
import com.persa.PERSA.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));

        String roleName = user.getRole() != null ? user.getRole().getName() : "APRENDIZ";

        String granted = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        boolean enabled = "ACTIVO".equalsIgnoreCase(user.getStatus());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                enabled, true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(granted))
        );
    }
}
