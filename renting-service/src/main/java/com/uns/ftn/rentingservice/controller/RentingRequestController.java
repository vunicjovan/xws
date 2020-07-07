package com.uns.ftn.rentingservice.controller;

import com.uns.ftn.rentingservice.domain.RequestStatus;
import com.uns.ftn.rentingservice.dto.RentingRequestDTO;
import com.uns.ftn.rentingservice.dto.RequestStatusDTO;
import com.uns.ftn.rentingservice.service.CommentService;
import com.uns.ftn.rentingservice.service.RentingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RentingRequestController {

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
        return new ResponseEntity<>(this.requestService.getAllFinished(agentId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long id) {
        return new ResponseEntity<>(this.requestService.getRequestForUser(id), HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<?> getUserHistory(@PathVariable Long id) {
        return new ResponseEntity<>(requestService.getHistoryUser(id), HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<?> createRequest(@RequestBody RentingRequestDTO rdto) {
        return this.requestService.createRequest(rdto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestBody RequestStatusDTO request) {
        return requestService.updateRequestStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelRequest(@PathVariable("id") Long id) {
        return requestService.cancelRequest(id);
    }

    @GetMapping("/comment/{requestId}/{advertisementId}")
    public ResponseEntity<?> checkCommentPostPermission(@PathVariable("requestId") Long requestId,
                                                        @PathVariable("advertisementId") Long advertisementId) {
        return new ResponseEntity(commentService.checkCommentPermission(requestId, advertisementId), HttpStatus.OK);
    }

}
