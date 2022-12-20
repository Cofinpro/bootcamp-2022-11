package com.cofinprobootcamp.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class TheExceptionHandler {
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleProfileNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "The Profile was not found!",
                wr.getDescription(false)
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JobTitleNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleJobTitleNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "The jobtitle was not found. This error should never occur if front and backend are synced",
                wr.getDescription(false)
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExpertiseNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleExpertiseNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "The Expertise was not found.",
                wr.getDescription(false)
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}

