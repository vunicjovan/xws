package com.uns.ftn.accountservice.controller;

import com.uns.ftn.accountservice.auth.AuthenticationRequest;
import com.uns.ftn.accountservice.auth.AuthenticationResponse;
import com.uns.ftn.accountservice.dto.UserDTO;
import com.uns.ftn.accountservice.service.CustomUserDetailsService;
import com.uns.ftn.accountservice.service.JWTUtil;
import com.uns.ftn.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;



    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                                authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException | UsernameNotFoundException e)
        {
            throw new Exception("Wrong email address or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('create')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return null;
    }

    @PreAuthorize("hasAuthority('add')")
    @PutMapping("/block/{id}")
    public ResponseEntity<?> block(@PathVariable("id") Long id) {
        return null;
    }

    @PreAuthorize("hasAuthority('delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return null;
    }


    @GetMapping(value = "/verify", produces = "application/json")
    public ResponseEntity<?> verify(@RequestParam(value = "token") String token) {
        System.out.println("Verification invoked!");
        return new ResponseEntity<>(jwtUtil.validateToken(token), HttpStatus.OK);
    }

}
