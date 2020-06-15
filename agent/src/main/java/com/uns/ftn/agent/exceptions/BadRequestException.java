package com.uns.ftn.agent.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String errorMessage) { super(errorMessage); }
}
