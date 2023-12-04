package com.deckerben.numberjumper;

public class CanNotLeaveException extends Exception{

    public CanNotLeaveException(Direction dir) {
        super("Leaving this tile is not permitted in direction "+dir+".");
    }

    public CanNotLeaveException(String message) {
        super(message);
    }

    public CanNotLeaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public CanNotLeaveException(Throwable cause) {
        super(cause);
    }

    protected CanNotLeaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
