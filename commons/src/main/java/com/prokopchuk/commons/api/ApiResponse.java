package com.prokopchuk.commons.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ApiResponse<T> {

    private final int code;
    private String message;
    private T body;

    @JsonCreator
    public ApiResponse(@JsonProperty("code") int code) {
        this.code = code;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, T body) {
        this.code = code;
        this.body = body;
    }

}
