package com.evently.dto.Response;

import java.time.Instant;

public record ApiResponse<T>(boolean success, int code, T payload, Instant timestamp) {

    public static <T> ApiResponse<T> ok(int code,T payload){
        return new ApiResponse<>(true,code,payload,Instant.now());
    }

    public static <T> ApiResponse<T> fail(int code,T payload){
        return new ApiResponse<>(false,code,payload,Instant.now());
    }

}
