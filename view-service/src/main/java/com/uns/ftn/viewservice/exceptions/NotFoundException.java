package com.uns.ftn.viewservice.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String exceptionMessage) { super(exceptionMessage); }
}
