package com.bol.mancala.error.exception;

public abstract class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
