package com.deckerben.numberjumper;

import java.awt.*;

public enum Direction {

    NORTH(1,0),
    EAST(0,1),
    SOUTH(-1,0),
    WEST(0,-1);

    final int rowOffset, columnOffset;

    Direction(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public Direction getOpposing(){
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }

    public static Direction getOpposing(Direction dir){
        return dir.getOpposing();
    }

    public Point getAsPoint(){
        return new Point(columnOffset,rowOffset);
    }

}
