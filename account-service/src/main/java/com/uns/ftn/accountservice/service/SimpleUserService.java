package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.domain.SimpleUser;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.SimpleUserDTO;
import com.uns.ftn.accountservice.exceptions.NotFoundException;
import com.uns.ftn.accountservice.repository.RoleRepository;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SimpleUserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SimpleUserRepository simpleRepo;
    private UserRepository userRepo;
    private RoleRepository roleRepository;

    @Autowired
    public SimpleUserService(SimpleUserRepository simpleRepo, UserRepository userRepo) {
        this.simpleRepo = simpleRepo;
        this.userRepo = userRepo;
    }

    public Set<SimpleUserDTO> getSimpleUsers() {
        logger.info("Retrieving simple users");
        List<SimpleUser> simpleUsers = simpleRepo.findAll();

        Set<SimpleUserDTO> simpleUserDTOSet = new HashSet<>();

        simpleUsers.forEach(simpleUser -> {
            User user = userRepo.findById(simpleUser.getUser().getId()).orElse(null);
            if (user != null && !user.getDeleted()) {
                simpleUserDTOSet.add(new SimpleUserDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        simpleUser.getNumberOfCancelations(),
                        simpleUser.getBlocked()
                ));
            }

        });

        return simpleUserDTOSet;
    }

    public ResponseEntity<?> blockUser(Long userId) {
        logger.info("Blocking user with id {}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new NotFoundException("User with given id does not exist!"));
        SimpleUser simpleUser = this.simpleRepo.findByUser(user);

        if (simpleUser != null && !user.getDeleted()) {
            simpleUser.setBlocked(!simpleUser.getBlocked());
            simpleUser = this.simpleRepo.save(simpleUser);
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    simpleUser.getNumberOfCancelations(),
                    simpleUser.getBlocked()
            );
            logger.info("User with username {} has been blocked", user.getEmail());
            return new ResponseEntity<>(simpleUserDTO, HttpStatus.OK);
        } else {
            logger.error("User with id does not exist", userId);
            throw new NotFoundException("User with given id does not exist!");
        }
    }

}
