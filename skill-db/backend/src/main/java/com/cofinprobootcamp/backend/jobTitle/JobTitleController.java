package com.cofinprobootcamp.backend.jobTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-titles")
public class JobTitleController {
    private final JobTitleService jobTitleService;

    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    /**
     * Endpoint to find all available job titles from the database.
     *
     * @return A {@link List} of {@link String} representations of each {@link JobTitle}.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'JOB_TITLES_GET_ALL')")
    public List<String> getJobTitles() {
        return jobTitleService.getAllJobTitles();
    }
}
