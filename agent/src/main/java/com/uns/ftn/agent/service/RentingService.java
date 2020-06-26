package com.uns.ftn.agent.service;

import com.uns.ftn.agent.client.RentingReportClient;
import com.uns.ftn.agent.client.RentingRequestClient;
import com.uns.ftn.agent.domain.AdWrapper;
import com.uns.ftn.agent.domain.Advertisement;
import com.uns.ftn.agent.dto.GetRentingRequestDTO;
import com.uns.ftn.agent.dto.RentingReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.catalog.CompileReportResponse;
import rs.ac.uns.ftn.catalog.FinishedRequest;
import rs.ac.uns.ftn.catalog.GetFinishedResponse;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class RentingService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.info("Retrieving finished reports");
        Set<GetRentingRequestDTO> retval = new HashSet<>();
        GetFinishedResponse response = requestClient.getFinishedRequests((long) 2);
        logger.info("Finished request for agent with id {} have been retrieved from microservices", 2);

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

    public ResponseEntity<?> compileRentingReport(RentingReportDTO rdto) {
        logger.info("Send renting report for renting request with id {}", rdto.getRequestID());
        RentingReportDTO retval = new RentingReportDTO();

        CompileReportResponse response = reportClient.compileReport(rdto);
        logger.info("Request has been successfully saved in microservices database");
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
