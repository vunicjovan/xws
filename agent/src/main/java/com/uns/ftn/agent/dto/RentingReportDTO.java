package com.uns.ftn.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentingReportDTO {

    private int kilometersTraveled;
    private String content;
    private Long requestID;
    private Long advertisementID;

}
