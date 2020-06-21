package com.uns.ftn.agentservice.service.impl;

import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.User;
import com.uns.ftn.agentservice.dto.RatingDTO;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.AdvertisementRepository;
import com.uns.ftn.agentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RatingService {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    @Autowired
    public RatingService(AdvertisementRepository advertisementRepository, UserRepository userRepository) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
    }

    public RatingDTO rateAd(Long adId, RatingDTO ratingDTO) {
        Advertisement advertisement = advertisementRepository.findById(adId)
                .orElseThrow(() -> new NotFoundException("Requested advertisement doesn't exist."));

        if (ratingDTO.getUserId() == null) {
            throw new BadRequestException("Requested user cannot rate advertisement.");
        }

        User user = new User();
        user.setUserId(ratingDTO.getUserId());
        user.setRatedAds(new HashSet<>());
        user.getRatedAds().add(advertisement);
        user = userRepository.save(user);

        double oldRating = advertisement.getRating();
        int timesRated = advertisement.getRatedByUsers().size();
        double newRating = (oldRating*timesRated + ratingDTO.getRating())/(timesRated + 1);
        advertisement.setRating(newRating);

        advertisement.getRatedByUsers().add(user);
        advertisement = advertisementRepository.save(advertisement);


        return ratingDTO;
    }
}
