package com.uns.ftn.agent.client;

import com.uns.ftn.agent.dto.AdvertisementDTO;
import com.uns.ftn.agent.dto.PhotoRequestDTO;
import com.uns.ftn.agent.dto.PublisherCommentDTO;
import com.uns.ftn.agent.dto.RentingIntervalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.*;

import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

public class AdvertisementClient extends WebServiceGatewaySupport {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public NewAdvertisementResponse newAdvertisement(AdvertisementDTO adDTO) {
        logger.info("Creating advertisement request for microservices");
        NewAdvertisementRequest request = new NewAdvertisementRequest();
        Vehicle vehicle = new Vehicle();
        Advertisement ad = new Advertisement();


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

        request.setAdvertisement(ad);

        logger.debug("Sending advertisement with vehicle model id {} via soap", adDTO.getVehicle().getModelId());
        NewAdvertisementResponse response = (NewAdvertisementResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response;
    }


    public NewPhotoResponse newPhoto(PhotoRequestDTO photoRequestDTO) {
        NewPhotoRequest request = new NewPhotoRequest();
        request.setBytes(photoRequestDTO.getBytes());
        request.setPath(photoRequestDTO.getPath());
        request.setAdId(photoRequestDTO.getId());
        NewPhotoResponse response = (NewPhotoResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

    public CommentResponse getComments(Long id) {
        logger.info("Creating request for retrieving comments from microservices");
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setOwnerId(id);

        logger.debug("Sending request via soap for retrieving comments posted by agent with id {}", id);
        CommentResponse commentResponse =(CommentResponse) getWebServiceTemplate()
                .marshalSendAndReceive(commentRequest);

        return commentResponse;
    }

    public NewRentingIntervalResponse newRentingInterval(RentingIntervalDTO rentingIntervalDTO) {
        logger.info("Creating request for new renting interval for advertisement with id {}", rentingIntervalDTO.getAdvertisementId());
        NewRentingIntervalRequest rentingIntervalRequest = new NewRentingIntervalRequest();
        rentingIntervalRequest.setAdvertisementId(rentingIntervalDTO.getAdvertisementId());

        RentingInterval rentingInterval = new RentingInterval();

        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.setTime(rentingIntervalDTO.getStartDate());
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.setTime(rentingIntervalDTO.getStartDate());
        try {
            rentingInterval.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc1));
            rentingInterval.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc2));
        } catch (Exception e) {
            logger.error("Not valid date formats for renting intervals");
            e.printStackTrace();
        }
        rentingIntervalRequest.setRentingInterval(rentingInterval);

        logger.debug("Sending interval({} - {}) via soap", rentingIntervalDTO.getStartDate(), rentingIntervalDTO.getEndDate());
        NewRentingIntervalResponse response = (NewRentingIntervalResponse) getWebServiceTemplate()
                .marshalSendAndReceive(rentingIntervalRequest);

        return response;
    }

    public NewCommentResponse newPublisherComment(PublisherCommentDTO publisherCommentDTO) {
        logger.info("Creating request for new comment for advertisement {}", publisherCommentDTO.getAdvertisementId());
        NewCommentRequest newCommentRequest = new NewCommentRequest();
        newCommentRequest.setAdvertisementId(publisherCommentDTO.getAdvertisementId());
        newCommentRequest.setContent(publisherCommentDTO.getContent());
        newCommentRequest.setTitle(publisherCommentDTO.getTitle());
        newCommentRequest.setUserId(publisherCommentDTO.getUserId());

        logger.debug("Sending new comment with title {} via soap", publisherCommentDTO.getTitle());
        NewCommentResponse response = (NewCommentResponse) getWebServiceTemplate().
                marshalSendAndReceive(newCommentRequest);

        return response;
    }
}
