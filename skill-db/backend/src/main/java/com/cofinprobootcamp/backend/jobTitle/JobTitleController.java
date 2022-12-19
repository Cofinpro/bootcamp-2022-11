package com.cofinprobootcamp.backend.jobTitle;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobTitles/")
public class JobTitleController {
    private final JobTitleRepository jobTitleRepository;
    public JobTitleController(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }
    @GetMapping("")
    public List<String> getJobTitles() {
        return jobTitleRepository
                .findAll()
                .stream()
                .map(JobTitle::getName)
                .toList();
    }
    @PostMapping("")
    public void addJobTitle(@RequestBody JobTitle jobTitle) {
        jobTitleRepository.save(jobTitle);
    }
}
