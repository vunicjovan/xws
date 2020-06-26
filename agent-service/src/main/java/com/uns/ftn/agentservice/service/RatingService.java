package com.uns.ftn.agentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.agentservice.components.QueueProducer;
import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.User;
import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.dto.RatingDTO;
import com.uns.ftn.agentservice.dto.UserDTO;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.AdvertisementRepository;
import com.uns.ftn.agentservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RatingService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final QueueProducer queueProducer;

    @Autowired
    public RatingService(AdvertisementRepository advertisementRepository, UserRepository userRepository, QueueProducer queueProducer) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.queueProducer = queueProducer;
    }

    public RatingDTO rateAd(Long adId, RatingDTO ratingDTO) {
        logger.info("Rating advertisement with id {}", adId);
        Advertisement advertisement = advertisementRepository.findById(adId)
                .orElseThrow(() -> new NotFoundException("Requested advertisement doesn't exist."));

        if (ratingDTO.getUserId() == null) {
            logger.error("Requested user cannot rate advertisement, his id has not been assigned");
            throw new BadRequestException("Requested user cannot rate advertisement.");
        }

        User user= userRepository.findByUserId(ratingDTO.getUserId());

        if (user == null) {
            user = new User();
            user.setUserId(ratingDTO.getUserId());
            user.setRatedAds(new HashSet<>());
        }

        user.getRatedAds().add(advertisement);
        user = userRepository.save(user);

        try {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(user.getId());
//            userDTO.setUserId(user.getUserId());
            logger.debug("Sending user through queue");
            queueProducer.produceUser(new UserDTO(user));
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        double oldRating = advertisement.getRating();
        int timesRated = advertisement.getRatedByUsers().size();
        double newRating = (oldRating*timesRated + ratingDTO.getRating())/(timesRated + 1);
        advertisement.setRating(newRating);
        logger.info("Advertisement rating changed to {}", newRating);

        advertisement.getRatedByUsers().add(user);
        advertisement = advertisementRepository.save(advertisement);
        logger.info("Advertisement with id {} successfully saved", advertisement.getId());

        return ratingDTO;
    }
}
