package com.uns.ftn.viewservice.service;

import com.uns.ftn.viewservice.client.AccountClient;
import com.uns.ftn.viewservice.domain.*;
import com.uns.ftn.viewservice.dto.*;
import com.uns.ftn.viewservice.exceptions.NotFoundException;
import com.uns.ftn.viewservice.repository.AdvertisementRepository;
import com.uns.ftn.viewservice.repository.CommentRepository;
import com.uns.ftn.viewservice.repository.ModelRepository;
import net.bytebuddy.asm.Advice;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewService.class);

    private AdvertisementRepository advertisementRepository;
    private AccountClient accountClient;
    private CommentRepository commentRepository;

    @Autowired
    public ViewService(CommentRepository commentRepository, AdvertisementRepository advertisementRepository, AccountClient accountClient) {
        this.commentRepository = commentRepository;
        this.advertisementRepository = advertisementRepository;
        this.accountClient = accountClient;
    }

    public Set<SimpleAdvertisementDTO> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementRepository.findAll();
        Set<SimpleAdvertisementDTO> simpleAdvertisementDTOSet = new HashSet<>();

        advertisements.forEach(advertisement -> simpleAdvertisementDTOSet.add(new SimpleAdvertisementDTO(
                advertisement.getId(),
                advertisement.getPrice(),
                advertisement.getLocation(),
                advertisement.getVehicle().getModel().getBrand().getName(),
                advertisement.getVehicle().getModel().getName(),
                advertisement.getPhotos().stream().map(photo -> photo.getPath()).collect(Collectors.toSet())
        )));

        if(simpleAdvertisementDTOSet.size() == 0) {
            LOGGER.warn("Advertisement list size equals 0.");
        }

        return simpleAdvertisementDTOSet;
    }

    public DetailedAdvertisementDTO getAdvertisement(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advertisement with that id does not exist!"));

        String owner;

        try {
            LOGGER.debug("Account feign client request user[id={}]", advertisement.getOwnerId());
            owner = accountClient.getOwnerName(advertisement.getOwnerId());
            LOGGER.debug("Account feign client responses user[name={}]", owner);
        } catch (Exception e) {
            LOGGER.error("Error occurred while contacting account feign client", e);
            throw new NotFoundException("Advertisement owner does not exist!");
        }

        DetailedAdvertisementDTO detailedAdvertisementDTO = new DetailedAdvertisementDTO(
                advertisement.getId(),
                advertisement.getVehicle().getModel().getBrand().getName(),
                advertisement.getVehicle().getModel().getName(),
                advertisement.getVehicle().getVehicleClass().getName(),
                advertisement.getVehicle().getGearboxType().getName(),
                advertisement.getVehicle().getFuelType().getName(),
                owner,
                advertisement.getLocation(),
                advertisement.getPrice(),
                advertisement.getVehicle().getKilometersTraveled(),
                advertisement.getCollisionDamageWaiver(),
                advertisement.getKilometersPerDayLimit(),
                advertisement.getVehicle().getChildSeatNumber(),
                advertisement.getVehicle().getHasAndroid(),
                advertisement.getDescription(),
                advertisement.getPhotos().stream().map(photo -> photo.getPath()).collect(Collectors.toSet()),
                advertisement.getOwnerId(),
                getCommentsForDisplay(advertisement.getId())
        );

        return detailedAdvertisementDTO;
    }

    public AdvertClientResponseDTO getAdvert(Long id) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested advertisement doesn't exist."));

        return new AdvertClientResponseDTO(ad.getId(), ad.getVehicle().getModel().getName(),
                ad.getVehicle().getModel().getBrand().getName(), ad.getLocation(), ad.getPrice(),
                ad.getRatedByUsers().stream().map(UserDTO::new).collect(Collectors.toSet()));
    }

    public Set<SimpleAdvertisementDTO> getAgentsAdvertisements(Long id) {
        return advertisementRepository.findAllByOwnerId(id).stream()
                .map(SimpleAdvertisementDTO::new).collect(Collectors.toSet());
    }



    public Set<CartAdvertisementDTO> getCartAdvertisements(List<Long> advertisementIdList) {
        List<Advertisement> advertisements = advertisementRepository.findAllById(advertisementIdList);

        return advertisements.stream().
                map(advertisement -> new CartAdvertisementDTO(advertisement)).collect(Collectors.toSet());
    }

    public CommentClientResponseDTO getCommentClient(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with that id doesn't exist."));

        return new CommentClientResponseDTO(comment.getId(), comment.getTitle(), comment.getContent());
    }

    public Set<CommentDisplayDTO> getCommentsForDisplay(Long adId) {
        List<Comment> comments = commentRepository.getAllByAdvertisement_Id(adId);

        return comments.stream().map(comment -> new CommentDisplayDTO(comment)).collect(Collectors.toSet());
    }

}
