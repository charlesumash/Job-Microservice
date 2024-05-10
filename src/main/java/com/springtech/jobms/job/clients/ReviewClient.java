package com.springtech.jobms.job.clients;

import com.springtech.jobms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE")
public interface ReviewClient {
//    @GetMapping("/reviews")
//    public List<Review> getReviews(@RequestParam("companyId") Long companyId);

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> findReviewsByCompanyId(@RequestParam Long companyId);
}
