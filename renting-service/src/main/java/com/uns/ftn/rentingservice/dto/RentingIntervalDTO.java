package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.RentingInterval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentingIntervalDTO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Long advertisementId;

    public RentingIntervalDTO(RentingInterval rentingInterval) {
        this.id = rentingInterval.getId();
        this.startDate = rentingInterval.getStartDate();
        this.endDate = rentingInterval.getEndDate();
        this.advertisementId = rentingInterval.getAdvertisement().getId();
    }

}
