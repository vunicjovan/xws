package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.domain.Agent;
import com.uns.ftn.accountservice.domain.Company;
import com.uns.ftn.accountservice.domain.SimpleUser;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.UserDTO;
import com.uns.ftn.accountservice.repository.AgentRepository;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserDTO registerUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return null;
        }

        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPassword());

        if (userDTO.getIsAgent()) {
            Company company = companyService.getOneByBusinessNumber(userDTO.getCompanyBusinessNumber());
            if (company == null) {
                return null;
            } else {
                Agent agent = new Agent(user, company);
                agentRepository.save(agent);
            }
        } else {
            SimpleUser simpleUser = new SimpleUser(user);
            simpleUserRepository.save(simpleUser);
        }

        userRepository.save(user);

        return userDTO;
    }

    public User getByMail(String mail) {
        User user = userRepository.findByEmail(mail);
        return user;
    }
}
