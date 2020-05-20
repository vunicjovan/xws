package com.uns.ftn.rentingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "rentingRequest")
public class RentingRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "startDate", nullable = false)
   private Date startDate;

   @Column(name = "endDate", nullable = false)
   private Date endDate;

   @Column(name = "status", nullable = false)
   @Enumerated(EnumType.ORDINAL)
   private RequestStatus status = RequestStatus.pending;

   @OneToMany(mappedBy = "rentingRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private java.util.Set<RentingReport> rentingReports;

}