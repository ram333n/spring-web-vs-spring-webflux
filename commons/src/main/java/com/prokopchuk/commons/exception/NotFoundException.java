package com.prokopchuk.commons.exception;

public class NotFoundException extends RestOperationException{

    public NotFoundException(String message) {
        super(message, 404);
    }

}
