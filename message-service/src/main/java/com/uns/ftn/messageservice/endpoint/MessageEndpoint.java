package com.uns.ftn.messageservice.endpoint;

import com.uns.ftn.messageservice.dto.ChatDTO;
import com.uns.ftn.messageservice.dto.MessageDTO;
import com.uns.ftn.messageservice.service.MessageService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.message.*;

import java.util.List;

@Endpoint
public class MessageEndpoint {
    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/message";

    @Autowired
    private MessageService messageService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "messageRequest")
    @ResponsePayload
    public MessageResponse getMessages(@RequestPayload MessageRequest messageRequest) {
        MessageResponse messageResponse = new MessageResponse();
        List<ChatDTO> chatDTOList = messageService.getChat(messageRequest.getUserId());

        chatDTOList.forEach(chatDTO -> {
            Chat chat = new Chat();
            chat.setSenderId(chatDTO.getSenderId());
            chat.setSenderUsername(chatDTO.getSenderUsername());

            chatDTO.getMessages().forEach(messageDTO -> {
                Message message = new Message();
                message.setId(messageDTO.getId());
                message.setContent(messageDTO.getContent());
                message.setReceiverId(messageDTO.getReceiverId());
                message.setSenderId(messageDTO.getSenderId());
                chat.getMessages().add(message);
            });

            messageResponse.getChat().add(chat);
        });

        return messageResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "newMessageRequest")
    @ResponsePayload
    public NewMessageResponse sendMessage(@RequestPayload NewMessageRequest newMessageRequest) throws NotFoundException {
        NewMessageResponse newMessageResponse = new NewMessageResponse();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(newMessageRequest.getMessage().getContent());
        messageDTO.setSenderId(newMessageRequest.getMessage().getSenderId());
        messageDTO.setReceiverId(newMessageRequest.getMessage().getReceiverId());

        messageDTO = messageService.saveAgentMessage(messageDTO);

        Message message = new Message();
        message.setSenderId(messageDTO.getSenderId());
        message.setReceiverId(messageDTO.getReceiverId());
        message.setContent(messageDTO.getContent());

        newMessageResponse.setMessage(message);

        return newMessageResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteMessageRequest")
    @ResponsePayload
    public DeleteMessageResponse deleteMessage(@RequestPayload DeleteMessageRequest deleteMessageRequest) {
        DeleteMessageResponse deleteMessageResponse = new DeleteMessageResponse();
        String message = messageService.deleteMessage(deleteMessageRequest.getId());
        deleteMessageResponse.setMessage(message);
        return deleteMessageResponse;
    }
}
