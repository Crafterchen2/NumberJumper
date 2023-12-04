package com.deckerben.numberjumper;

import java.awt.*;

public class PlayerOutOfBoundsException extends Exception{

    public PlayerOutOfBoundsException(Point player, Dimension field) {
        super("Player at column="+player.x+" and row="+player.y+" is not inside field with columns="+field.width+" and rows="+field.height+".");
    }

    public PlayerOutOfBoundsException(String message) {
        super(message);
    }

    public PlayerOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerOutOfBoundsException(Throwable cause) {
        super(cause);
    }

    protected PlayerOutOfBoundsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static boolean check(Point player, Dimension field){
        return between(player.x, 0, field.width-1) && between(player.y, -1, field.height);
    }

    public static void checkThrow(Point player, Dimension field) throws PlayerOutOfBoundsException {
        if (!check(player, field)) throw new PlayerOutOfBoundsException(player,field);
    }

    public static boolean between(int num, int lower, int upper){
        return (num >= lower) && (num <= upper);
    }

}
