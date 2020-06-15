package com.uns.ftn.agent.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String errorMessage) { super(errorMessage); }
}
