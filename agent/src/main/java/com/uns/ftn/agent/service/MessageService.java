package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.MessageClient;
import com.uns.ftn.agent.dto.ChatDTO;
import com.uns.ftn.agent.dto.MessageDTO;
import com.uns.ftn.agent.exceptions.BadRequestException;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageClient messageClient;

    @Autowired
    public MessageService(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    public List<ChatDTO> getChat() {
        logger.info("Getting messages");
        List<ChatDTO> messages = new ArrayList<>();

        MessageResponse messageResponse = messageClient.getMessages((long) 2);
        logger.info("Messages have been retrieved from microservices");

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
        logger.info("Validating message for sending");
        validate(messageDTO);
        logger.info("Message has been validated");
        
        messageDTO.setSenderId((long) 2);
        logger.info("Sending message from agent with id {} to user with id {}", 2, messageDTO.getSenderId());

        NewMessageResponse newMessageResponse = messageClient.sendMessage(messageDTO);
        logger.info("Message has been successfully sent to user");

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
            logger.error("Message has empty or corrupted data");
            throw new BadRequestException("Message is not well formed!");
        }

        messageDTO.setContent(Encode.forHtml(messageDTO.getContent()));

    }

}
