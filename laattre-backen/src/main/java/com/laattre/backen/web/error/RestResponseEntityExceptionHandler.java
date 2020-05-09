package com.laattre.backen.web.error;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.laattre.backen.web.util.GenericResponse;
import com.sun.mail.smtp.SMTPAddressFailedException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messages;

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    // 400
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ InvalidOldPasswordException.class })
    public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.invalidOldPassword", null, request.getLocale()), "InvalidOldPassword");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ ReCaptchaInvalidException.class })
    public ResponseEntity<Object> handleReCaptchaInvalid(final RuntimeException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.invalidReCaptcha", null, request.getLocale()), "InvalidReCaptcha");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 404
    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
        logger.error("404 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.userNotFound", null, request.getLocale()), "UserNotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409
    @ExceptionHandler({ UserAlreadyExistException.class })
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        logger.error("409 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.regError", null, request.getLocale()), "UserAlreadyExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 500
    @ExceptionHandler({ MailAuthenticationException.class })
    public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.email.config.error", null, request.getLocale()), "MailError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ ReCaptchaUnavailableException.class })
    public ResponseEntity<Object> handleReCaptchaUnavailable(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.unavailableReCaptcha", null, request.getLocale()), "InvalidReCaptcha");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({ CustomBadCredentialsException.class })
    public ResponseEntity<Object> handleBadCredentialsException(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        String messageError = "message.badCredentials";
        
        if (ex.getMessage().equalsIgnoreCase("User is disabled")) {
        	messageError = "auth.message.disabled";
        }
        else if (ex.getMessage().equalsIgnoreCase("User account has expired")) {
        	messageError = "auth.message.expired";
        }
        else if (ex.getMessage().equalsIgnoreCase("blocked")) {
        	messageError = "auth.message.blocked";
        }
        else if (ex.getMessage().equalsIgnoreCase("Bad credentials")) {
        	messageError = "message.badCredentials";
        }
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(messageError, null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(final NoSuchElementException ex, final WebRequest request) {
    	logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
 
    }

    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<Object> handleResourceDisabledException(final DisabledException ex, final WebRequest request) {
    	logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("auth.message.disabled", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
 
    }
    
    @ExceptionHandler({MailSendException.class})
    public ResponseEntity<Object> handleMailSendException(final MailSendException ex, final WebRequest request) {
    	logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.badEmail", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
 
    }
    
    @ExceptionHandler({SendFailedException.class})
    public ResponseEntity<Object> handleSendFailedException(final SendFailedException ex, final WebRequest request) {
    	logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.badEmail", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
 
    }
    
    @ExceptionHandler({SMTPAddressFailedException.class})
    public ResponseEntity<Object> handleSMTPAddressFailedException(final SMTPAddressFailedException ex, final WebRequest request) {
    	logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.badEmail", null, request.getLocale()), "InternalError");
        return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
 
    }

}
