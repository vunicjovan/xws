package com.uns.ftn.accountservice.controller;

import com.uns.ftn.accountservice.auth.AuthenticationRequest;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.*;
import com.uns.ftn.accountservice.service.JWTUtil;
import com.uns.ftn.accountservice.service.SimpleUserService;
import com.uns.ftn.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {

    private UserService userService;
    private SimpleUserService simpleService;
    private JWTUtil jwtUtil;

    @Autowired
    public AccountController(UserService userService, SimpleUserService simpleService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.simpleService = simpleService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(simpleService.getSimpleUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<?> getAdvertisementOwner(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getAdvertisementOwner(id), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<?> getLogged(@RequestHeader("username") String username) {
        User user = userService.getByMail(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new UserResponseDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/verify", produces = "application/json")
    public ResponseEntity<?> verify(@RequestParam(value = "token") String token) {
        System.out.println("Verification invoked!");
        return new ResponseEntity<>(jwtUtil.validateToken(token), HttpStatus.OK);
    }

    @GetMapping(value = "/unregistered", produces = "application/json")
    public ResponseEntity<?> getUnregisteredAgents() {
        return userService.getUnregisteredAgents();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(userService.login(authenticationRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/invokeReset", produces = "application/json")
    public ResponseEntity<?> invokePasswordReset(@RequestBody EmailDTO edto) {
        return userService.createResetToken(edto);
    }

    @PutMapping(value = "/resetPassword", consumes = "application/json")
    public ResponseEntity<?> resetPassword(@RequestBody ResetDTO rdto) {
        return userService.resetPassword(rdto);
    }

//    @PreAuthorize("hasAuthority('create')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(userUpdateDTO);
    }

    @PutMapping("/cancelation/{id}")
    public ResponseEntity<?> incrementCancelation(@PathVariable("id") Long id) {
        return userService.incrementCancelation(id);
    }

    //@PreAuthorize("hasAuthority('add')")
    @PutMapping("/block/{id}")
    public ResponseEntity<?> block(@PathVariable("id") Long id) {
        return simpleService.blockUser(id);
    }

    @PutMapping(value = "/changePassword", consumes = "application/json")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO pcDTO) {
        return userService.changePassword(pcDTO);
    }

    @PutMapping(value = "/registerAgent", consumes = "application/json")
    public ResponseEntity<?> registerAgent(@RequestBody AgentRegisterDTO agnRegDTO) {
        return userService.registerAgent(agnRegDTO);
    }

    @PutMapping(value = "/activate/{token}")
    public ResponseEntity<?> registerUser(@PathVariable("token") String token) {
        return userService.activateAccount(token);
    }

    //@PreAuthorize("hasAuthority('delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}/ad/count")
    public ResponseEntity<?> getAdCount(@PathVariable ("id") Long id) {
        return userService.getUsersAdCount(id);
    }


}
