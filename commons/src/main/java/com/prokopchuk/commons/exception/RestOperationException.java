package com.prokopchuk.commons.exception;

import lombok.Getter;

@Getter
public class RestOperationException extends OperationException {

    private final int code;

    public RestOperationException(String message, int code) {
        super(message);
        this.code = code;
    }

}
