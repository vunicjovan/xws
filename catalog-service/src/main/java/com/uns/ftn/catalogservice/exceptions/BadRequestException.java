package com.uns.ftn.catalogservice.exceptions;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String errorMessage) { super(errorMessage); }
}
