package com.uns.ftn.messageservice.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String exceptionMessage) { super(exceptionMessage); }

}
