package com.uns.ftn.messageservice.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {}

    public BadRequestException(String errorMessage) { super(errorMessage); }
}
