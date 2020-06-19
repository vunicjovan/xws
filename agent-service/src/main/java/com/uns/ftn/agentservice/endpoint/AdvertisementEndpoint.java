package com.uns.ftn.agentservice.endpoint;

import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.dto.CommDTO;
import com.uns.ftn.agentservice.service.AdvertisementService;
import com.uns.ftn.agentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.advertisement.*;

import java.util.List;

@Endpoint
public class AdvertisementEndpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/advertisement";

    private AdvertisementService advertisementService;
    private CommentService commentService;

    @Autowired
    public AdvertisementEndpoint(AdvertisementService advertisementService, CommentService commentService) {
        this.advertisementService = advertisementService;
        this.commentService = commentService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "newAdvertisementRequest")
    @ResponsePayload
    public NewAdvertisementResponse newAdvertisement(@RequestPayload NewAdvertisementRequest request) {
        NewAdvertisementResponse response = new NewAdvertisementResponse();
        AdvertisementDTO adDTO = new AdvertisementDTO(request.getAdvertisement());

        ResponseEntity<?> resp =  advertisementService.postNewAd(adDTO);



        if (resp.getStatusCode().equals(HttpStatus.CREATED)) {

            adDTO = (AdvertisementDTO) resp.getBody();
            Advertisement ad = new Advertisement();
            Vehicle vehicle = new Vehicle();

            assert adDTO != null;
            vehicle.setId(adDTO.getVehicle().getId());
            vehicle.setChildSeatNumber(adDTO.getVehicle().getChildSeatNumber());
            vehicle.setFuelTypeId(adDTO.getVehicle().getFuelTypeId());
            vehicle.setGearboxTypeId(adDTO.getVehicle().getGearboxTypeId());
            vehicle.setHasAndroid(adDTO.getVehicle().getHasAndroid());
            vehicle.setKilometersTraveled(adDTO.getVehicle().getKilometersTraveled());
            vehicle.setModelId(adDTO.getVehicle().getModelId());
            vehicle.setVehicleClassId(adDTO.getVehicle().getVehicleClassId());

            ad.setId(adDTO.getId());
            ad.setCollisionDamageWaiver(adDTO.getCollisionDamageWaiver());
            ad.setKilometersPerDayLimit(adDTO.getKilometersPerDayLimit());
            ad.setDescription(adDTO.getDescription());
            ad.setLocation(adDTO.getLocation());
            ad.setOwnerId(adDTO.getOwnerId());
            ad.setPrice(adDTO.getPrice());
            ad.setRating(adDTO.getRating());
            ad.setVehicle(vehicle);

            response.setAdvertisement(ad);

            return response;

        } else {
            return null;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "commentRequest")
    @ResponsePayload
    public CommentResponse getComments(@RequestPayload CommentRequest commentRequest) {
        CommentResponse commentResponse = new CommentResponse();
        List<CommDTO> commDTOlist = commentService.getCommentsByAdvertisementOwner(commentRequest.getOwnerId());
        commDTOlist.forEach(commDTO -> {
            Comment comment = new Comment();
            comment.setId(commDTO.getId());
            comment.setTitle(commDTO.getTitle());
            comment.setContent(commDTO.getContent());
            comment.setAllowed(true);
            comment.setUserId(commDTO.getUserId());
            comment.setRentingRequestId(1);
            comment.setAdvertisementId(commDTO.getAdvertisementId());
            commentResponse.getComments().add(comment);
        });

        return commentResponse;
    }

}
