package com.example.bhcbbackend.exceptions;

public class BhcpException extends RuntimeException
{
    public BhcpException(final String message)
    {
        super(message);
    }

    public BhcpException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public BhcpException(final Throwable cause)
    {
        super(cause);
    }

    public BhcpException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
