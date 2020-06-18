package com.uns.ftn.rentingservice.endpoints;

import com.uns.ftn.rentingservice.dto.GetRentingRequestDTO;
import com.uns.ftn.rentingservice.service.RentingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.renting.FinishedRequest;
import rs.ac.uns.ftn.renting.GetFinishedRequest;
import rs.ac.uns.ftn.renting.GetFinishedResponse;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.Set;

@Endpoint
public class RentingRequestEndpoint {

    private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/renting";

    private RentingRequestService requestService;

    @Autowired
    public RentingRequestEndpoint(RentingRequestService requestService) {
        this.requestService = requestService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFinishedRequest")
    @ResponsePayload
    public GetFinishedResponse getFinishedRequests(@RequestPayload GetFinishedRequest getFinishedRequest) {
        GetFinishedResponse response = new GetFinishedResponse();
        Set<GetRentingRequestDTO> requests = requestService.getAllFinished(getFinishedRequest.getId());

        for (GetRentingRequestDTO gdto : requests) {
            GregorianCalendar startCalendar = new GregorianCalendar();
            GregorianCalendar endCalendar = new GregorianCalendar();
            startCalendar.setTime(gdto.getStartDate());
            endCalendar.setTime(gdto.getEndDate());

            try {
                XMLGregorianCalendar xmlStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startCalendar);
                XMLGregorianCalendar xmlEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endCalendar);

                FinishedRequest fr = new FinishedRequest();
                fr.setRequestId(gdto.getRequestId());
                fr.setStartDate(xmlStartDate);
                fr.setEndDate(xmlEndDate);
                fr.setAdvertisementId(gdto.getAdvertisementID());

                response.getFinishedRequests().add(fr);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

}
