package com.uns.ftn.accountservice.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {}

    public UnauthorizedException(String errorMessage) { super(errorMessage); }

}
