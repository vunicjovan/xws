package com.uns.ftn.agentservice.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.uns.ftn.agentservice.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Boolean allowed;
    private String owner;
    private Long advertisementId;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.allowed = comment.getAllowed();
    }
}
