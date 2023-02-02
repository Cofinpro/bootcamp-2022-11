package com.cofinprobootcamp.backend.jobTitle;

import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTitleService {
    private final JobTitleRepository jobTitleRepository;

    public JobTitleService(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    public JobTitle findJobTitleIfExistsElseThrowException(String name) throws JobTitleNotFoundException {
        return jobTitleRepository.findJobTitleByName(name).orElseThrow(JobTitleNotFoundException::new);
    }

    /**
     * Gets a list of all available job titles from the database.
     * @return A {@link List} of {@link String} representations of each job title
     */
    public List<String> getAllJobTitles() {
        return jobTitleRepository
                .findAll()
                .stream()
                .map(JobTitle::getName)
                .toList();
    }
}
