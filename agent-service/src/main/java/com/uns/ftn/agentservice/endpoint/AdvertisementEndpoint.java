package com.uns.ftn.agentservice.endpoint;

import com.uns.ftn.agentservice.dto.*;
import com.uns.ftn.agentservice.service.AdvertisementService;
import com.uns.ftn.agentservice.service.CommentService;
import com.uns.ftn.agentservice.service.PriceListService;
import com.uns.ftn.agentservice.service.RentingIntervalService;
import com.uns.ftn.agentservice.service.impl.PhotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.advertisement.*;

import java.io.IOException;
import java.util.List;

@Endpoint
public class AdvertisementEndpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/advertisement";

    private AdvertisementService advertisementService;
    private PhotoServiceImpl photoService;
    private CommentService commentService;
    private final RentingIntervalService rentingIntervalService;
    private PriceListService priceListService;

    @Autowired
    public AdvertisementEndpoint(AdvertisementService advertisementService, CommentService commentService,
                                 PhotoServiceImpl photoService, RentingIntervalService rentingIntervalService,
                                 PriceListService priceListService) {
        this.advertisementService = advertisementService;
        this.commentService = commentService;
        this.photoService = photoService;
        this.rentingIntervalService = rentingIntervalService;
        this.priceListService = priceListService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "newAdvertisementRequest")
    @ResponsePayload
    public NewAdvertisementResponse newAdvertisement(@RequestPayload NewAdvertisementRequest request) {
        NewAdvertisementResponse response = new NewAdvertisementResponse();
        AdvertisementDTO adDTO = new AdvertisementDTO(request.getAdvertisement());

        ResponseEntity<?> resp = advertisementService.postNewAd(adDTO);


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


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "newPhotoRequest")
    @ResponsePayload
    public NewPhotoResponse newPhoto(@RequestPayload NewPhotoRequest request) throws IOException {
        NewPhotoResponse response = new NewPhotoResponse();
        photoService.storeSOAP(request);

        response.setPath(request.getPath());

        return response;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "newRentingIntervalRequest")
    @ResponsePayload
    public NewRentingIntervalResponse newRentingInterval(@RequestPayload NewRentingIntervalRequest rentRequest) {
        NewRentingIntervalResponse rentingIntervalResponse = new NewRentingIntervalResponse();
        RentingIntervalDTO rentingIntervalDTO = new RentingIntervalDTO(rentRequest.getRentingInterval());
        rentingIntervalDTO.setAdvertisementId(rentRequest.getAdvertisementId());

        RentingIntervalDTO responseDTO = rentingIntervalService.manuallyAddInterval(rentingIntervalDTO);

        RentingInterval rentingInterval = new RentingInterval();
        rentingInterval.setStartDate(rentRequest.getRentingInterval().getStartDate());
        rentingInterval.setEndDate(rentRequest.getRentingInterval().getEndDate());
        rentingIntervalResponse.setRentingInterval(rentingInterval);

        return rentingIntervalResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "newCommentRequest")
    @ResponsePayload
    public NewCommentResponse newComment(@RequestPayload NewCommentRequest commentRequest) {
        NewCommentResponse commentResponse = new NewCommentResponse();
        PublisherCommentDTO publisherCommentDTO = new PublisherCommentDTO();
        publisherCommentDTO.setContent(commentRequest.getContent());
        publisherCommentDTO.setTitle(commentRequest.getTitle());
        publisherCommentDTO.setUserId(commentRequest.getUserId());
        publisherCommentDTO.setAdvertisementId(commentRequest.getAdvertisementId());
        publisherCommentDTO.setId(commentRequest.getId());
        publisherCommentDTO.setAllowed(commentRequest.isAllowed());

        PublisherCommentDTO responseDTO = commentService.publisherPostComment(publisherCommentDTO);

        Comment comment = new Comment();
        comment.setAdvertisementId(responseDTO.getAdvertisementId());
        comment.setContent(responseDTO.getContent());
        comment.setTitle(responseDTO.getTitle());
        comment.setUserId(responseDTO.getUserId());
        comment.setId(responseDTO.getId());
        comment.setAllowed(responseDTO.getAllowed());
        commentResponse.setComment(comment);

        return commentResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "priceRequest")
    @ResponsePayload
    public PriceResponse createPriceListItem(@RequestPayload PriceRequest priceRequest) {
        PriceResponse priceResponse = new PriceResponse();

        PriceListItemDTO priceListItemDTO = new PriceListItemDTO();
        priceListItemDTO.setCdwPrice(priceRequest.getPriceListItem().getCdwPrice());
        priceListItemDTO.setDailyPrice(priceRequest.getPriceListItem().getDailyPrice());
        priceListItemDTO.setDebtPrice(priceRequest.getPriceListItem().getDebtPrice());
        priceListItemDTO.setCreatorId(priceRequest.getPriceListItem().getAgentId());

        priceListItemDTO = priceListService.createPriceListItem(priceListItemDTO);

        PriceListItem priceListItem = new PriceListItem();
        priceListItem.setCdwPrice(priceListItemDTO.getCdwPrice());
        priceListItem.setDailyPrice(priceListItemDTO.getDailyPrice());
        priceListItem.setDebtPrice(priceListItemDTO.getDebtPrice());
        priceListItem.setAgentId(priceListItemDTO.getCreatorId());
        priceListItem.setServiceId(priceListItemDTO.getId());

        priceResponse.setPriceListItem(priceListItem);

        return priceResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAdvertisementRequest")
    @ResponsePayload
    public UpdateAdvertisementResponse updateAdvertisement(@RequestPayload UpdateAdvertisementRequest advertisementRequest) {
        UpdateAdvertisementResponse advertisementResponse = new UpdateAdvertisementResponse();

        AdvertisementUpdateDTO advertisementDTO = new AdvertisementUpdateDTO();
        advertisementDTO.setDescription(advertisementRequest.getDescription());
        advertisementDTO.setPriceListItemId(advertisementRequest.getPriceListItemId());

        ResponseEntity<?> response = advertisementService.updateAdvertisement(advertisementRequest.getAdvertisementId(), advertisementDTO);


        if (response.getStatusCode().equals(HttpStatus.OK)) {
            AdvertisementDTO adDTO = (AdvertisementDTO) response.getBody();
            advertisementResponse.setAdvertisementId(adDTO.getId());
            advertisementResponse.setDescription(adDTO.getDescription());
            advertisementResponse.setPriceListItemId(adDTO.getPriceListItemId()); // TODO: adDto.getPriceListItem()
            return advertisementResponse;
        } else {
            return null;
        }
    }

}
