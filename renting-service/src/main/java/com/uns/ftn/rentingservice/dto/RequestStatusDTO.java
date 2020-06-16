package com.uns.ftn.rentingservice.dto;

import com.uns.ftn.rentingservice.domain.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestStatusDTO {
    private Long id;
    private RequestStatus status;
}
