package com.prokopchuk.commons.exception;

public class BadRequestException extends RestOperationException {

    public BadRequestException(String message) {
        super(message, 400);
    }

}
