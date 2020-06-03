package com.uns.ftn.accountservice.controller;

import com.uns.ftn.accountservice.auth.AuthenticationRequest;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.UserDTO;
import com.uns.ftn.accountservice.dto.UserResponseDTO;
import com.uns.ftn.accountservice.service.JWTUtil;
import com.uns.ftn.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {

    private UserService userService;

    private JWTUtil jwtUtil;

    @Autowired
    public AccountController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
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

    @GetMapping("/owner/{id}")
    public ResponseEntity<?> getAdvertisementOwner(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getAdvertisementOwner(id), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<?> getLogged(@RequestHeader("username") String username) {
        User user = userService.getByMail(username);

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new UserResponseDTO(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(userService.login(authenticationRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register", produces = "application/json")
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
