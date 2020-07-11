package com.uns.ftn.agent.client;

import com.uns.ftn.agent.dto.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.*;

import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

public class AdvertisementClient extends WebServiceGatewaySupport {

    public NewAdvertisementResponse newAdvertisement(AdvertisementDTO adDTO) {
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
//        ad.setPriceListItemId(adDTO.getPriceListItemDTO().getId());
        ad.setRating(adDTO.getRating());
        ad.setVehicle(vehicle);

        request.setAdvertisement(ad);

        try {
            return (NewAdvertisementResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setOwnerId(id);
        CommentResponse commentResponse = new CommentResponse();
        try {
           commentResponse = (CommentResponse) getWebServiceTemplate()
                   .marshalSendAndReceive(commentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commentResponse;
    }

    public NewRentingIntervalResponse newRentingInterval(RentingIntervalDTO rentingIntervalDTO) {
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
            e.printStackTrace();
        }
        rentingIntervalRequest.setRentingInterval(rentingInterval);

        NewRentingIntervalResponse response = (NewRentingIntervalResponse) getWebServiceTemplate()
                .marshalSendAndReceive(rentingIntervalRequest);

        return response;
    }

    public NewCommentResponse newPublisherComment(PublisherCommentDTO publisherCommentDTO) {
        NewCommentRequest newCommentRequest = new NewCommentRequest();
        newCommentRequest.setAdvertisementId(publisherCommentDTO.getAdvertisementId());
        newCommentRequest.setContent(publisherCommentDTO.getContent());
        newCommentRequest.setTitle(publisherCommentDTO.getTitle());
        newCommentRequest.setUserId(publisherCommentDTO.getUserId());
        newCommentRequest.setAllowed(publisherCommentDTO.getAllowed());

        NewCommentResponse response = (NewCommentResponse) getWebServiceTemplate()
                .marshalSendAndReceive(newCommentRequest);

        return response;
    }

    public PriceResponse createPriceListItem(PriceListItemDTO priceListItemDTO) {
        PriceRequest priceRequest  = new PriceRequest();
        PriceListItem priceListItem = new PriceListItem();
        priceListItem.setCdwPrice(priceListItemDTO.getCdwPrice());
        priceListItem.setDailyPrice(priceListItemDTO.getDailyPrice());
        priceListItem.setDebtPrice(priceListItemDTO.getDebtPrice());
        priceListItem.setAgentId(priceListItemDTO.getCreatorId());
        priceRequest.setPriceListItem(priceListItem);

        try {
            return  (PriceResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(priceRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public UpdateAdvertisementResponse updateAdvertisement(UpdateAdvertisementDTO updateAdvertisementDTO) {
        UpdateAdvertisementRequest advertisementRequest = new UpdateAdvertisementRequest();
        advertisementRequest.setAdvertisementId(updateAdvertisementDTO.getAdvertisementId());
        advertisementRequest.setPriceListItemId(updateAdvertisementDTO.getPriceListItemId());
        advertisementRequest.setDescription(updateAdvertisementDTO.getDescription());

        try {
            return (UpdateAdvertisementResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(advertisementRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DeleteAdvertisementResponse deleteAdvertisement(Long id) {
        DeleteAdvertisementRequest advertisementRequest = new DeleteAdvertisementRequest();
        advertisementRequest.setAdvertisementId(id);

        try {
            return (DeleteAdvertisementResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(advertisementRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NewDiscountResponse createDiscount(Long id, double discount) {
        NewDiscountRequest discountRequest = new NewDiscountRequest();
        discountRequest.setOwnerId(id);
        discountRequest.setDiscount(discount);

        try {
            return (NewDiscountResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(discountRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
