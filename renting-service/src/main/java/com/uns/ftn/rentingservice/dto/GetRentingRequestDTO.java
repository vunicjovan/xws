package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.RentingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetRentingRequestDTO {

    private Long requestId;
    private Date startDate;
    private Date endDate;
    private Long advertisementID;

    public GetRentingRequestDTO(RentingRequest request) {
        this.requestId = request.getId();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }

}
