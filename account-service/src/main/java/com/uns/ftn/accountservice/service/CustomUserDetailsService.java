package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.domain.Role;
import com.uns.ftn.accountservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userService.getByMail(mail);

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        Set<Role> roleList = user.getRoles();

        if (roleList != null) {
            for (Role role: user.getRoles()) {
                role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
            }
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
