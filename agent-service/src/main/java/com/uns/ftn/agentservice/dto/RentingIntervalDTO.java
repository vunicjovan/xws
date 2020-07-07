package com.uns.ftn.agentservice.dto;

import com.uns.ftn.agentservice.domain.RentingInterval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public RentingIntervalDTO(rs.ac.uns.ftn.advertisement.RentingInterval rentingInterval) {
        this.startDate = rentingInterval.getStartDate().toGregorianCalendar().getTime();
        this.endDate = rentingInterval.getEndDate().toGregorianCalendar().getTime();
//        this.advertisementId = rentingInterval.get
    }
}
