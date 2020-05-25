package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.domain.*;
import com.uns.ftn.accountservice.dto.UserDTO;
import com.uns.ftn.accountservice.repository.AgentRepository;
import com.uns.ftn.accountservice.repository.RoleRepository;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserDTO userDTO) {

        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE)([a-zA-Z0-9\\\\!\\\\?\\\\#\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (!validateUser(userDTO, pattern)) {
            return null;
        }

        sanitizeUserData(userDTO);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return null;
        }

        User user = new User(userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmail(),
                                                                        passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getIsAgent()) {
            Company company = companyService.getOneByBusinessNumber(userDTO.getCompanyBusinessNumber());
            if (company == null) {
                return null;
            } else {
                user.getRoles().add(roleRepository.findByName("AGENT"));
                userRepository.save(user);
                Agent agent = new Agent(user, company);
                agentRepository.save(agent);
            }
        } else {
            user.getRoles().add(roleRepository.findByName("SIMPLE_USER"));
            userRepository.save(user);
            SimpleUser simpleUser = new SimpleUser(user);
            simpleUserRepository.save(simpleUser);
        }

        return userDTO;
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
        if (userDTO.getFirstName().trim().equals("") || userDTO.getFirstName() == null ||
            userDTO.getLastName().trim().equals("") || userDTO.getLastName() == null ||
            userDTO.getEmail().trim().equals("") || userDTO.getEmail() == null ||
            userDTO.getPassword().trim().equals("") || userDTO.getPassword() == null ||
            userDTO.getRepeatPassword().trim().equals("") || userDTO.getRepeatPassword() == null ||
            !userDTO.getRepeatPassword().equals(userDTO.getPassword()) ||
            !pattern.matcher(userDTO.getFirstName().trim()).matches() ||
            !pattern.matcher(userDTO.getLastName().trim()).matches() ||
            (userDTO.getEmail().trim().split("@").length <= 1) ||
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
        if (email.trim().equals("") || email == null || (email.trim().split("@").length <= 1) ||
            password.trim().equals("") || password == null || !pattern.matcher(password.trim()).matches()) {
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
}
