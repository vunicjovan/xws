package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.Comment;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import com.uns.ftn.rentingservice.dto.CommentDTO;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.CommentRepository;
import com.uns.ftn.rentingservice.repository.RentingRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private RentingRequestRepository rentingRequestRepository;
    private AdvertisementRepository advertisementRepository;
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(RentingRequestRepository rentingRequestRepository,
                          AdvertisementRepository advertisementRepository,
                          CommentRepository commentRepository) {
        this.rentingRequestRepository = rentingRequestRepository;
        this.advertisementRepository = advertisementRepository;
        this.commentRepository = commentRepository;
    }

    public CommentDTO checkCommentPermission(Long requestId, Long advertisementId) {
        LOGGER.debug("Feign client requested check for commenting permission for request[requestId={}, " +
                        "advertisementId={}]",
                requestId, advertisementId);
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElse(null);
        RentingRequest request = rentingRequestRepository.findById(requestId)
                .orElseThrow(() -> {
                    LOGGER.warn("Database query: request[id={}] doesn't exist", requestId);
                   return new NotFoundException("Request doesn't exist.");
                });
        CommentDTO commentDTO = new CommentDTO();

        if (advertisement == null) {
            LOGGER.warn("Database query: advertisement[id={}] doesn't exist", advertisementId);
            throw new NotFoundException("Advertisement doesn't exist!");
        }

        Comment temp = findIfExist(advertisement, request);

        if (temp == null) {
            Comment comment = new Comment();
            comment.setAdvertisement(advertisement);
            comment.setRentingRequest(request);
            commentRepository.save(comment);
            LOGGER.info("Database entry: created new comment[id={}, requestId={}, advertisementId={}]",
                    comment.getId(), requestId, advertisementId);
            commentDTO.setRentingRequestId(request.getId());
            commentDTO.setUserId(request.getSenderId());
            LOGGER.debug("Feign client response for request on check for commenting permission request[requestId={}," +
                    "advertisementId={}]", requestId, advertisementId);
            return commentDTO;
        }

        LOGGER.debug("Feign client response for request on check for commenting permission request[requestId={}," +
                "advertisementId={}]", requestId, advertisementId);
        return commentDTO;
    }

    public Comment findIfExist(Advertisement advertisement, RentingRequest rentingRequest) {
        return commentRepository.findByAdvertisementAndRentingRequest(advertisement, rentingRequest);
    }
}
