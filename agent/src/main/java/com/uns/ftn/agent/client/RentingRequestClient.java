package com.uns.ftn.agent.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.GetFinishedRequest;
import rs.ac.uns.ftn.catalog.GetFinishedResponse;
import rs.ac.uns.ftn.catalog.PendingRentingRequestRequest;
import rs.ac.uns.ftn.catalog.PendingRentingRequestResponse;

public class RentingRequestClient extends WebServiceGatewaySupport {

    public GetFinishedResponse getFinishedRequests(Long id) {
        GetFinishedRequest request = new GetFinishedRequest();
        request.setId(id);

        GetFinishedResponse response = (GetFinishedResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

    public PendingRentingRequestResponse getPendingRentingRequest(Long id) {
        PendingRentingRequestRequest request = new PendingRentingRequestRequest();
        request.setId(id);

        PendingRentingRequestResponse response =
                (PendingRentingRequestResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        return response;
    }

}
