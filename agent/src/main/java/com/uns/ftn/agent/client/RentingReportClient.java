package com.uns.ftn.agent.client;

import com.uns.ftn.agent.dto.RentingReportDTO;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.CompileReportRequest;
import rs.ac.uns.ftn.catalog.CompileReportResponse;

public class RentingReportClient extends WebServiceGatewaySupport {

    public CompileReportResponse compileReport(RentingReportDTO rdto) {
        CompileReportRequest cpr = new CompileReportRequest();

        cpr.setKilometersTraveled(rdto.getKilometersTraveled());
        cpr.setContent(rdto.getContent());
        cpr.setRequestID(rdto.getRequestID());
        cpr.setAdvertisementID(rdto.getAdvertisementID());

        CompileReportResponse response = (CompileReportResponse) getWebServiceTemplate().marshalSendAndReceive(cpr);

        return response;
    }

}
