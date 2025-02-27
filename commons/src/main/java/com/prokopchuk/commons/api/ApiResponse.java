package com.prokopchuk.commons.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final int code;
    private String message;
    private T body;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, T body) {
        this.code = code;
        this.body = body;
    }

}
