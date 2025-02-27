package com.prokopchuk.commons.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Responses {

    public static <T> ApiResponse<T> ok(T t) {
        return new ApiResponse<>(200, t);
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(200);
    }

}
