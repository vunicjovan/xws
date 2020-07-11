package com.uns.ftn.rentingservice.endpoints;

import com.uns.ftn.rentingservice.domain.RequestStatus;
import com.uns.ftn.rentingservice.dto.GetRentingRequestDTO;
import com.uns.ftn.rentingservice.dto.ReqResponseDTO;
import com.uns.ftn.rentingservice.dto.RequestStatusDTO;
import com.uns.ftn.rentingservice.service.RentingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rs.ac.uns.ftn.renting.*;

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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "pendingRentingRequestRequest")
    @ResponsePayload
    public PendingRentingRequestResponse getPendingRentingRequest(@RequestPayload PendingRentingRequestRequest request) {
        PendingRentingRequestResponse response = new PendingRentingRequestResponse();
        Set<ReqResponseDTO> requests = requestService.getRequestForUser(request.getId());

        requests.forEach(req -> {
            PendingRequest pendingRequest = new PendingRequest();
            pendingRequest.setId(req.getId());
            pendingRequest.setSenderId(req.getSenderId());
            try {
                GregorianCalendar startCalendar = new GregorianCalendar();
                GregorianCalendar endCalendar = new GregorianCalendar();
                startCalendar.setTime(req.getStartDate());
                endCalendar.setTime(req.getEndDate());
                XMLGregorianCalendar xmlStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startCalendar);
                XMLGregorianCalendar xmlEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endCalendar);

                pendingRequest.setStartDate(xmlStartDate);
                pendingRequest.setEndDate(xmlEndDate);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            req.getAdvertisements().forEach(ad -> {
                pendingRequest.getAdvertisementIds().add(ad.getId());
            });

            response.getPendingRequests().add(pendingRequest);
        });

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateRentingStatusRequest")
    @ResponsePayload
    public UpdateRentingStatusResponse updateRentingStatus(@RequestPayload UpdateRentingStatusRequest request) {
        UpdateRentingStatusResponse response = new UpdateRentingStatusResponse();
        RequestStatusDTO requestStatusDTO = new RequestStatusDTO();
        requestStatusDTO.setId(request.getId());
        if(request.getStatus() == 2) {
            requestStatusDTO.setStatus(RequestStatus.paid);
        } else if(request.getStatus() == 3) {
            requestStatusDTO.setStatus(RequestStatus.canceled);
        }
        ResponseEntity<?> status = requestService.updateRequestStatus(request.getId(), requestStatusDTO);
        if(status.getStatusCode().equals(HttpStatus.OK)) {
            response.setId(request.getId());
        }

        return response;
    }
}
