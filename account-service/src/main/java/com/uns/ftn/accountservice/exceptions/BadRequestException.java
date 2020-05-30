package com.uns.ftn.accountservice.exceptions;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String errorMessage) { super(errorMessage); }
}
