package com.yu.controller;

import com.yu.exception.InconsistencyDataException;
import com.yu.exception.RecordNotFoundException;
import com.yu.exception.SecurityRiskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_INVALID_USE_ERROR = "INVALID_USE_ERROR";

    private static final String ERROR_VALIDATION_ERROR = "VALIDATION_ERROR";

    private static final String ERROR_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    private static final String ERROR_RECORD_NOT_FOUND = "RECORD_NOT_FOUND";

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SecurityRiskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> securityRisk(SecurityRiskException exception){
        logger.warn("securityRisk: {}", exception.getMessage(), exception);
        return Collections.singletonMap("errorCode", ERROR_VALIDATION_ERROR);
    }

    @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> sqlLevelException(java.sql.SQLIntegrityConstraintViolationException exception){
        logger.warn("sqlLevelException: {}", exception.getMessage(), exception);
        return Collections.singletonMap("errorCode", ERROR_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> recordNotFound(RecordNotFoundException exception){
        logger.warn("recordNotFound: {}", exception.getMessage(), exception);
        return Collections.singletonMap("errorCode", ERROR_RECORD_NOT_FOUND);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> mvcValidationFailure(org.springframework.web.bind.MethodArgumentNotValidException exception){
        logger.warn("mvcValidationFailure: {}", exception.getMessage(), exception);
        return Collections.singletonMap("errorCode", ERROR_VALIDATION_ERROR);
    }

    @ExceptionHandler(InconsistencyDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalidCallFailure(InconsistencyDataException exception){
        logger.warn("invalidCallFailure: {}", exception.getMessage(), exception);
        return Collections.singletonMap("errorCode", ERROR_INVALID_USE_ERROR);
    }

}
