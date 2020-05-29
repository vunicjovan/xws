package com.uns.ftn.catalogservice.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String exceptionMessage) { super(exceptionMessage); }

}
