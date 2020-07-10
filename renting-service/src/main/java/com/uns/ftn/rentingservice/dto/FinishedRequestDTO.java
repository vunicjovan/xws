package com.uns.ftn.rentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinishedRequestDTO {
    private Long requestId;
    private String vehicle;
    private String startDate;
    private String endDate;
    private Long advertisementID;
}
