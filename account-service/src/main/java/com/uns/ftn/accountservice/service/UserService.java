package com.uns.ftn.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.accountservice.auth.AuthenticationRequest;
import com.uns.ftn.accountservice.auth.AuthenticationResponse;
import com.uns.ftn.accountservice.components.QueueProducer;
import com.uns.ftn.accountservice.domain.*;
import com.uns.ftn.accountservice.dto.*;
import com.uns.ftn.accountservice.exceptions.BadRequestException;
import com.uns.ftn.accountservice.exceptions.NotFoundException;
import com.uns.ftn.accountservice.exceptions.UnauthorizedException;
import com.uns.ftn.accountservice.repository.*;
import com.uns.ftn.coreapi.commands.CreateSimpleUserCommand;
import io.jsonwebtoken.ExpiredJwtException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private AgentRepository agentRepository;
    private SimpleUserRepository simpleUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;
    private QueueProducer queueProducer;
    private VerificationTokenRepository tokenRepo;
    private ResetTokenRepository resetRepo;

    @Inject
    private transient CommandGateway commandGateway;

    @Autowired
    public UserService(
            UserRepository userRepository,
            AgentRepository agentRepository,
            SimpleUserRepository simpleUserRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            JWTUtil jwtUtil,
            QueueProducer queueProducer,
            VerificationTokenRepository tokenRepo,
            ResetTokenRepository resetRepo
    ) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.simpleUserRepository = simpleUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.queueProducer = queueProducer;
        this.tokenRepo = tokenRepo;
        this.resetRepo = resetRepo;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with provided id does not exist."));
    }

    public ResetToken findResetTokenByUser(User user) {
        ResetToken rt = resetRepo.findByUser(user);
        if (rt == null) {
            throw new NotFoundException("Reset token for given user does not exist.");
        }

        return rt;
    }

    public UserDTO registerUser(UserDTO userDTO) {

        logger.info("New user has submitted registration request");

        String regexNames = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\\s?)+$";
        String regexPass = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W+)([a-zA-Z0-9!?#\\s?]+)$";
        Pattern patternNames = Pattern.compile(regexNames);
        Pattern patternPass = Pattern.compile(regexPass);

        if (!validateUser(userDTO, patternNames, patternPass)) {
            logger.error("User has submitted invalid data");
            throw new BadRequestException("Given data is not well formed!");
        }

        sanitizeUserData(userDTO);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            logger.error("User has tried to register with existing email address {}", userDTO.getEmail());
            throw new BadRequestException("User with given email already exists!");
        }

        User user = new User(userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmail(),
                                                                        passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getIsAgent()) {
            user.getRoles().add(roleRepository.findByName("AGENT"));
            userRepository.save(user);
            logger.info("User with username {} has been saved", userDTO.getEmail());
            Agent agent = new Agent(user);
            agentRepository.save(agent);
            logger.debug("User with role AGENT has been saved");
        } else {
            user.getRoles().add(roleRepository.findByName("SIMPLE_USER"));
            userRepository.save(user);
            logger.info("User with username {} has been saved", user.getEmail());
            SimpleUser simpleUser = new SimpleUser(user);
            simpleUserRepository.save(simpleUser);
            logger.debug("User with role SIMPLE_USER has been saved");

            //emit user created event and begin saga
            commandGateway.send(new CreateSimpleUserCommand(user.getId()));

            //send registration email to user and store his verification token
            VerificationToken token = new VerificationToken();
            token.setToken(UUID.randomUUID().toString());
            token.setUser(user);
            token.setExpiryDate(getTomorrowDate());
            tokenRepo.save(token);
            logger.info("User verification token has been saved");
            MessageDTO mdto = new MessageDTO("Confirm your registration to RentaSoul services", "<p>Please click <a href=\"http://localhost:8090/registerUser/" + token.getToken() + "\">here</a> to verify your registration.</p>" +
                    "<br><br><p>RentaSoul Team</p>", false);


                queueProducer.produce(mdto);
        }

        return userDTO;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        logger.info("User is signing in");
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W+)([a-zA-Z0-9!?#\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (!validateLoginData(authenticationRequest.getEmail(), authenticationRequest.getPassword(), pattern)) {
            logger.error("User has submitted invalid or corrupted data");
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
            logger.error("User submitted invalid email or password");
            throw new BadRequestException("Wrong email or password");
        }

        // check if simple-user is blocked or if user is deleted
        checkIfBlockedOrDeleted(authenticationRequest.getEmail());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        logger.info("Token has been issued to user with username {}", authenticationRequest.getEmail());

        return new AuthenticationResponse(jwt);
    }

    public ResponseCookie createCookie(String mail) {
        logger.info("Refresh token is being issued to user with username {}", mail);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(mail);
        return ResponseCookie.
                from("refreshToken", jwtUtil.generateRefreshToken(userDetails))
                .path("/account")
                .maxAge(60 * 40)
                .sameSite("Strict")
                .httpOnly(true)
                .build();
    }

    public ResponseCookie refreshCookie(String token) {
        logger.info("New refresh token is being issued");
        String username = jwtUtil.extractUsername(token);
        return createCookie(username);
    }

    public AuthenticationResponse refreshToken(String token) {
        logger.info("Refreshing token...");
        try {
            jwtUtil.isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            logger.error("Refresh token has expired, token will not be renewed for user");
            throw new UnauthorizedException("Refresh token has expired!");
        }

        String username = jwtUtil.extractUsername(token);
        checkIfBlockedOrDeleted(username);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);
        logger.info("Token has been refreshed for user with username {}", username);

        return new AuthenticationResponse(jwt);
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        logger.info("Deleting user");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with given id does not exist!"));

        if (user.getDeleted()) {
            logger.error("User with username {} is already deleted", user.getEmail());
            throw new BadRequestException("User is already deleted.");
        }

        user.setDeleted(true);
        String stremail = user.getEmail();
        user.setEmail(user.getEmail() + ".deleted");
        userRepository.save(user);
        logger.info("User with username {} has been deleted", user.getEmail());

        return new ResponseEntity<>("User with username " + stremail + " deleted.", HttpStatus.OK);
    }

    private Date getTomorrowDate() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();

        return dt;
    }

    /*
     * Creates token used for password reset.
     */
    public ResponseEntity<?> createResetToken(EmailDTO edto) {
        //validate and sanitize
        sanitizeEmail(edto.getEmail());
        User user = findByUsername(edto.getEmail());
        ResetToken rt = new ResetToken();

        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryDate(getTenMinuteLimit());
        rt.setUser(user);
        resetRepo.save(rt);

        MessageDTO mdto = new MessageDTO("RentaSoul password reset for user " + user.getFirstName() + " " + user.getLastName(),
                "<p>Please click <a href=\"http://localhost:8090/resetPassword/" + rt.getToken() + "\">here</a> to reset your password.</p><br><br><p>RentaSoul Team</p>", false);
        queueProducer.produce(mdto);

        return new ResponseEntity<>("You will receive email with instructions soon.", HttpStatus.CREATED);
    }

    /*
     * Password reset for given user email and new password value.
     */
    public ResponseEntity<?> resetPassword(ResetDTO rdto) {
        logger.info("Resetting password");
        Date now = new Date();
        //validate and sanitize
        sanitizeEmail(rdto.getEmail());
        sanitizePasswordDTO(rdto);

        ResetToken token = resetRepo.findByToken(rdto.getToken());
        if (token == null) {
            logger.error("Reset password is requested with missing token");
            throw new NotFoundException("Requested token does not exist.");
        } else if (now.after(token.getExpiryDate())) {
            logger.error("Password reset token has expired");
            resetRepo.delete(token);
            throw new NotFoundException("Your token for password reset has expired. Please make new one.");
        }

        User user = token.getUser();
        if (user == null || user.getDeleted()) {
            logger.error("User is deleted or does not exist");
            throw new NotFoundException("Your account data does not exist.");
        } else if (!user.getEmail().equals(rdto.getEmail())) {
            logger.error("Given Email {} does not match users email {}", rdto.getEmail(), user.getEmail());
            throw new NotFoundException("Given email does not match requested user email.");
        }

        user.setPassword(passwordEncoder.encode(rdto.getNewPassword()));
        save(user);
        resetRepo.delete(token);
        logger.info("User with username {} has successfully renewed password", user.getEmail());

        return new ResponseEntity<>("Password successfully renewed.", HttpStatus.OK);
    }

    private void sanitizeEmail(String email) {
        if (email == null || email.trim().equals("") || (email.trim().split("@").length <= 1)) {
            logger.error("Email does not exist or has invalid format (email: {})", email);
            throw new BadRequestException("Invalid email format. Please try again with valid data.");
        }
    }

    private void sanitizePasswordDTO(ResetDTO rdto) {
        String regexPass = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W+)([a-zA-Z0-9!?#\\s?]+)$";
        Pattern patternPass = Pattern.compile(regexPass);

        if (rdto.getNewPassword() == null || rdto.getNewPassword().trim().equals("") ||
                rdto.getNewPasswordRetype() == null || rdto.getNewPasswordRetype().trim().equals("")) {
            logger.error("Password data is missing");
            throw new BadRequestException("Invalid password format.");
        } else if (!rdto.getNewPassword().equals(rdto.getNewPasswordRetype())) {
            logger.error("Password is not correctly repeated");
            throw new BadRequestException("Both passwords must have the same value.");
        }

        rdto.setNewPassword(Encode.forHtml(rdto.getNewPassword()));
        rdto.setNewPasswordRetype(Encode.forHtml(rdto.getNewPasswordRetype()));
    }

    private Date getTenMinuteLimit() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MINUTE, 1);
        dt = c.getTime();

        return dt;
    }

    private void checkIfBlockedOrDeleted(String email) {
        logger.info("Checking status of user with username {}", email);
        User myUser = getByMail(email);

        if (!myUser.getEnabled()) {
            logger.error("User with username {} is not enabled", email);
            throw new BadRequestException("You must enable your account before login. Please verify and try again.");
        }

        SimpleUser simpleUser = getByUser(myUser);
        if (simpleUser != null) {
            if (simpleUser.getBlocked()) {
                logger.error("User with username {} is blocked", email);
                throw new BadRequestException("Your account has been blocked and system log-in is unavailable while block is active.");
            }
            else if (myUser.getDeleted()) {
                logger.error("User with username {} is deleted", email);
                throw new NotFoundException("Your account has been deleted.");
            }
        }
    }
  
    public User getByMail(String mail) {
        logger.debug("Retrieving user with username {}", mail);
        User user = userRepository.findByEmail(mail);
        return user;
    }

    private SimpleUser getByUser(User user) {
        logger.debug("Retrieving simple user with username {}", user.getEmail());
        SimpleUser simpleUser = simpleUserRepository.findByUser(user);
        return simpleUser;
    }

    public String getAdvertisementOwner(Long id) {
        logger.debug("Retrieving user with id {}", id);
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
        logger.info("User with username {} has requested password change", username);
        // validate and sanitize password data
        validateAndSanitizePasswordData(pcDTO);

        if (authenticationManager != null) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pcDTO.getOldPassword()));
            } catch (Exception e) {
                logger.error("Invalid username or password");
                throw new BadRequestException("Invalid username or password. User cannot be authenticated.");
            }
        } else {
            logger.error("Authentication manager is invalid or not working");
            throw new BadRequestException("Authentication manager is invalid or not working.");
        }

        User user = findByUsername(username);
        user.setPassword(passwordEncoder.encode(pcDTO.getNewPassword()));
        user = save(user);
        logger.info("User with username {} has successfully changed his password", username);

        return new ResponseEntity<>("Successfully changed password for user with username " + user.getEmail() + ".", HttpStatus.OK);
    }

    public ResponseEntity<?> getUnregisteredAgents() {
        logger.info("Retrieving unregistered agents");
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
        logger.info("Registering agent...");
        User user = findOne(agnRegDTO.getId());
        if(user.getEnabled()) {
            logger.error("Agent with username {} is not enabled", user.getEmail());
            throw new BadRequestException("User already registered and verified!");
        }
        if(agnRegDTO.getAccepted()) {
            user.setEnabled(true);
            save(user);
            logger.info("Agent with username {} has been enabled", user.getEmail());
            //Send confirmation email to the agent.
            MessageDTO mdto = new MessageDTO("RentaSoul Registration for " + user.getFirstName() + " " + user.getLastName(),
                    "Your registration request has been accepted. Please sign in to use our services.\n\nRentaSoul Team", true);
            try {
                logger.debug("Message for accepted agent is being sent through queue");
                queueProducer.produce(mdto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            //Send notification email about rejecting to the agent.
            MessageDTO mdto = new MessageDTO("RentaSoul Registration for " + user.getFirstName() + " " + user.getLastName(),
                    "Your registration request has been rejected. You can send new request at any moment.\n\nRentaSoul Team", true);
            try {
                logger.debug("Message for rejected agent is being sent through queue");
                queueProducer.produce(mdto);
            } catch (Exception e) {
                e.printStackTrace();
            }

            delete(agnRegDTO.getId());
            logger.info("Agent with username {} has been rejected", user.getEmail());
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>(agnRegDTO, HttpStatus.OK);
    }

    public ResponseEntity<?> activateAccount(String token) {
        logger.info("Activating account");
        Date now = new Date();
        if (token == null || token.equals("")) {
            logger.error("Activation token does not exist");
            throw new BadRequestException("Requested registration token does not exist.");
        }

        VerificationToken vt = tokenRepo.findByToken(token);
        if (vt == null) {
            logger.error("Activation token has not been found in database");
            throw new NotFoundException("Requested registration token does not exist.");
        } else if (now.after(vt.getExpiryDate())) {
            deleteUser(vt.getUser().getId());
            logger.info("Activation token has expired");
            MessageDTO mdto = new MessageDTO("RentaSoul Registration", "Your registration token has expired. Please send new registration request.", true);
            try {
                logger.debug("Message about expired token is being sent through queue");
                queueProducer.produce(mdto);
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new BadRequestException("Registration token expired. Please send new registration request.");
        }

        vt.getUser().setEnabled(true);
        save(vt.getUser());
        logger.info("User is successfully registered");

        // if everything is ok, notify user through email also
        MessageDTO mdto = new MessageDTO("RentaSoul Registration", "Activation successful. Please sign in to use our services.", true);
        try {
            logger.debug("Message about successful registration is being sent through queue");
            queueProducer.produce(mdto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Activation successful. Please sign in to use our services.", HttpStatus.OK);
    }

    public ResponseEntity<?> getUser(Long id) {
        logger.info("Retrieving user with id {}", id);
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
            logger.error("Submitted password data is corrupted or invalid");
            throw new BadRequestException("Entered data is either corrupted or invalid. Please try again with valid data.");
        } else if (!pcDTO.getNewPassword().equals(pcDTO.getNewPasswordRetype())) {
            logger.error("Password is not repeated correctly");
            throw new BadRequestException("New password is not repeated correctly. Please try again.");
        }

        pcDTO.setOldPassword(Encode.forHtml(pcDTO.getOldPassword().trim()));
        pcDTO.setNewPassword(Encode.forHtml(pcDTO.getNewPassword().trim()));
        pcDTO.setNewPasswordRetype(Encode.forHtml(pcDTO.getNewPasswordRetype().trim()));
    }

    private User findByUsername(String username) {
        logger.info("Retrieving user with username {}", username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.warn("User with username {} does not exist", username);
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
