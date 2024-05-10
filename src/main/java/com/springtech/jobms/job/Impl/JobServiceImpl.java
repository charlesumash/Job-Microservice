package com.springtech.jobms.job.Impl;


import com.springtech.jobms.job.Job;
import com.springtech.jobms.job.JobRepository;
import com.springtech.jobms.job.JobService;
import com.springtech.jobms.job.clients.CompanyClient;
import com.springtech.jobms.job.clients.ReviewClient;
import com.springtech.jobms.job.dto.JobDTO;
import com.springtech.jobms.job.external.Company;
import com.springtech.jobms.job.external.Review;
import com.springtech.jobms.job.mapper.JobMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;
    @Autowired
    private ReviewClient reviewClient;

//    public JobServiceImpl(CompanyClient companyClient, ReviewClient reviewClient) {
//        this.companyClient = companyClient;
//        this.reviewClient = reviewClient;
//    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::addCompanyToJob)
                .collect(Collectors.toList());
    }

    public JobDTO addCompanyToJob(Job job){
        Company company = companyClient.getCompanyById(job.getCompanyId()).getBody();

//        Company company = restTemplate.getForObject(
//                "http://company-service:8081/companies/" + job.getCompanyId(),
//                Company.class);

        List<Review> reviews = reviewClient.findReviewsByCompanyId(job.getCompanyId()).getBody();

//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
//        List<Review> reviews = reviewResponse.getBody();

        return JobMapper.mapToJobDTO(job, company, reviews);
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);

    }

    @Override
    public JobDTO getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job != null)
            return addCompanyToJob(job);
        else
            return null;
    }

    @Override
    public Boolean deleteJobById(Long jobId) {
        try {
            jobRepository.deleteById(jobId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean updateJobById(Long jobId, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
