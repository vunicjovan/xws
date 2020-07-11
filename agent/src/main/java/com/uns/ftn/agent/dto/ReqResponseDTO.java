package com.uns.ftn.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Set<Long> advertisements = new HashSet<>();

}
