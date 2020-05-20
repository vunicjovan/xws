package com.uns.ftn.rentingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class RentingRequest {

   private Long id;
   private Date startDate;
   private Date endDate;
   private RequestStatus status = RequestStatus.pending;
   
   private java.util.Set<RentingReport> rentingReports;

}