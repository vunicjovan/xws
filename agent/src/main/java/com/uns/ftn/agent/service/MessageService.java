package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.MessageClient;
import com.uns.ftn.agent.dto.ChatDTO;
import com.uns.ftn.agent.dto.MessageDTO;
import com.uns.ftn.agent.exceptions.BadRequestException;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.MessageResponse;
import rs.ac.uns.ftn.catalog.NewMessageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private MessageClient messageClient;

    @Autowired
    public MessageService(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    public List<ChatDTO> getChat() {
        List<ChatDTO> messages = new ArrayList<>();

        MessageResponse messageResponse = messageClient.getMessages((long) 2);

        messageResponse.getChat().forEach(chat -> {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setSenderId(chat.getSenderId());
            chatDTO.setSenderUsername(chat.getSenderUsername());
            chatDTO.setMessages(chat.getMessages()
                    .stream()
                    .map(message ->
                            new MessageDTO(message.getContent(), message.getSenderId(), message.getReceiverId()))
                    .collect(Collectors.toList()));
            messages.add(chatDTO);
        });

        return messages;
    }

    public MessageDTO sendMessage(MessageDTO messageDTO) {
        validate(messageDTO);
        messageDTO.setSenderId((long) 2);

        NewMessageResponse newMessageResponse = messageClient.sendMessage(messageDTO);

        messageDTO.setSenderId(newMessageResponse.getMessage().getSenderId());
        messageDTO.setReceiverId(newMessageResponse.getMessage().getReceiverId());
        messageDTO.setContent(newMessageResponse.getMessage().getContent());

        return messageDTO;
    }

    private void validate(MessageDTO messageDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (messageDTO.getContent() == null || messageDTO.getContent().trim().equals("") ||
                !pattern.matcher(messageDTO.getContent().trim()).matches() || messageDTO.getReceiverId() == null) {
            throw new BadRequestException("Message is not well formed!");
        }

        messageDTO.setContent(Encode.forHtml(messageDTO.getContent()));

    }

}
