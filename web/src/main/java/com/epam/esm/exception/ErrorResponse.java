package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse extends Exception{

    private HttpStatus status;
    private String errorMessage;
    private int errorCode;

    @Override
    public String toString() {
        return "HTTP Status: "+ status.value()+
                "\nresponse body\n{\n" +
                "\"errorMessage\"=\"" + errorMessage + "\"," +
                "\n\"errorCode\"=" + errorCode + "\n" +
                '}';
    }
}
