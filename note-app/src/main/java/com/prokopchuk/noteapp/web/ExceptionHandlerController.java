package com.prokopchuk.noteapp.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.exception.OperationException;
import com.prokopchuk.commons.exception.RestOperationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerController {

    private static final String SOMETHING_WENT_WRONG = "Something went wrong";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.warn("Handling exception: {}", e.getMessage(), e);

        return ResponseEntity.internalServerError()
            .body(Responses.internalServerError(SOMETHING_WENT_WRONG));
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleOperationException(OperationException e) {
        log.warn("Handling operation exception: {}", e.getMessage(), e);

        return ResponseEntity.internalServerError()
            .body(Responses.internalServerError(e.getMessage()));
    }

    @ExceptionHandler(RestOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleRestOperationException(RestOperationException e) {
        log.warn("Handling rest operation exception: {}", e.getMessage(), e);

        return new ResponseEntity<>(
            Responses.of(e.getCode(), e.getMessage()),
            HttpStatusCode.valueOf(e.getCode())
        );
    }

}
