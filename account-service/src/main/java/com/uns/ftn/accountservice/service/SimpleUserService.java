package com.uns.ftn.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.accountservice.components.QueueProducer;
import com.uns.ftn.accountservice.domain.SimpleUser;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.MessageDTO;
import com.uns.ftn.accountservice.dto.SimpleUserDTO;
import com.uns.ftn.accountservice.exceptions.NotFoundException;
import com.uns.ftn.accountservice.repository.RoleRepository;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SimpleUserService {

    private SimpleUserRepository simpleRepo;
    private UserRepository userRepo;
    private RoleRepository roleRepository;
    private QueueProducer queueProducer;

    @Autowired
    public SimpleUserService(SimpleUserRepository simpleRepo, UserRepository userRepo, QueueProducer queueProducer) {
        this.simpleRepo = simpleRepo;
        this.userRepo = userRepo;
        this.queueProducer = queueProducer;
    }

    public Set<SimpleUserDTO> getSimpleUsers() {
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

            if (simpleUser.getBlocked()) {
                MessageDTO mdto = new MessageDTO("RentaSoul Platform",
                        "Your account has been blocked. You will be unable to sign in until administrator allows you to.", true);
                try {
                    queueProducer.produce(mdto);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                MessageDTO mdto = new MessageDTO("RentaSoul Platform",
                        "Your account has been unblocked. You can sign in to system again.", true);
                try {
                    queueProducer.produce(mdto);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            return new ResponseEntity<>(simpleUserDTO, HttpStatus.OK);
        } else {
            throw new NotFoundException("User with given id does not exist!");
        }
    }

}
