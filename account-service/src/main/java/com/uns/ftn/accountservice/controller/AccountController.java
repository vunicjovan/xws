package com.uns.ftn.accountservice.controller;

import com.uns.ftn.accountservice.auth.AuthenticationRequest;
import com.uns.ftn.accountservice.auth.AuthenticationResponse;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.*;
import com.uns.ftn.accountservice.service.JWTUtil;
import com.uns.ftn.accountservice.service.SimpleUserService;
import com.uns.ftn.accountservice.service.UserService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class AccountController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public ResponseEntity<?> getAll()
    {
        logger.debug("Get simple users");
        return new ResponseEntity<>(simpleService.getSimpleUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id)
    {
        logger.debug("Get user with id {}", id);
        return userService.getUser(id);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<?> getAdvertisementOwner(@PathVariable("id") Long id) {
        logger.debug("Get advertisement owner with id {}", id);
        return new ResponseEntity<>(userService.getAdvertisementOwner(id), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<?> getLogged(@RequestHeader("username") String username) {
        logger.debug("Get logged user with username {}", username);
        User user = userService.getByMail(username);

        if (user == null) {
            logger.error("User with username {} does not exist", username);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new UserResponseDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/verify", produces = "application/json")
    public ResponseEntity<?> verify(@RequestParam(value = "token") String token) {
        logger.debug("Token verification invoked");
        return new ResponseEntity<>(jwtUtil.validateToken(token), HttpStatus.OK);
    }

    @GetMapping(value = "/unregistered", produces = "application/json")
    public ResponseEntity<?> getUnregisteredAgents()
    {
        logger.debug("Get unregistered agents");
        return userService.getUnregisteredAgents();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        logger.debug("Login user with username {}", authenticationRequest.getEmail());
        AuthenticationResponse response = userService.login(authenticationRequest);
        ResponseCookie responseCookie = userService.createCookie(authenticationRequest.getEmail());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(response);
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        logger.debug("Register user with username {}", userDTO.getEmail());
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/invokeReset", produces = "application/json")
    public ResponseEntity<?> invokePasswordReset(@RequestBody EmailDTO edto) {
        logger.debug("Password reset for user with username {}", edto.getEmail());
        return userService.createResetToken(edto);
    }

    @PutMapping(value = "/resetPassword", consumes = "application/json")
    public ResponseEntity<?> resetPassword(@RequestBody ResetDTO rdto) {
        logger.debug("Reset password");
        return userService.resetPassword(rdto);
    }

    @PreAuthorize("hasAuthority('create')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return null;
    }

    //@PreAuthorize("hasAuthority('add')")
    @PutMapping("/block/{id}")
    public ResponseEntity<?> block(@PathVariable("id") Long id) {
        logger.debug("Block user with id {}", id);
        return simpleService.blockUser(id);
    }

    @PutMapping(value = "/changePassword", consumes = "application/json")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO pcDTO) {
        logger.debug("Change password");
        return userService.changePassword(pcDTO);
    }

    @PutMapping(value = "/registerAgent", consumes = "application/json")
    public ResponseEntity<?> registerAgent(@RequestBody AgentRegisterDTO agnRegDTO) {
        logger.debug("Register agent with id {}", agnRegDTO.getId());
        return userService.registerAgent(agnRegDTO);
    }

    @PutMapping(value = "/activate/{token}")
    public ResponseEntity<?> registerUser(@PathVariable("token") String token) {
        logger.debug("Activate account");
        return userService.activateAccount(token);
    }

    //@PreAuthorize("hasAuthority('delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        logger.debug("Delete user with id {}", id);
        return userService.deleteUser(id);
    }

    @PutMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = true) String cookie) {
        logger.debug("Refresh token");
        AuthenticationResponse response = userService.refreshToken(cookie);
        ResponseCookie responseCookie = userService.refreshCookie(cookie);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refreshToken", required = true) String cookie) {
        logger.debug("Logout");
        ResponseCookie responseCookie = ResponseCookie.
                from("refreshToken", cookie)
                .path("/account")
                .maxAge(0)
                .sameSite("Strict")
                .httpOnly(true).build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();
    }

}
