package com.deckerben.numberjumper;

public class CanNotEnterException extends Exception{

    public CanNotEnterException(Direction dir) {
        super("Entering this tile is not permitted from direction "+dir+".");
    }

    public CanNotEnterException(String message) {
        super(message);
    }

    public CanNotEnterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CanNotEnterException(Throwable cause) {
        super(cause);
    }

    protected CanNotEnterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
