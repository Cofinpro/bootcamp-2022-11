package com.cofinprobootcamp.backend.jobTitle;

import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JobTitleService {
    private final JobTitleRepository jobTitleRepository;

    public JobTitleService(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    public JobTitle findJobTitleIfExistsElseThrowException(String name) throws JobTitleNotFoundException {
        return jobTitleRepository.findJobTitleByName(name).orElseThrow(JobTitleNotFoundException::new);
    }
}
