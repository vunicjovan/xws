package com.uns.ftn.agentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uns.ftn.agentservice.client.AccountClient;
import com.uns.ftn.agentservice.client.RentRequestClient;
import com.uns.ftn.agentservice.components.QueueProducer;
import com.uns.ftn.agentservice.domain.Advertisement;
import com.uns.ftn.agentservice.domain.Comment;
import com.uns.ftn.agentservice.dto.*;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.CommentRepository;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private AccountClient accountClient;
    private AdvertisementService advertisementService;
    private RentRequestClient rentRequestClient;
    private QueueProducer queueProducer;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          AccountClient accountClient,
                          AdvertisementService advertisementService,
                          RentRequestClient rentRequestClient,
                          QueueProducer queueProducer) {
        this.commentRepository = commentRepository;
        this.accountClient = accountClient;
        this.advertisementService = advertisementService;
        this.rentRequestClient = rentRequestClient;
        this.queueProducer = queueProducer;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void remove(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment findOne(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Requested comment doesn't exist."));
    }

    public List<CommDTO> getCommentsByAdvertisementOwner(Long id) {
        List<Advertisement> advertisements = advertisementService.findByOwner(id);
        List<CommDTO> commDTOList = new ArrayList<>();
        advertisements.forEach(advertisement -> {
            advertisement.getComments().forEach(comment -> {
                commDTOList.add(new CommDTO(comment.getId(), comment.getTitle(), comment.getContent(),
                        comment.getUserId(), comment.getAdvertisement().getId(), comment.getRentingRequestId()));
            });
        });
        return commDTOList;
    }

    public List<Comment> getAllByAllowed(Boolean allowed) {
        return commentRepository.getAllByAllowed(allowed);
    }

    public ResponseEntity<?> getUnapprovedComments() {
        List<CommentResponseDTO> commentResponseDTOS = getAllByAllowed(false).stream().map(comment -> {
            UserResponseDTO userDto = accountClient.getUser(comment.getUserId());
            return new CommentResponseDTO(comment.getId(), comment.getTitle(), comment.getContent(),
                    comment.getAllowed(), userDto.getFirstName() + " " + userDto.getLastName(),
                    comment.getAdvertisement().getId());
        })
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentResponseDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> approveComment(Long adId, Long id) {
        Comment comment = findOne(id);

        if (comment.getAdvertisement().getId() != adId) {
            throw new BadRequestException("Requested comment doesn't belong to searched advertisement.");
        }

        comment.setAllowed(true);
        comment = save(comment);

        try {
            queueProducer.produceComment(new CommDTO(comment));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new CommentResponseDTO(comment), HttpStatus.OK);
    }

    public ResponseEntity<?> rejectComment(Long adId, Long id) {
        Comment comment = findOne(id);

        if (comment.getAllowed()) {
            throw new BadRequestException("Requested comment is posted and cannot be deleted.");
        }

        if (comment.getAdvertisement().getId() != adId) {
            throw new BadRequestException("Requested comment doesn't belong to searched advertisement.");
        }

        remove(id);

        return new ResponseEntity<>("Comment successfully removed.", HttpStatus.OK);
    }

    public PublisherCommentDTO publisherPostComment(PublisherCommentDTO commentDTO) {
        Advertisement advertisement = advertisementService.findById(commentDTO.getAdvertisementId());

        if (advertisement == null) {
            throw new NotFoundException("Advertisement doesn't exit!");
        }

        Comment comment = new Comment();
        comment.setTitle(commentDTO.getTitle());
        comment.setContent(commentDTO.getContent());
        comment.setUserId(commentDTO.getUserId());
        comment.setAdvertisement(advertisement);
        commentRepository.save(comment);

        return new PublisherCommentDTO(comment);
    }

    public CommDTO postComment(CommDTO commentDTO) {
        commentDTO = validateAndSanitize(commentDTO);
        CommDTO checkCommentDTO;
        Advertisement advertisement = advertisementService.findById(commentDTO.getAdvertisementId());

        if (advertisement == null) {
            throw new NotFoundException("Advertisement doesn't exist!");
        }

        try {
            checkCommentDTO = rentRequestClient.checkCommentPostingPermission(commentDTO.getRentingRequestId(),
                    commentDTO.getAdvertisementId());
        } catch (NotFoundException e) {
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
        comment.setRentingRequestId(commentDTO.getRentingRequestId());
        commentRepository.save(comment);
        return new CommDTO(comment);
    }

    public CommentClientResponseDTO getClientComment(Long id) {
        Comment comment = findOne(id);

        if(comment.getAllowed()) {
            return new CommentClientResponseDTO(comment.getId(), comment.getTitle(), comment.getContent());
        }

        return null;
    }

    private CommDTO validateAndSanitize(CommDTO commDTO) {
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";

        Pattern pattern = Pattern.compile(regex);

        if (commDTO.getTitle() == null || commDTO.getTitle().isEmpty()
                || !pattern.matcher(commDTO.getTitle().trim()).matches()
                || commDTO.getContent() == null || commDTO.getContent().isEmpty()
                || !pattern.matcher(commDTO.getContent().trim()).matches()
                || commDTO.getUserId() == null || commDTO.getAdvertisementId() == null) {
            throw new BadRequestException("Given data is not well formed!");
        } else {
            commDTO.setTitle(Encode.forHtml(commDTO.getTitle()));
            commDTO.setContent(Encode.forHtml(commDTO.getContent()));
            return commDTO;
        }
    }

}
