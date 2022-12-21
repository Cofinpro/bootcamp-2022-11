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
        CustomErrorMessage body = new CustomErrorMessage("The Profile was not found!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JobTitleNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleJobTitleNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "The jobtitle was not found. This error should never occur if front and backend are synced",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpertiseNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleExpertiseNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage("The Expertise was not found.",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleUserNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "No user with the given username (i.e., email address) exists!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EndPointNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleEndPointNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "No rights can be granted for one of the specified endpoints, because it does not exist. ",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleRoleNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage("No role with the given short name exists.",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<CustomErrorMessage> handleRoleAlreadyExists(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage("A role with the given short name already exists.",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}

