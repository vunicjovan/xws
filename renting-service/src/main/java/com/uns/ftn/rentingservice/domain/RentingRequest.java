package com.uns.ftn.rentingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
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

   @Column(name = "senderId", nullable = false)
   private Long senderId;

   @OneToMany(mappedBy = "rentingRequest", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
   private Set<RentingReport> rentingReports;

   @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
   @JoinTable(name = "renting_request_advertisements", joinColumns = @JoinColumn(name = "renting_request_id",
           referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"))
   private Set<Advertisement> advertisements;

   @OneToMany(mappedBy = "rentingRequest", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
   private  Set<Comment> comments;

}