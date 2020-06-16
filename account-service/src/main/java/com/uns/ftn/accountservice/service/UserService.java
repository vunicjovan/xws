package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.auth.AuthenticationRequest;
import com.uns.ftn.accountservice.auth.AuthenticationResponse;
import com.uns.ftn.accountservice.domain.Agent;
import com.uns.ftn.accountservice.domain.SimpleUser;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.*;
import com.uns.ftn.accountservice.exceptions.BadRequestException;
import com.uns.ftn.accountservice.exceptions.NotFoundException;
import com.uns.ftn.accountservice.repository.AgentRepository;
import com.uns.ftn.accountservice.repository.RoleRepository;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import com.uns.ftn.coreapi.commands.CreateSimpleUserCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Inject
    private transient CommandGateway commandGateway;

    public User save(User user) {
        return userRepository.save(user);
    }
    public void delete(Long id) { userRepository.deleteById(id); }

    public User findOne(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User with provided id does not exist.")); }

    public UserDTO registerUser(UserDTO userDTO) {

        String regexNames = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\\s?)+$";
        String regexPass = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W+)([a-zA-Z0-9!?#\\s?]+)$";
        Pattern patternNames = Pattern.compile(regexNames);
        Pattern patternPass = Pattern.compile(regexPass);

        if (!validateUser(userDTO, patternNames, patternPass)) {
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

            //emit user created event and begin saga

            commandGateway.send(new CreateSimpleUserCommand(user.getId()));

        }

        return userDTO;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W+)([a-zA-Z0-9!?#\\s?]+)$";
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

        // check if simple-user is blocked or if user is deleted
        checkIfBlockedOrDeleted(authenticationRequest.getEmail());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponse(jwt);
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with given id does not exist!"));

        if (user.getDeleted()) {
            throw new BadRequestException("User is already deleted.");
        }

        user.setDeleted(true);
        String stremail = user.getEmail();
        user.setEmail(user.getEmail() + ".deleted");
        userRepository.save(user);

        return new ResponseEntity<>("User with username " + stremail + " deleted.", HttpStatus.OK);
    }

    private void checkIfBlockedOrDeleted(String email) {
        User myUser = getByMail(email);

        if (!myUser.getEnabled()) {
            throw new BadRequestException("You must enable your account before login. Please verify and try again.");
        }

        SimpleUser simpleUser = getByUser(myUser);
        if (simpleUser != null) {
            if (simpleUser.getBlocked()) {
                throw new BadRequestException("Your account has been blocked and system log-in is unavailable while block is active.");
            }
            else if (myUser.getDeleted()) {
                throw new NotFoundException("Your account has been deleted.");
            }
        }
    }
  
    public User getByMail(String mail) {
        User user = userRepository.findByEmail(mail);
        return user;
    }

    private SimpleUser getByUser(User user) {
        SimpleUser simpleUser = simpleUserRepository.findByUser(user);
        return simpleUser;
    }

    public String getAdvertisementOwner(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Owner with given id does not exist!"));

        return user.getFirstName() + " " + user.getLastName();
    }

    public void createSimpleUserRollback(Long userId) {
        System.out.println("TREBA ISPISATI FUNKCIJU ZA ROLLBACK MOMCI");
    }

    public ResponseEntity<?> changePassword(PasswordChangeDTO pcDTO) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        // validate and sanitize password data
        validateAndSanitizePasswordData(pcDTO);

        if (authenticationManager != null) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pcDTO.getOldPassword()));
            } catch (Exception e) {
                throw new BadRequestException("Invalid username or password. User cannot be authenticated.");
            }
        } else {
            throw new BadRequestException("Authentication manager is invalid or not working.");
        }

        User user = findByUsername(username);
        user.setPassword(passwordEncoder.encode(pcDTO.getNewPassword()));
        user = save(user);

        return new ResponseEntity<>("Successfully changed password for user with username " + user.getEmail() + ".", HttpStatus.OK);
    }

    public ResponseEntity<?> getUnregisteredAgents() {
        List<Agent> agents = agentRepository.findAll();
        return new ResponseEntity<>(
                agents.stream()
                        .filter(agent -> agent.getUser().getEnabled().equals(false))
                        .map(agent -> new UnregisteredAgentDTO(agent.getUser().getId(),
                                agent.getUser().getFirstName(),
                                agent.getUser().getLastName()))
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<?> registerAgent(AgentRegisterDTO agnRegDTO) {
        User user = findOne(agnRegDTO.getId());
        if(user.getEnabled()) {
            throw new BadRequestException("User already registered and verified!");
        }
        if(agnRegDTO.getAccepted()) {
            user.setEnabled(true);
            save(user);
            //TODO 1.1 Send confirmation email to the agent.
        }
        else {
            //TODO 1.2 Send notification email about rejecting to the agent.
            delete(agnRegDTO.getId());
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>(agnRegDTO, HttpStatus.OK);
    }

    public ResponseEntity<?> getUser(Long id) {
        User user = findOne(id);
        UserResponseDTO userDTO = new UserResponseDTO();

        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    private void validateAndSanitizePasswordData(PasswordChangeDTO pcDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W+)([a-zA-Z0-9!?#\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (pcDTO.getOldPassword() == null || pcDTO.getOldPassword().trim().equals("") ||
                pcDTO.getNewPassword() == null || pcDTO.getNewPassword().trim().equals("") ||
                pcDTO.getNewPasswordRetype() == null || pcDTO.getNewPasswordRetype().trim().equals("") ||
                !pattern.matcher(pcDTO.getOldPassword().trim()).matches() ||
                !pattern.matcher(pcDTO.getNewPassword().trim()).matches() ||
                !pattern.matcher(pcDTO.getNewPasswordRetype().trim()).matches() ||
                pcDTO.getOldPassword().trim().length() < 10 ||
                pcDTO.getNewPassword().trim().length() < 10 ||
                pcDTO.getNewPasswordRetype().trim().length() < 10) {
            throw new BadRequestException("Entered data is either corrupted or invalid. Please try again with valid data.");
        } else if (!pcDTO.getNewPassword().equals(pcDTO.getNewPasswordRetype())) {
            throw new BadRequestException("New password is not repeated correctly. Please try again.");
        }

        pcDTO.setOldPassword(Encode.forHtml(pcDTO.getOldPassword().trim()));
        pcDTO.setNewPassword(Encode.forHtml(pcDTO.getNewPassword().trim()));
        pcDTO.setNewPasswordRetype(Encode.forHtml(pcDTO.getNewPasswordRetype().trim()));
    }

    private User findByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new NotFoundException("User does not exist.");
        }

        return user;
    }

    /*
     * Checks if there is mismatch against given regex in any of String attributes of User, which are received from
     * client through DTO. It also checks if attributes are existing.
     * Returns TRUE if given DTO is valid, else returns FALSE.
     */
    private Boolean validateUser(UserDTO userDTO, Pattern patternNames, Pattern patternPass) {
        if (userDTO.getFirstName() == null || userDTO.getLastName() == null || userDTO.getEmail() == null ||
                userDTO.getPassword() == null || userDTO.getRepeatPassword() == null ||
                userDTO.getFirstName().trim().equals("") || userDTO.getLastName().trim().equals("") ||
                userDTO.getEmail().trim().equals("") || userDTO.getPassword().trim().equals("") ||
                userDTO.getRepeatPassword().trim().equals("") || userDTO.getFirstName().length() < 3 ||
                userDTO.getLastName().length() < 3 || userDTO.getPassword().length() < 10 ||
                userDTO.getRepeatPassword().length() < 10 || !userDTO.getRepeatPassword().equals(userDTO.getPassword()) ||
                (userDTO.getEmail().trim().split("@").length <= 1) ||
                !patternNames.matcher(userDTO.getFirstName().trim()).matches() ||
                !patternNames.matcher(userDTO.getLastName().trim()).matches() ||
                !patternPass.matcher(userDTO.getPassword().trim()).matches() ||
                !patternPass.matcher(userDTO.getRepeatPassword().trim()).matches()) {
            return false;
        }

        return true;
    }

    /*
    * Returns TRUE if email and password are valid String values, else returns FALSE.
    */
    private Boolean validateLoginData(String email, String password, Pattern pattern) {
        if (email == null || password == null || email.trim().equals("") || password.trim().equals("") ||
                (email.trim().split("@").length <= 1) || password.length() < 10 ||
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
