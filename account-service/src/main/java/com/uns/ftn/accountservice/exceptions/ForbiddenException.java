package com.uns.ftn.accountservice.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {}

    public ForbiddenException(String exception) { super(exception); }
}
