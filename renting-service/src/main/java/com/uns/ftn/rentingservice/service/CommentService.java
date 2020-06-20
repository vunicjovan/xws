package com.uns.ftn.rentingservice.service;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.Comment;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import com.uns.ftn.rentingservice.dto.CommentDTO;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.repository.AdvertisementRepository;
import com.uns.ftn.rentingservice.repository.CommentRepository;
import com.uns.ftn.rentingservice.repository.RentingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

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
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElse(null);
        RentingRequest request = rentingRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request doesn't exist."));
        CommentDTO commentDTO = new CommentDTO();

        if (advertisement == null) {
            throw new NotFoundException("Advertisement doesn't exist!");
        }

        Comment temp = findIfExist(advertisement, request);

        if (temp == null) {
            Comment comment = new Comment();
            comment.setAdvertisement(advertisement);
            comment.setRentingRequest(request);
            commentRepository.save(comment);
            commentDTO.setRentingRequestId(request.getId());
            commentDTO.setUserId(request.getSenderId());
            return commentDTO;
        }

        return commentDTO;
    }

    public Comment findIfExist(Advertisement advertisement, RentingRequest rentingRequest) {
        return commentRepository.findByAdvertisementAndRentingRequest(advertisement, rentingRequest);
    }
}
