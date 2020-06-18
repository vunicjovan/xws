package com.uns.ftn.agent.client;

import com.uns.ftn.agent.dto.AdvertisementDTO;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.*;

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
        ad.setRating(adDTO.getRating());
        ad.setVehicle(vehicle);

        request.setAdvertisement(ad);

        NewAdvertisementResponse response = (NewAdvertisementResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response;
    }
}
