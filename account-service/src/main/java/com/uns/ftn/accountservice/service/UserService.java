package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.auth.AuthenticationRequest;
import com.uns.ftn.accountservice.auth.AuthenticationResponse;
import com.uns.ftn.accountservice.domain.*;
import com.uns.ftn.accountservice.dto.UserDTO;
import com.uns.ftn.accountservice.exceptions.BadRequestException;
import com.uns.ftn.accountservice.repository.AgentRepository;
import com.uns.ftn.accountservice.repository.RoleRepository;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    public UserDTO registerUser(UserDTO userDTO) {

        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9\\\\!\\\\?\\\\#\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (!validateUser(userDTO, pattern)) {
            throw new BadRequestException("Given data is not well formed!");
        }

        sanitizeUserData(userDTO);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("User with given email already exists!");
        }

        User user = new User(userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmail(),
                                                                        passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getIsAgent()) {
            user.getRoles().add(roleRepository.findByName("AGENT"));
            userRepository.save(user);
            Agent agent = new Agent(user);
            agentRepository.save(agent);
        } else {
            user.getRoles().add(roleRepository.findByName("SIMPLE_USER"));
            userRepository.save(user);
            SimpleUser simpleUser = new SimpleUser(user);
            simpleUserRepository.save(simpleUser);
        }

        return userDTO;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9\\\\!\\\\?\\\\#\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (!validateLoginData(authenticationRequest.getEmail(), authenticationRequest.getPassword(), pattern)) {
            throw new BadRequestException("Email or password are not in correct format!");
        }

        sanitizeAuthData(authenticationRequest);

        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }
        catch (Exception e)
        {
            throw new BadRequestException("Wrong email or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponse(jwt);
    }
  
    public User getByMail(String mail) {
        User user = userRepository.findByEmail(mail);
        return user;
    }
  
    /*
    * Checks if there is mismatch against given regex in any of String attributes of User, which are received from
    * client through DTO. It also checks if attributes are existing.
    * Returns TRUE if given DTO is valid, else returns FALSE.
    */
    private Boolean validateUser(UserDTO userDTO, Pattern pattern) {
        if (userDTO.getFirstName() == null || userDTO.getLastName() == null || userDTO.getEmail() == null ||
            userDTO.getPassword() == null || userDTO.getRepeatPassword() == null ||
            userDTO.getFirstName().trim().equals("") || userDTO.getLastName().trim().equals("") ||
            userDTO.getEmail().trim().equals("") || userDTO.getPassword().trim().equals("") ||
            userDTO.getRepeatPassword().trim().equals("") || userDTO.getFirstName().length() < 3 ||
            userDTO.getLastName().length() < 3 || userDTO.getPassword().length() < 8 ||
            userDTO.getRepeatPassword().length() < 8 || !userDTO.getRepeatPassword().equals(userDTO.getPassword()) ||
            (userDTO.getEmail().trim().split("@").length <= 1) ||
            !pattern.matcher(userDTO.getFirstName().trim()).matches() ||
            !pattern.matcher(userDTO.getLastName().trim()).matches() ||
            !pattern.matcher(userDTO.getPassword().trim()).matches() ||
            !pattern.matcher(userDTO.getRepeatPassword().trim()).matches()) {
            return false;
        }

        return true;
    }

    /*
    * Returns TRUE if email and password are valid String values, else returns FALSE.
    */
    private Boolean validateLoginData(String email, String password, Pattern pattern) {
        if (email == null || password == null || email.trim().equals("") || password.trim().equals("") ||
            (email.trim().split("@").length <= 1) || password.length() < 8 ||
            !pattern.matcher(password.trim()).matches()) {
            return false;
        }

        return true;
    }

    /*
    * Helper method for forbidden character sanitization in terms of user data.
    */
    private void sanitizeUserData(UserDTO userDTO) {
        userDTO.setFirstName(Encode.forHtml(userDTO.getFirstName()));
        userDTO.setLastName(Encode.forHtml(userDTO.getLastName()));
        userDTO.setEmail(Encode.forHtml(userDTO.getEmail()));
        userDTO.setPassword(Encode.forHtml(userDTO.getPassword()));
        userDTO.setRepeatPassword(Encode.forHtml(userDTO.getRepeatPassword()));
    }

    /*
     * Helper method for forbidden character sanitization in terms of authentication data.
     */
    private void sanitizeAuthData(AuthenticationRequest arq) {
        arq.setEmail(Encode.forHtml(arq.getEmail()));
        arq.setPassword(Encode.forHtml(arq.getPassword()));
    }
}
