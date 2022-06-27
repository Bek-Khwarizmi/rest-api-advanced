package com.epam.esm.configuration.exceptionHandler;

import com.epam.esm.configuration.localization.TranslatorConfig;
import com.epam.esm.exception.ErrorResponse;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import static com.epam.esm.exception.ErrorResponseExceptionCode.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Class {@code ExceptionHandler} presents entity
 * which is returned from controller in case of exception
 *
 * @author Bekhzod Kurbonboev
 *
 */

@RestControllerAdvice
public class ExceptionHandlerConfiguration {

    @ExceptionHandler(GeneralPersistenceException.class)
    public final String handleDaoExceptions(GeneralPersistenceException ex) {
        String details = TranslatorConfig.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND, details, NOT_FOUND_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler(value = {IncorrectParamException.class})
    public final String handleIncorrectParameterExceptions(IncorrectParamException ex){
        String message = TranslatorConfig.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, message, BAD_REQUEST_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final String handleBadRequestExceptions() {
        String details = TranslatorConfig.toLocale("exception.badRequest");
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, details, BAD_REQUEST_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final String handleBadRequestException() {
        String details = TranslatorConfig.toLocale("exception.noHandler");
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND, details, NOT_FOUND_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final String methodNotAllowedExceptionException() {
        String details = TranslatorConfig.toLocale("exception.notSupported");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED, details, METHOD_NOT_ALLOWED_EXCEPTION.getCode());
        return errorResponse.toString();
    }
}
