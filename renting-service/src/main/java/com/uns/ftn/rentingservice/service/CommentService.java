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

    @Autowired
    private RentingRequestRepository rentingRequestRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private CommentRepository commentRepository;

    public CommentDTO checkCommentPermission(Long userId, Long advertisementId) {
        List<RentingRequest> rentingRequestList = rentingRequestRepository.findAllBySenderId(userId);
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElse(null);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setUserId(userId);

        if (advertisement == null) {
            throw new NotFoundException("Advertisement doesn't exist!");
        }


        for (RentingRequest rentingRequest : rentingRequestList) {
            if (rentingRequest.getAdvertisements().contains(advertisement)
                    && rentingRequest.getRentingReports()
                    .stream()
                    .filter(rentingReport -> rentingReport.getAdvertisement().getId() == advertisementId)
                    .findFirst()
                    .orElse(null) != null
                    && rentingRequest.getComments()
                    .stream()
                    .filter(comment -> comment.getAdvertisement().getId() == advertisementId)
                    .findFirst()
                    .orElse(null) == null) {
                Comment comment = new Comment();
                comment.setAdvertisement(advertisement);
                comment.setRentingRequest(rentingRequest);
                commentRepository.save(comment);
                commentDTO.setRentingRequestId(rentingRequest.getId());
                return commentDTO;
            }
        }

        return commentDTO;
    }

    public Comment findIfExist(Advertisement advertisement, RentingRequest rentingRequest) {
        return commentRepository.findByAdvertisementAndRentingRequest(advertisement, rentingRequest);
    }
}
