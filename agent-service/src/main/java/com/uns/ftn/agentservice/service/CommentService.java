package com.uns.ftn.agentservice.service;

import com.uns.ftn.agentservice.client.AccountClient;
import com.uns.ftn.agentservice.domain.Comment;
import com.uns.ftn.agentservice.dto.AdvertisementDTO;
import com.uns.ftn.agentservice.dto.CommentResponseDTO;
import com.uns.ftn.agentservice.dto.UserResponseDTO;
import com.uns.ftn.agentservice.exceptions.BadRequestException;
import com.uns.ftn.agentservice.exceptions.NotFoundException;
import com.uns.ftn.agentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private AccountClient accountClient;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          AccountClient accountClient) {
        this.commentRepository = commentRepository;
        this.accountClient = accountClient;
    }

    public Comment save(Comment comment) { return commentRepository.save(comment); }

    public void remove(Long id) { commentRepository.deleteById(id);}

    public Comment findOne(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Requested comment doesn't exist.")); }

    public List<Comment> getAllByAllowed(Boolean allowed) { return commentRepository.getAllByAllowed(allowed); }

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

        if(comment.getAdvertisement().getId() != adId) {
            throw new BadRequestException("Requested comment doesn't belong to searched advertisement.");
        }

        comment.setAllowed(true);
        comment = save(comment);

        return new ResponseEntity<>(new CommentResponseDTO(comment), HttpStatus.OK);
    }

    public ResponseEntity<?> rejectComment(Long adId, Long id) {
        Comment comment = findOne(id);

        if(comment.getAdvertisement().getId() != adId) {
            throw new BadRequestException("Requested comment doesn't belong to searched advertisement.");
        }

        remove(id);

        return new ResponseEntity<>("Comment successfully removed.", HttpStatus.OK);
    }
}
