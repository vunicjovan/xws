package com.uns.ftn.agent.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.GetFinishedRequest;
import rs.ac.uns.ftn.catalog.GetFinishedResponse;

public class RentingRequestClient extends WebServiceGatewaySupport {

    public GetFinishedResponse getFinishedRequests(Long id) {
        GetFinishedRequest request = new GetFinishedRequest();
        request.setId(id);

        GetFinishedResponse response = (GetFinishedResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

}
