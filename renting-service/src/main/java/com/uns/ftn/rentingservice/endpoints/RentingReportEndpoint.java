package com.uns.ftn.rentingservice.endpoints;

import com.uns.ftn.rentingservice.dto.RentingReportDTO;
import com.uns.ftn.rentingservice.service.RentingReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.renting.CompileReportRequest;
import rs.ac.uns.ftn.renting.CompileReportResponse;

@Endpoint
public class RentingReportEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentingReportEndpoint.class);

    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/renting";

    private RentingReportService reportService;

    @Autowired
    public RentingReportEndpoint(RentingReportService reportService) {
        this.reportService = reportService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "compileReportRequest")
    @ResponsePayload
    public CompileReportResponse compileRentingReport(@RequestPayload CompileReportRequest compileReportRequest) {
        LOGGER.info("User had entered compileReportRequest endpoint with SOAP report[requestId={}, " +
                "advertisementId={}", compileReportRequest.getRequestID(), compileReportRequest.getAdvertisementID());
        RentingReportDTO rdto = new RentingReportDTO();
        rdto.setKilometersTraveled(compileReportRequest.getKilometersTraveled());
        rdto.setContent(compileReportRequest.getContent());
        rdto.setRequestID(compileReportRequest.getRequestID());
        rdto.setAdvertisementID(compileReportRequest.getAdvertisementID());

        RentingReportDTO respDTO = reportService.createReport(rdto);

        CompileReportResponse response = new CompileReportResponse();

        response.setKilometersTraveled(respDTO.getKilometersTraveled());
        response.setContent(respDTO.getContent());
        response.setRequestID(respDTO.getRequestID());
        response.setAdvertisementID(respDTO.getAdvertisementID());

        return response;
    }

}
