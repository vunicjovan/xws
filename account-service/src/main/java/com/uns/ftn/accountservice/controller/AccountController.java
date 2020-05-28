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

    private UserService userService;

    private CustomUserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    @Autowired
    public AccountController(UserService userService, CustomUserDetailsService customUserDetailsService,
                             AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userService = userService;
        this.userDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

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
            return new ResponseEntity<>("Wrong email or password",HttpStatus.BAD_REQUEST);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        UserDTO response = userService.registerUser(userDTO);

        if (response != null) {
            return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
