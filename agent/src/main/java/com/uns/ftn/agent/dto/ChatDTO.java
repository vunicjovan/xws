package com.uns.ftn.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long senderId;
    private String senderUsername;
    private List<MessageDTO> messages = new ArrayList<>();
}
