package com.uns.ftn.rentingservice.dto;

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
public class RentingRequestDTO {

    private Date startDate;
    private Date endDate;
    private Long senderId;
    private Set<Long> advertisementIDs;

}
