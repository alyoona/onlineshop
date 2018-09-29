package com.stroganova.onlineshop.exception;

public class NoUniqueUserNameException extends RuntimeException {

    public NoUniqueUserNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
