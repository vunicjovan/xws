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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.info("Retrieving comments from advertisements posted by user with id {}", id);
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
        logger.info("Retrieving unapproved comments");
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
        logger.info("Approving comment with id {} posted on advertisement with id {}", id, adId);
        Comment comment = findOne(id);

        if (comment.getAdvertisement().getId() != adId) {
            logger.error("Comment with id {} does not belong to advertisement with id {}", id, adId);
            throw new BadRequestException("Requested comment doesn't belong to searched advertisement.");
        }

        comment.setAllowed(true);
        comment = save(comment);
        logger.info("Comment with id {} approved", id);

        try {
            logger.debug("Sending comment changes through queue");
            queueProducer.produceComment(new CommDTO(comment));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new CommentResponseDTO(comment), HttpStatus.OK);
    }

    public ResponseEntity<?> rejectComment(Long adId, Long id) {
        logger.info("Rejecting comment with id {} posted on advertisement with id {}", id, adId);
        Comment comment = findOne(id);

        if (comment.getAllowed()) {
            logger.error("Comment with id {} is previously approved", id);
            throw new BadRequestException("Requested comment is posted and cannot be deleted.");
        }

        if (comment.getAdvertisement().getId() != adId) {
            logger.error("Comment with id {} does not belong to advertisement with id {}", id, adId);
            throw new BadRequestException("Requested comment doesn't belong to searched advertisement.");
        }

        remove(id);
        logger.info("Comment with id {} rejected", id);

        return new ResponseEntity<>("Comment successfully removed.", HttpStatus.OK);
    }

    public PublisherCommentDTO publisherPostComment(PublisherCommentDTO commentDTO) {
        logger.info("Publishing comment with title {} posted by user {}", commentDTO.getTitle(), commentDTO.getId());
        Advertisement advertisement = advertisementService.findById(commentDTO.getAdvertisementId());

        if (advertisement == null) {
            logger.error("Comment cannot be posted on not existing advertisement with id {}", commentDTO.getAdvertisementId());
            throw new NotFoundException("Advertisement doesn't exit!");
        }

        Comment comment = new Comment();
        comment.setTitle(commentDTO.getTitle());
        comment.setContent(commentDTO.getContent());
        comment.setUserId(commentDTO.getUserId());
        comment.setAdvertisement(advertisement);
        commentRepository.save(comment);
        logger.info("Comment with title published", commentDTO.getTitle());

        return new PublisherCommentDTO(comment);
    }

    public CommDTO postComment(CommDTO commentDTO) {
        logger.info("Posting comment with title {}", commentDTO.getTitle());
        commentDTO = validateAndSanitize(commentDTO);
        CommDTO checkCommentDTO;
        Advertisement advertisement = advertisementService.findById(commentDTO.getAdvertisementId());

        if (advertisement == null) {
            logger.error("Comment cannot be posted on not existing advertisement with id {}", commentDTO.getAdvertisementId());
            throw new NotFoundException("Advertisement doesn't exist!");
        }

        try {
            logger.info("Checking for permission to post comment in renting service");
            checkCommentDTO = rentRequestClient.checkCommentPostingPermission(commentDTO.getRentingRequestId(),
                    commentDTO.getAdvertisementId());
        } catch (NotFoundException e) {
            logger.error("Comment cannot be posted on not existing advertisement with id {}", commentDTO.getAdvertisementId());
            throw new NotFoundException("Advertisement doesn't exist!");
        }

        if (checkCommentDTO.getRentingRequestId() == null) {
            logger.error("Comment by user with id {} is already posted", commentDTO.getUserId());
            throw new BadRequestException("You have already posted comment for your rent request");
        }


        Comment comment = new Comment();
        comment.setTitle(commentDTO.getTitle());
        comment.setContent(commentDTO.getContent());
        comment.setUserId(commentDTO.getUserId());
        comment.setAdvertisement(advertisement);
        comment.setRentingRequestId(commentDTO.getRentingRequestId());
        commentRepository.save(comment);
        logger.info("Comment posted by user with id {} is successfully saved", commentDTO.getUserId());

        return new CommDTO(comment);
    }

    public CommentClientResponseDTO getClientComment(Long id) {
        logger.info("Retrieving comment with id {}", id);
        Comment comment = findOne(id);

        if(comment.getAllowed()) {
            return new CommentClientResponseDTO(comment.getId(), comment.getTitle(), comment.getContent());
        }

        logger.warn("Comment with given id {} does not exist or is not approved", id);
        return null;
    }

    private CommDTO validateAndSanitize(CommDTO commDTO) {
        logger.info("Validating comment data with");
        String regex = "^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\\s?]+)$";

        Pattern pattern = Pattern.compile(regex);

        if (commDTO.getTitle() == null || commDTO.getTitle().isEmpty()
                || !pattern.matcher(commDTO.getTitle().trim()).matches()
                || commDTO.getContent() == null || commDTO.getContent().isEmpty()
                || !pattern.matcher(commDTO.getContent().trim()).matches()
                || commDTO.getUserId() == null || commDTO.getAdvertisementId() == null) {
            logger.error("Comment data is invalid or corrupted");
            throw new BadRequestException("Given data is not well formed!");
        } else {
            logger.info("Comment data is well formed");
            commDTO.setTitle(Encode.forHtml(commDTO.getTitle()));
            commDTO.setContent(Encode.forHtml(commDTO.getContent()));
            return commDTO;
        }
    }

}
