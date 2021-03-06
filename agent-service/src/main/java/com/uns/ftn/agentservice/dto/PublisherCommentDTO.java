package com.uns.ftn.agentservice.dto;

import com.uns.ftn.agentservice.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PublisherCommentDTO {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long advertisementId;
    private Boolean allowed;

    public PublisherCommentDTO(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.advertisementId = comment.getAdvertisement().getId();
        this.userId = comment.getUserId();
        this.allowed = comment.getAllowed();
    }

    public PublisherCommentDTO(rs.ac.uns.ftn.advertisement.Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.userId = comment.getUserId();
        this.allowed = comment.isAllowed();
    }

}
