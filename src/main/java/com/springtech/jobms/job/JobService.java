package com.springtech.jobms.job;

import com.springtech.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    public List<JobDTO> findAll();

    public void createJob(Job job);

    public JobDTO getJobById(Long jobId);

    public Boolean deleteJobById(Long jobId);

    public Boolean updateJobById(Long jobId, Job updatedJob);
}
