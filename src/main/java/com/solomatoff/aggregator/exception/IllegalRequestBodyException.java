package com.solomatoff.aggregator.exception;

public class IllegalRequestBodyException extends IllegalArgumentException {
    public IllegalRequestBodyException(String s) {
        super(s);
    }
}
