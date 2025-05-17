package com.example.bhcbbackend.exceptions;

public class BhcbException extends RuntimeException
{
    public BhcbException(final String message)
    {
        super(message);
    }

    public BhcbException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public BhcbException(final Throwable cause)
    {
        super(cause);
    }

    public BhcbException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
