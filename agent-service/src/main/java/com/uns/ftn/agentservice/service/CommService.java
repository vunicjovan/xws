package com.uns.ftn.agentservice.service;

import com.uns.ftn.agentservice.client.RentRequestClient;
import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.Comment;
import com.uns.ftn.agentservice.dto.CommDTO;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.CommRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommService {
    @Autowired
    private CommRepository commRepository;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private RentRequestClient rentRequestClient;

    public CommDTO postComment(CommDTO commentDTO) {
        CommDTO checkCommentDTO;
        Advertisement advertisement = advertisementService.findById(commentDTO.getAdvertisementId());

        try {
            checkCommentDTO = rentRequestClient.checkCommentPostingPermission(commentDTO.getUserId(),
                    commentDTO.getAdvertisementId());
        } catch (NotFoundException e) {
            throw new NotFoundException("Advertisement doesn't exist!");
        }

        if (advertisement == null) {
            throw new NotFoundException("Advertisement doesn't exist!");
        }

        if (checkCommentDTO.getRentingRequestId() == null) {
            throw new BadRequestException("You have already posted comment for your rent request");
        }


        Comment comment = new Comment();
        comment.setTitle(commentDTO.getTitle());
        comment.setContent(commentDTO.getContent());
        comment.setUserId(commentDTO.getUserId());
        comment.setAdvertisement(advertisement);
        commRepository.save(comment);
        return new CommDTO(comment);
    }

}
