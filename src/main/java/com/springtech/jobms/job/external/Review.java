package com.springtech.jobms.job.external;

import lombok.Data;

@Data
public class Review {
    private Long reviewId;
    private String title;
    private String description;
    private Double rating;
}
