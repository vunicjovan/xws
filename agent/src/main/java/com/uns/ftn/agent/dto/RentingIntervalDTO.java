package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.RentingInterval;
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

    private Date startDate;
    private Date endDate;
    private Long advertisementId;

    public RentingIntervalDTO(RentingInterval rentingInterval) {
        this.startDate = rentingInterval.getStartDate();
        this.endDate = rentingInterval.getEndDate();
        this.advertisementId = rentingInterval.getAdvertisement().getId();
    }

    public RentingIntervalDTO(rs.ac.uns.ftn.catalog.RentingInterval rentingInterval) {
        this.startDate = rentingInterval.getStartDate().toGregorianCalendar().getTime();
        this.endDate = rentingInterval.getEndDate().toGregorianCalendar().getTime();
//        this.advertisementId = rentingInterval.get
    }
}
