package com.uns.ftn.viewservice.dto;

import com.uns.ftn.viewservice.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDisplayDTO {

    private Long id;
    private String title;
    private String content;

    public CommentDisplayDTO(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
    }
}
