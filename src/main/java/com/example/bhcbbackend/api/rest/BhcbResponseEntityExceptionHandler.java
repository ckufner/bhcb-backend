package com.example.bhcbbackend.api.rest;

import com.example.bhcbbackend.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class BhcbResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFound(HttpServletResponse response) throws IOException
    {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(NumberFormatException.class)
    public void handleNumberFormatException(HttpServletResponse response) throws IOException
    {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
