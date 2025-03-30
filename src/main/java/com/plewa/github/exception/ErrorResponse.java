package com.plewa.github.exception;

public record ErrorResponse(
        int status,
        String message
) {
}
