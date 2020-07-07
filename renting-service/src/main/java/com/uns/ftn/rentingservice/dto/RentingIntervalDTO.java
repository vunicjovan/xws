package com.uns.ftn.rentingservice.dto;

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
}
