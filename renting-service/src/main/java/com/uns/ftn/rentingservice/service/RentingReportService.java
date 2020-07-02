package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.client.CommentClient;
import com.uns.ftn.rentingservice.domain.*;
import com.uns.ftn.rentingservice.dto.RentingReportDTO;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.DebtRepository;
import com.uns.ftn.rentingservice.repository.RentingReportRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class RentingReportService {

    private RentingRequestService requestService;
    private AdvertisementRepository adRepo;
    private RentingReportRepository reportRepo;
    private CommentClient commentClient;
    private DebtRepository debtRepo;

    @Autowired
    public RentingReportService(
            RentingRequestService requestService,
            AdvertisementRepository adRepo,
            RentingReportRepository reportRepo,
            CommentClient commentClient,
            DebtRepository debtRepo) {
        this.requestService = requestService;
        this.adRepo = adRepo;
        this.reportRepo = reportRepo;
        this.commentClient = commentClient;
        this.debtRepo = debtRepo;
    }

    public RentingReport save(RentingReport report) {
        return reportRepo.save(report);
    }

    public RentingReportDTO createReport(RentingReportDTO rdto) {
        // check if sent report creation request contains all valid values
        checkValidityOfReport(rdto);

        RentingRequest request = requestService.findOne(rdto.getRequestID());
        Advertisement advertisement = findAdById(rdto.getAdvertisementID());
        RentingReport report = new RentingReport();

        report.setKilometersTraveled(rdto.getKilometersTraveled());
        report.setContent(rdto.getContent());
        report.setLimitBroken(false);

        double price = commentClient.generateDebtPrice(rdto.getAdvertisementID(),
                daysBetween(request.getStartDate(), request.getEndDate()), rdto.getKilometersTraveled());

        if (price > 0) {
            Debt debt = new Debt();
            debt.setAgentId(advertisement.getOwnerId());
            debt.setSimpleUserId(request.getSenderId());
            debt.setValue(price);

            debtRepo.save(debt);
        }

        report.setRentingRequest(request);
        report.setAdvertisement(advertisement);

        report = save(report);

        return new RentingReportDTO(report);
    }

    private int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void checkValidityOfReport(RentingReportDTO rdto) {
        RentingRequest request = requestService.findOne(rdto.getRequestID());
        Advertisement advertisement = findAdById(rdto.getAdvertisementID());
        Date currentTime = new Date();
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";
        Pattern pattern = Pattern.compile(regex);

        if (request.getStatus() != RequestStatus.paid) {
            throw new BadRequestException("This request is yet to be reserved.");
        } else if (!currentTime.after(request.getEndDate())) {
            throw new BadRequestException("This request is yet to be expired in terms of renting time.");
        }
        else if (rdto.getKilometersTraveled() < 0 || !pattern.matcher(rdto.getContent().trim()).matches()) {
            throw new BadRequestException("Either you entered negative value for traveled distance or you haven't wrote valid content.");
        }

        rdto.setContent(Encode.forHtml(rdto.getContent().trim()));
    }

    private Advertisement findAdById(Long id) {
        return adRepo.findById(id).orElseThrow(() -> new NotFoundException("Requested advertisement doesn't exist."));
    }

}
