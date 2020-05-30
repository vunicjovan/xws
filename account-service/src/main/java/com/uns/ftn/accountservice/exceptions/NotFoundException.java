package com.uns.ftn.accountservice.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String exceptionMessage) { super(exceptionMessage); }

}
