package com.ccsw.tutorial.exception;

/*
Pongo extends de RuntimeException para que Spring se encargue de hacer el tratamiento
Sirve para represenar un error de negocio de forma rica y estructurada
*/
public class BusinessConflictException extends RuntimeException {

    private final String errorCode; //Para el Fortend identificar el tipo exacto de error
    private final String field; //El campo en concreto que causa el error

    public BusinessConflictException(String errorCode, String message, String field) {
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
