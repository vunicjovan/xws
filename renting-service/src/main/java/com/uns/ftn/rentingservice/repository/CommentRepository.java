package com.uns.ftn.rentingservice.repository;

import com.uns.ftn.rentingservice.domain.Advertisement;
import com.uns.ftn.rentingservice.domain.Comment;
import com.uns.ftn.rentingservice.domain.RentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByAdvertisementAndRentingRequest(Advertisement advertisement, RentingRequest rentingRequest);
}
