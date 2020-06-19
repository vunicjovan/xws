package com.uns.ftn.messageservice.dto;

import com.uns.ftn.messageservice.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {

    private Long senderId;
    private String senderUsername;
    private List<MessageDTO> messages;

    public ChatDTO(Long id, String senderUsername) {
        this.senderId = id;
        this.senderUsername = senderUsername;
        this.messages = new ArrayList<>();
    }

}
