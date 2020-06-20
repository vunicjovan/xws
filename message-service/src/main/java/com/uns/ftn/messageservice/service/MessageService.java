package com.uns.ftn.messageservice.service;

import com.uns.ftn.messageservice.domain.Message;
import com.uns.ftn.messageservice.dto.ChatDTO;
import com.uns.ftn.messageservice.dto.MessageDTO;
import com.uns.ftn.messageservice.exception.BadRequestException;
import com.uns.ftn.messageservice.repository.MessageRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<ChatDTO> getChat(Long id) {
        List<Message> messages = messageRepository.findAllByReceiverId(id);
        List<ChatDTO> chat = new ArrayList<>();

        messages.forEach(message -> {
            addMessageToChat(chat, message, message.getSenderId());
        });

        messages = messageRepository.findAllBySenderId(id);

        messages.forEach(message -> {
            addMessageToChat(chat, message, message.getReceiverId());
        });

        chat.forEach(chatDTO -> chatDTO.getMessages()
                .sort((msg, msgAnother) -> msg.getTimestamp().compareTo(msgAnother.getTimestamp())));


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

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        Message message = new Message();

        validate(messageDTO);

        message.setContent(messageDTO.getContent());
        message.setTimestamp(new Date());
        message.setSenderId(messageDTO.getSenderId());
        message.setReceiverId(messageDTO.getReceiverId());
        message.setSenderUsername(messageDTO.getUsername());

        message = messageRepository.save(message);

        return new MessageDTO(message);
    }

    private void validate(MessageDTO messageDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        System.out.println(messageDTO);

        if (messageDTO.getContent() == null || messageDTO.getContent().trim().equals("") ||
                !pattern.matcher(messageDTO.getContent().trim()).matches() ||  messageDTO.getSenderId() == null
                || messageDTO.getReceiverId() == null || messageDTO.getUsername() == null
                || messageDTO.getUsername().trim().equals("") ||
                !pattern.matcher(messageDTO.getUsername().trim()).matches()) {
            throw new BadRequestException("Message is not well formed!");
        }

        messageDTO.setContent(Encode.forHtml(messageDTO.getContent()));
        messageDTO.setUsername(Encode.forHtml(messageDTO.getUsername()));

    }

}
