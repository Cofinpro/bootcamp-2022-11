package com.cofinprobootcamp.backend.jobTitle;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Order(1)
enum JobTitles {
    CONSULTANT("Consultant"),
    EXPERT_CONSULTANT("Expert Consultant"),
    SENIOR_CONSULTANT("Senior Consultant"),
    MANAGER("Manager"),
    ARCHITECT("Architect"),
    SENIOR_MANAGER("Senior Manager"),
    SENIOR_ARCHITECT("Senior Architect"),
    DIRECTOR ("Director"),
    PARTNER("Partner"),
    EXEC("Vorstand");

    private final String name;

    JobTitles(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

@Service
public class JobTitleSetup {
    private final JobTitleRepository jobTitleRepository;

    public JobTitleSetup(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    @PostConstruct
    public void init() {
        createStandardJobTitles();
    }

    private void createStandardJobTitles() {
        for (var constant : JobTitles.values()) {
            jobTitleRepository.save(fromString(constant.toString()));
        }
        jobTitleRepository.flush();
    }

    private JobTitle fromString(String name) {
        return new JobTitle(name);
    }
}
