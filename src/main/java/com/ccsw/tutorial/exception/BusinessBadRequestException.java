package com.ccsw.tutorial.exception;

public class BusinessBadRequestException extends RuntimeException {

    private final String errorCode;
    private final String field;

    public BusinessBadRequestException(String errorCode, String message, String field) {
        super(message);
        this.errorCode = errorCode;
        this.field = field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }
}
