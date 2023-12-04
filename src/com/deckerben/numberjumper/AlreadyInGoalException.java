package com.deckerben.numberjumper;

public class AlreadyInGoalException extends Exception{

    public AlreadyInGoalException() {
        super("Can't jump anymore as the Game is over because the goal has been reached.");
    }

    public AlreadyInGoalException(String message) {
        super(message);
    }

    public AlreadyInGoalException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyInGoalException(Throwable cause) {
        super(cause);
    }

    protected AlreadyInGoalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
