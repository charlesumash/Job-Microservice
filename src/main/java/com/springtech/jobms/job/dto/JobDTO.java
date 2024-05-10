package com.springtech.jobms.job.dto;

import com.springtech.jobms.job.external.Company;
import com.springtech.jobms.job.external.Review;
import lombok.Data;

import java.util.List;

@Data
public class JobDTO {
    private Long jobId;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;

    // company object
    private Company company;

    // review object
    private List<Review> reviews;
}
