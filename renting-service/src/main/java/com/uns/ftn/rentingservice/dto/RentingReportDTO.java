package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.RentingReport;
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

    public RentingReportDTO(RentingReport report) {
        this.kilometersTraveled = report.getKilometersTraveled();
        this.content = report.getContent();
        this.requestID = report.getRentingRequest().getId();
        this.advertisementID = report.getAdvertisement().getId();
    }

}
