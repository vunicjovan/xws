package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.RentingReportClient;
import com.uns.ftn.agent.client.RentingRequestClient;
import com.uns.ftn.agent.domain.AdWrapper;
import com.uns.ftn.agent.domain.Advertisement;
import com.uns.ftn.agent.dto.GetRentingRequestDTO;
import com.uns.ftn.agent.dto.RentingReportDTO;
import com.uns.ftn.agent.dto.ReqResponseDTO;
import com.uns.ftn.agent.dto.RequestStatusDTO;
import com.uns.ftn.agent.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RentingService {

    private RentingRequestClient requestClient;
    private RentingReportClient reportClient;
    private final AdvertisementService advertisementService;

    @Autowired
    public RentingService(
            RentingRequestClient requestClient,
            RentingReportClient reportClient,
            AdvertisementService advertisementService) {
        this.requestClient = requestClient;
        this.reportClient = reportClient;
        this.advertisementService = advertisementService;
    }

    public ResponseEntity<?> getFinishedRequests() {
        Set<GetRentingRequestDTO> retval = new HashSet<>();
        GetFinishedResponse response = requestClient.getFinishedRequests((long) 2);

        for (FinishedRequest fr : response.getFinishedRequests()) {
            GetRentingRequestDTO gdto = new GetRentingRequestDTO();
            //AdWrapper adWrapper = advertisementService.findOneAdWrapper(fr.getAdvertisementId());
            Advertisement advertisement = advertisementService.findOne(fr.getAdvertisementId());

            gdto.setRequestId(fr.getRequestId());
            gdto.setStartDate(calendarToDate(fr.getStartDate()));
            gdto.setEndDate(calendarToDate(fr.getEndDate()));
            gdto.setAdvertisementId(fr.getAdvertisementId());
            gdto.setBrand(advertisement.getVehicle().getModel().getBrand().getName());
            gdto.setModel(advertisement.getVehicle().getModel().getName());
            gdto.setStringStartDate(calendarToString(fr.getStartDate()));
            gdto.setStringEndDate(calendarToString(fr.getEndDate()));

            retval.add(gdto);
        }

        return new ResponseEntity<>(retval, HttpStatus.OK);
    }

    public Set<ReqResponseDTO> getPendingRentingRequests() {
        PendingRentingRequestResponse response = requestClient.getPendingRentingRequest((long) 2);
        Set<ReqResponseDTO> ret = new HashSet<>();

        response.getPendingRequests().forEach(pendingRequest -> {
            ReqResponseDTO r = new ReqResponseDTO();
            r.setId(pendingRequest.getId());
            r.setSenderId(pendingRequest.getSenderId());
            r.setStartDate(calendarToString(pendingRequest.getStartDate()));
            r.setEndDate(calendarToString(pendingRequest.getEndDate()));
            r.setAdvertisements(pendingRequest.getAdvertisementIds().stream().map(ad -> {
                AdWrapper wrapper = advertisementService.findOneAdWrapper(ad);
                Advertisement advertisement = advertisementService.findOne(wrapper.getAdvertisementId());
                return advertisement.getVehicle().getModel().getBrand().getName() + " " + advertisement.getVehicle().getModel().getName();
            }).collect(Collectors.toSet()));
            ret.add(r);
        });

        return ret;
    }

    public RequestStatusDTO updateRequestStatus(RequestStatusDTO reqDto) {
        UpdateRentingStatusResponse response = requestClient.updateRentingStatus(reqDto);

        if(response == null) {
            throw new BadRequestException("Couldn't finish updating request.");
        }

        RequestStatusDTO requestStatusDTO = new RequestStatusDTO();

        requestStatusDTO.setId(response.getId());
        requestStatusDTO.setStatus(reqDto.getStatus());

        return requestStatusDTO;
    }

    public ResponseEntity<?> compileRentingReport(RentingReportDTO rdto) {
        RentingReportDTO retval = new RentingReportDTO();

        CompileReportResponse response = reportClient.compileReport(rdto);
        retval.setKilometersTraveled(response.getKilometersTraveled());
        retval.setContent(response.getContent());
        retval.setRequestID(response.getRequestID());
        retval.setAdvertisementID(response.getAdvertisementID());

        return new ResponseEntity<>(retval, HttpStatus.OK);
    }

    private Date calendarToDate(XMLGregorianCalendar calendar) {
        return calendar.toGregorianCalendar().getTime();
    }

    private String calendarToString(XMLGregorianCalendar calendar) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String formattedDate = formatter.format(calendar.toGregorianCalendar().getTime());

        return formattedDate;
    }

}
