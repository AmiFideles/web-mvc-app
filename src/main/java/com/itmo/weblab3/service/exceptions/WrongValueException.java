package com.itmo.weblab3.service.exceptions;

public class WrongValueException extends Exception{
    public WrongValueException(String message) {
        super(message);
    }

    public WrongValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
