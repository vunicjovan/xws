package com.uns.ftn.agent.dto;

import com.uns.ftn.agent.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String title;
    private String content;
    private Boolean allowed;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.allowed = comment.getAllowed();
    }
}
