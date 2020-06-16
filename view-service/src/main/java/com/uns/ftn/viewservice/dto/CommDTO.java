package com.uns.ftn.viewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long advertisementId;
    private Long rentingRequestId;
}
