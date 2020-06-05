package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.domain.SimpleUser;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.exceptions.NotFoundException;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SimpleUserService {

    private SimpleUserRepository simpleRepo;
    private UserRepository userRepo;

    @Autowired
    public SimpleUserService(SimpleUserRepository simpleRepo, UserRepository userRepo) {
        this.simpleRepo = simpleRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> blockUser(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new NotFoundException("User with given id does not exist!"));
        SimpleUser simpleUser = this.simpleRepo.findByUser(user);

        if (simpleUser != null && !user.getDeleted()) {
            simpleUser.setBlocked(!simpleUser.getBlocked());
            simpleUser = this.simpleRepo.save(simpleUser);

            return new ResponseEntity<>(simpleUser, HttpStatus.OK);
        }
        else {
            throw new NotFoundException("User with given id does not exist!");
        }
    }

}
