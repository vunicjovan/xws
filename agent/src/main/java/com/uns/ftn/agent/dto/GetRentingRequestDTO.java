package com.uns.ftn.agent.dto;

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
    private String stringStartDate;
    private String stringEndDate;

}
