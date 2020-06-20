package com.uns.ftn.rentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableCommentDTO {
    private AdvertClientResponseDTO advertisement;
    private boolean commentAvailable;
    private Long rentingRequestId;
    private Set<RentingIntervalDTO> rentingIntervals = new HashSet<>();
    private Set<CommentClientResponseDTO> comments = new HashSet<>();
}
