package com.cofinprobootcamp.backend.jobTitle;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-titles")
public class JobTitleController {
    private final JobTitleRepository jobTitleRepository;
    public JobTitleController(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public List<String> getJobTitles() {
        return jobTitleRepository
                .findAll()
                .stream()
                .map(JobTitle::getName)
                .toList();
    }

    // Für addJobTitle sollte noch ein DTO angelegt werden oder die Entity darf nicht zwangsläufig eine ID
    // require → sonst muss das FrontEnd eine ID für neue Titel spezifizieren, das sollte das BE tun!
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public void addJobTitle(@RequestBody JobTitle jobTitle) {
        jobTitleRepository.save(jobTitle);
    }
}
