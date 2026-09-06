package com.evently.exception;

import com.evently.dto.Response.ApiResponse;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleGrpcException(
            StatusRuntimeException e) {

        if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            ApiResponse.fail(
                                    HttpStatus.NOT_FOUND.value(),
                                    e.getStatus().getDescription()
                            )
                    );
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.fail(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal server error"
                        )
                );
    }
}