package com.cofinprobootcamp.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.DateTimeException;

@ControllerAdvice
public class TheExceptionHandler {

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<CustomErrorMessage> handleDateTimeException(DateTimeException e, WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Datum nicht in passendem Format!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "VALIDATION: " +
                        e.getBindingResult().getFieldErrors().stream()
                                .map((fieldError -> fieldError.getDefaultMessage()))
                                .toList(),
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CSVFormatException.class)
    public ResponseEntity<CustomErrorMessage> handleCSVFormatException(
            CSVFormatException e, WebRequest wr
    ) {
        CustomErrorMessage body = new CustomErrorMessage(
                e.getError(),
                wr.getDescription(false)
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CSVArgumentNotValidException.class)
    public ResponseEntity<CustomErrorMessage> handleCSVArgumentNotValidException(
            CSVArgumentNotValidException e, WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "VALIDATION:" +
                        e.getViolations()+
                        "  (At row number "  + e.getRowNumber() + ")",
                wr.getDescription(false)
        );
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleProfileNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Profil konnte nicht gefunden werden!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<CustomErrorMessage> handleProfileAlreadyExistsException(ProfileAlreadyExistsException paee, WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Der Nutzer hat bereits ein Profil!",
                wr.getDescription(false),
                paee
        );
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JobTitleNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleJobTitleNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Unbekannter Jobtitel!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpertiseNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleExpertiseNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Unbekannte Prim??rkompetenz!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleUsernameNotFoundException(
            UsernameNotFoundException unfe,
            WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                unfe.getMessage(),
                wr.getDescription(false),
                unfe);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleUserNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Der angefragte Nutzer konnte nicht gefunden werden!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EndPointNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleEndPointNotFoundException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Einer der angegebenen Endpunkte existiert nicht!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleRoleNotFoundException(
            RoleNotFoundException rnfe,
            WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                rnfe.getRoleErrorMessage(),
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<CustomErrorMessage> handleRoleAlreadyExists(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Eine Rolle mit diesem Namen existiert bereits!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalOperationFailedException.class)
    public ResponseEntity<CustomErrorMessage> handleUserCreationFailed(
            InternalOperationFailedException iofe,
            WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                iofe.getMessage(),
                wr.getDescription(false),
                iofe.getCause());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidAuthorityFormatException.class)
    public ResponseEntity<CustomErrorMessage> handleInvalidAuthorityFormat(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage(
                "Es gab einen Fehler beim Pr??fen der Zugriffsberechtigungen dieses Nutzers [Ung??ltiges Rechteformat]. Zugriff verweigert.",
                wr.getDescription(false)
        );
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ImageFormatNotAllowedException.class)
    public ResponseEntity<CustomErrorMessage> handleImageFormatNotAllowedException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage("Bild Muss Format PNG, JPG oder JPEG haben!",
                wr.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailNotSentException.class)
    public ResponseEntity<CustomErrorMessage> handleMailNotSentException(WebRequest wr) {
        CustomErrorMessage body = new CustomErrorMessage("Mail wurde nicht gesendet!",
                wr.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.BAD_GATEWAY);
    }
}

