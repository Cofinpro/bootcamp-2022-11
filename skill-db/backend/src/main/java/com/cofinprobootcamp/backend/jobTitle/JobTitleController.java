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

    @GetMapping("")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'JOB_TITLES_GET_ALL')")
    public List<String> getJobTitles() {
        return jobTitleService.getAllJobTitles();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'JOB_TITLES_POST_NEW')")
    public void addJobTitle(@RequestBody String jobTitle) {
        jobTitleService.addNewJobTitle(jobTitle);
    }
}
