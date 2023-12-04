package com.deckerben.numberjumper;

public class AlreadyInGoalExcepition extends Exception{

    public AlreadyInGoalExcepition() {
        super("Can't jump anymore as the Game is over because the goal has been reached.");
    }

    public AlreadyInGoalExcepition(String message) {
        super(message);
    }

    public AlreadyInGoalExcepition(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyInGoalExcepition(Throwable cause) {
        super(cause);
    }

    protected AlreadyInGoalExcepition(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
