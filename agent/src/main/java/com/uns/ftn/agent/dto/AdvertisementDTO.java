package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDTO {

    private Long id;
    private VehicleDTO vehicle;
    private double price;
    private int kilometersPerDayLimit;
    private Boolean collisionDamageWaiver;
    private String location;
    private String description;
    private Long ownerId;
    private double rating;
    private PriceListItemDTO priceListItemDTO;
    private Boolean deleted = false;

    public AdvertisementDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.vehicle = new VehicleDTO(advertisement.getVehicle());
        this.price = advertisement.getPrice();
        this.kilometersPerDayLimit = advertisement.getKilometersPerDayLimit();
        this.collisionDamageWaiver = advertisement.getCollisionDamageWaiver();
        this.location = advertisement.getLocation();
        this.description = advertisement.getDescription();
        this.ownerId = advertisement.getOwnerId();
        this.rating = advertisement.getRating();
        this.deleted = advertisement.getDeleted();
        this.priceListItemDTO = new PriceListItemDTO(advertisement.getPriceListItem());
    }

}
