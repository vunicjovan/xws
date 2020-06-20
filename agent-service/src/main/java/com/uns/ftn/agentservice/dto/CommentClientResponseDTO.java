package com.uns.ftn.agentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentClientResponseDTO {
    private Long id;
    private String title;
    private String content;
}
