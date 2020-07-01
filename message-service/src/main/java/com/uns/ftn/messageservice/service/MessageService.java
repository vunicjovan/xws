package com.uns.ftn.messageservice.service;

import com.uns.ftn.messageservice.AccountClient;
import com.uns.ftn.messageservice.domain.Message;
import com.uns.ftn.messageservice.dto.ChatDTO;
import com.uns.ftn.messageservice.dto.MessageDTO;
import com.uns.ftn.messageservice.exception.BadRequestException;
import com.uns.ftn.messageservice.repository.MessageRepository;
import javassist.NotFoundException;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountClient accountClient;

    public List<ChatDTO> getChat(Long id) {
        List<Message> messages;
        List<ChatDTO> chat = new ArrayList<>();

        messages = messageRepository.findAllByReceiverId(id);
        messages.forEach(message -> {
            addMessageToChat(chat, message, message.getSenderId(), false);
        });

        messages = messageRepository.findAllBySenderId(id);

        messages.forEach(message -> {
            addMessageToChat(chat, message, message.getReceiverId(), true);
        });

        chat.forEach(chatDTO -> chatDTO.getMessages()
                .sort((msg, msgAnother) -> msg.getTimestamp().compareTo(msgAnother.getTimestamp())));


        return chat;
    }

    private void addMessageToChat(List<ChatDTO> chat, Message message, Long charRoomId, Boolean secondLoop) {
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
            if (secondLoop) {
                chatDTO = new ChatDTO(charRoomId, accountClient.getOwnerName(charRoomId));
            } else {
                chatDTO = new ChatDTO(charRoomId, message.getSenderUsername());
            }
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

    public MessageDTO saveAgentMessage(MessageDTO messageDTO) throws NotFoundException {
        try {
            messageDTO.setUsername(accountClient.getOwnerName(messageDTO.getSenderId()));
        } catch (Exception e) {
            throw new NotFoundException("Agent with given id does not exist!");
        }

        return saveMessage(messageDTO);
    }

    public Boolean createChat(Long senderId, Long receiverId) {
        List<Message> messages = messageRepository.findAllBySenderIdAndReceiverId(senderId, receiverId);

        if (!messages.isEmpty()) {
            return false;
        } else {
            Message message = new Message();
            message.setSenderId(senderId);
            message.setReceiverId(receiverId);
            message.setTimestamp(new Date());
            message.setContent("Chat available!");
            message.setSenderUsername(accountClient.getOwnerName(senderId));
            messageRepository.save(message);
            return true;
        }
    }

    public String deleteMessage(Long id) {
        try {
            messageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequestException("Message does not exist!");
        }

        return "Message successfully deleted";
    }

}
