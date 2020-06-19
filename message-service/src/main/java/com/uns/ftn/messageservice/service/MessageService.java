package com.uns.ftn.messageservice.service;

import com.uns.ftn.messageservice.domain.Message;
import com.uns.ftn.messageservice.dto.ChatDTO;
import com.uns.ftn.messageservice.dto.MessageDTO;
import com.uns.ftn.messageservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<ChatDTO> getChat(Long id) {
        List<Message> messages = messageRepository.findAllBySenderIdOrReceiverId(id, id);
        List<ChatDTO> chat = new ArrayList<>();

        messages.forEach(message -> {

            if (message.getSenderId() != id) {
                addMessageToChat(chat, message, message.getSenderId());
            } else {
                addMessageToChat(chat, message, message.getReceiverId());
            }

        });

        return chat;
    }

    private void addMessageToChat(List<ChatDTO> chat, Message message, Long charRoomId) {
        MessageDTO messageDTO = new MessageDTO(message);

        ChatDTO chatDTO = chat.stream().filter(singleChat -> singleChat.getSenderId() == charRoomId).
                findFirst().orElse(null);

        if (chatDTO != null) {
            chat.forEach(targetChat-> {
                if (targetChat.getSenderId() == charRoomId) {
                    targetChat.getMessages().add(messageDTO);
                }
            });
        } else {
            chatDTO = new ChatDTO(charRoomId, message.getSenderUsername());
            chatDTO.getMessages().add(messageDTO);
            chat.add(chatDTO);
        }
    }

}
