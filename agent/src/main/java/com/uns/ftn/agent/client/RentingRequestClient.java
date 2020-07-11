package com.uns.ftn.agent.client;

import com.uns.ftn.agent.dto.RequestStatusDTO;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.*;

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

    public UpdateRentingStatusResponse updateRentingStatus(RequestStatusDTO reqDTO) {
        UpdateRentingStatusRequest request = new UpdateRentingStatusRequest();
        request.setId(reqDTO.getId());
        request.setStatus(reqDTO.getStatus());

        try{
            UpdateRentingStatusResponse response =
                    (UpdateRentingStatusResponse) getWebServiceTemplate().marshalSendAndReceive(request);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
