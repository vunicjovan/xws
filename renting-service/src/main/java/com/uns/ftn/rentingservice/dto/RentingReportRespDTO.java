package com.uns.ftn.rentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentingReportRespDTO {
    private Long id;
    private String vehicle;
    private int kilometers;
    private String content;
    private String startDate;
    private String endDate;
}
