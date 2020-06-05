package com.uns.ftn.agentservice.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String exceptionMessage) { super(exceptionMessage); }

}
