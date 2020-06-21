package com.uns.ftn.agent.client;

import com.uns.ftn.agent.dto.MessageDTO;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.*;

public class MessageClient extends WebServiceGatewaySupport {

    public MessageResponse getMessages(Long id) {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserId(id);

        MessageResponse messageResponse = (MessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive(messageRequest);

        return messageResponse;
    }

    public NewMessageResponse sendMessage(MessageDTO messageDTO) {
        NewMessageRequest newMessageRequest = new NewMessageRequest();

        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setReceiverId(messageDTO.getReceiverId());
        message.setSenderId(messageDTO.getSenderId());

        newMessageRequest.setMessage(message);

        NewMessageResponse newMessageResponse = (NewMessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive(newMessageRequest);

        return newMessageResponse;
    }

}
