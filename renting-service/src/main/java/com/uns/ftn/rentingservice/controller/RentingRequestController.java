package com.uns.ftn.rentingservice.controller;

import com.uns.ftn.rentingservice.dto.RentingRequestDTO;
import com.uns.ftn.rentingservice.dto.RequestStatusDTO;
import com.uns.ftn.rentingservice.service.CommentService;
import com.uns.ftn.rentingservice.service.RentingRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RentingRequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentingRequestController.class);

    private RentingRequestService requestService;
    private CommentService commentService;

    @Autowired
    public RentingRequestController(RentingRequestService requestService, CommentService commentService) {
        this.requestService = requestService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/{agentId}/finished")
    public ResponseEntity<?> getAllFinished(@PathVariable Long agentId) {
        LOGGER.info("User had requested finished for agentId={}", agentId);
        return new ResponseEntity<>(this.requestService.getAllFinished(agentId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long id) {
        LOGGER.info("User had requested requests[userId={}]", id);
        return new ResponseEntity<>(this.requestService.getRequestForUser(id), HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<?> getUserHistory(@PathVariable Long id) {
        LOGGER.info("User had requested renting history for userId={}", id);
        return new ResponseEntity<>(requestService.getHistoryUser(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_RENT_REQUEST')")
    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> createRequest(@RequestBody RentingRequestDTO rdto) {
        LOGGER.info("User had requested adding new rentingRequest[senderId={}]", rdto.getSenderId());
        return this.requestService.createRequest(rdto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestBody RequestStatusDTO request) {
        LOGGER.info("User had requested update of rentingRequest[id={}, status={}]", id, request.getStatus());
        return requestService.updateRequestStatus(id, request);
    }

    // agent-service: post comment
    @GetMapping("/comment/{requestId}/{advertisementId}")
    public ResponseEntity<?> checkCommentPostPermission(@PathVariable("requestId") Long requestId,
                                                      @PathVariable("advertisementId") Long advertisementId) {
        LOGGER.info("User had requested check for comment posting comment[requestId={}, advertisementId={}]", requestId,
                advertisementId);
        return new ResponseEntity(commentService.checkCommentPermission(requestId, advertisementId), HttpStatus.OK);
    }

}
