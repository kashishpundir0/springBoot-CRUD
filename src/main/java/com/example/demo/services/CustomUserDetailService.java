package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.repositorise.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("Pearl".equals(username)) {
            // In-memory admin user
            return User.withUsername("Pearl")
                    .password(passwordEncoder.encode("PearlProdChecker@12390"))
                    .authorities("SUPERADMIN")
                    .build();
        }

        Employee employee = employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email"));

        String password = employee.getPassword();

        if (password == null || password.isEmpty()) {
            password = "";
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (employee.getLevel() != null)
            authorities.add(new SimpleGrantedAuthority(employee.getLevel().toString()));

        return new User(
                employee.getEmail(),
                password,
                authorities);
    }


}