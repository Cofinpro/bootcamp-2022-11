package com.cofinprobootcamp.backend.jobTitle;

import com.cofinprobootcamp.backend.exceptions.JobTitleAlreadyExistsException;
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
     * Add a new job title to the database, if it does not exist yet.
     * @param title A {@code String} representing the new job title's name
     * @throws JobTitleAlreadyExistsException An exception that is thrown when a job title with
     *         the given name already exists
     */
    public void addNewJobTitle(String title) throws JobTitleAlreadyExistsException {
        if (jobTitleRepository.findJobTitleByName(title).isPresent()) {
            throw new JobTitleAlreadyExistsException("Der anzulegende Jobtitel existiert bereits");
        } else {
            jobTitleRepository.saveAndFlush(new JobTitle(title));
        }
    }

    /**
     * Gets a list of all available job titles from the database.
     * @return A {@code List} of {@code String} representations of each job title
     */
    public List<String> getAllJobTitles() {
        return jobTitleRepository
                .findAll()
                .stream()
                .map(JobTitle::getName)
                .toList();
    }
}
