package com.epam.esm.exception;

public class IncorrectParamException extends Exception{

    public IncorrectParamException() {
    }

    public IncorrectParamException(String message) {
        super(message);
    }

    public IncorrectParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectParamException(Throwable cause) {
        super(cause);
    }
}
