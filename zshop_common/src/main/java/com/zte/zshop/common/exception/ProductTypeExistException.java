package com.zte.zshop.common.exception;

/**
 * Author:helloboy
 * Date:2020-10-28 8:55
 * Description:<描述>
 */
public class ProductTypeExistException extends Exception
{
    public ProductTypeExistException()
    {
    }

    public ProductTypeExistException(String message)
    {
        super(message);
    }

    public ProductTypeExistException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProductTypeExistException(Throwable cause)
    {
        super(cause);
    }

    public ProductTypeExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
