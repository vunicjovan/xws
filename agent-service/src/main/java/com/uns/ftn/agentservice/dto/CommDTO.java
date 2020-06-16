package com.uns.ftn.agentservice.dto;

import com.uns.ftn.agentservice.domain.Comment;
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

    public CommDTO(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.userId = comment.getUserId();
        this.advertisementId = comment.getAdvertisement().getId();
    }

}
