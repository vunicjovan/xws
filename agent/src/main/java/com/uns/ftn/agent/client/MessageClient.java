package com.uns.ftn.agent.client;

import com.uns.ftn.agent.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.*;

public class MessageClient extends WebServiceGatewaySupport {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageResponse getMessages(Long id) {
        logger.info("Getting messages for agent with id {} form microservices", id);
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserId(id);

        MessageResponse messageResponse = (MessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive(messageRequest);

        return messageResponse;
    }

    public NewMessageResponse sendMessage(MessageDTO messageDTO) {
        NewMessageRequest newMessageRequest = new NewMessageRequest();
        logger.info("Sending message by agent with id {} via soap", messageDTO.getSenderId());

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
