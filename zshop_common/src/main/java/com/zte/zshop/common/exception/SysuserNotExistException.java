package com.zte.zshop.common.exception;

/**
 * Author:helloboy
 * Date:2020-11-06 15:53
 * Description:<描述>
 */
public class SysuserNotExistException extends Exception
{
    public SysuserNotExistException()
    {
        super();
    }

    public SysuserNotExistException(String message)
    {
        super(message);
    }

    public SysuserNotExistException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SysuserNotExistException(Throwable cause)
    {
        super(cause);
    }

    protected SysuserNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
