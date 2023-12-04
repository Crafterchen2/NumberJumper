package com.deckerben.numberjumper;

import java.awt.*;

public class IllegalDirectionException extends Exception{

    public IllegalDirectionException(Direction dir) {
        super("Can't move in direction "+dir+" without leaving the playfield.");
    }

    public IllegalDirectionException(String message) {
        super(message);
    }

    public IllegalDirectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDirectionException(Throwable cause) {
        super(cause);
    }

    protected IllegalDirectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static boolean check(Direction dir, Point player, Dimension field){
        Point target = new Point(player);
        target.translate(dir.columnOffset, dir.rowOffset);
        return PlayerOutOfBoundsException.check(target,field);
    }

    public static void checkThrow(Direction dir, Point player, Dimension field) throws IllegalDirectionException {
        if (!check(dir, player, field)) throw new IllegalDirectionException(dir);
    }

}
