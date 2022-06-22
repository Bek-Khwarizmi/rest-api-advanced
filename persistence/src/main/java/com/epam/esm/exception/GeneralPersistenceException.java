package com.epam.esm.exception;

public class GeneralPersistenceException extends Exception{

    public GeneralPersistenceException() {
    }

    public GeneralPersistenceException(String messageCode) {
        super(messageCode);
    }

    public GeneralPersistenceException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public GeneralPersistenceException(Throwable cause) {
        super(cause);
    }
}
