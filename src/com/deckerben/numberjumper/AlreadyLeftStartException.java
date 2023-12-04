package com.deckerben.numberjumper;

public class AlreadyLeftStartException extends Exception{

    public AlreadyLeftStartException() {
        super("Can't re-enter the start fields as they have already been left.");
    }

    public AlreadyLeftStartException(String message) {
        super(message);
    }

    public AlreadyLeftStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyLeftStartException(Throwable cause) {
        super(cause);
    }

    protected AlreadyLeftStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
