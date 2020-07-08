package com.uns.ftn.accountservice.service;

import com.uns.ftn.accountservice.domain.SimpleUser;
import com.uns.ftn.accountservice.domain.User;
import com.uns.ftn.accountservice.dto.AdCountDTO;
import com.uns.ftn.accountservice.exceptions.NotFoundException;
import com.uns.ftn.accountservice.repository.SimpleUserRepository;
import com.uns.ftn.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPumpService {

    private final SimpleUserRepository simpleUserRepository;
    private final UserRepository userRepository;

    public DataPumpService(SimpleUserRepository simpleUserRepository, UserRepository userRepository) {
        this.simpleUserRepository = simpleUserRepository;
        this.userRepository = userRepository;
    }

    public void adCountHandler(AdCountDTO adCountDTO) {
        User user = userRepository.findById(adCountDTO.getUserId()).orElseThrow(null);
        if (user == null) {
            throw new NotFoundException("User does not exist.");
        }
        SimpleUser simpleUser = simpleUserRepository.findByUser(user);
        simpleUser.setNumberOfAds(adCountDTO.getAdCount());

        simpleUserRepository.save(simpleUser);
    }
}
