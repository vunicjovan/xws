package com.uns.ftn.agent.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.GetFinishedRequest;
import rs.ac.uns.ftn.catalog.GetFinishedResponse;

public class RentingRequestClient extends WebServiceGatewaySupport {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public GetFinishedResponse getFinishedRequests(Long id) {
        logger.info("Getting finished requests for agent with id {} via soap", id);
        GetFinishedRequest request = new GetFinishedRequest();
        request.setId(id);

        GetFinishedResponse response = (GetFinishedResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

}
