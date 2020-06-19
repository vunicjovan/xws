package com.uns.ftn.rentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqResponseDTO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Long senderId;
    private Set<AdvertClientResponseDTO> advertisements = new HashSet<>();


}
