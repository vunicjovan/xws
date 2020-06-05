package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.RentingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateResponseDTO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Long senderId;

    public CreateResponseDTO(RentingRequest req) {
        this.id = req.getId();
        this.startDate = req.getStartDate();
        this.endDate = req.getEndDate();
        this.senderId = req.getSenderId();
    }

}
