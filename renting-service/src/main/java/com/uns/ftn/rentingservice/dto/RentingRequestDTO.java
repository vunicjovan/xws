package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentingRequestDTO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Long senderId;
    private Set<Long> advertisementIDs;

    public RentingRequestDTO(RentingRequest rentingRequest) {
        this.id = rentingRequest.getId();
        this.startDate = rentingRequest.getStartDate();
        this.endDate = rentingRequest.getEndDate();
        this.senderId = rentingRequest.getSenderId();
        this.advertisementIDs = rentingRequest.getAdvertisements().stream().map(Advertisement::getId).collect(Collectors.toSet());
    }

}
