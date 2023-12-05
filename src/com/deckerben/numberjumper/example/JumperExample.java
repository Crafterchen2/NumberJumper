package com.deckerben.numberjumper.example;

import com.deckerben.numberjumper.*;

import java.awt.*;

public class JumperExample implements NumberJumper {

    private int points = 0;
    private final Point location = new Point(0,-1);
    private final NumberField[][] playField;

    public JumperExample(int nRows, int nSpecials){
        NumberField[] rows = new NumberField[nRows];
        for (int i = 0; i < nRows; i++) {
            rows[i] = new FieldExample(i);
        }
        SpecialField[] specials = new SpecialField[nSpecials];
        for (int i = 0; i < nSpecials; i++) {
            specials[i] = new SpecialExample(i);
        }
        //playField = NumberJumper.generateField(rows,4,specials);
        playField = new NumberField[nRows][];
        playField[0] = new NumberField[]{new FieldExample(0),new FieldExample(0),new SpecialExample(0),new FieldExample(0)};
        playField[1] = new NumberField[]{new FieldExample(1),new FieldExample(1),new FieldExample(1),new FieldExample(1)};
        playField[2] = new NumberField[]{new FieldExample(2),new FieldExample(2),new FieldExample(2),new FieldExample(2)};
        playField[3] = new NumberField[]{new FieldExample(3),new FieldExample(3),new FieldExample(3),new FieldExample(3)};
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println("\\/\\/\\/---IT"+i+"---\\/\\/\\/");
            NumberJumper nj = new JumperExample(4,6);
            try {
                nj.jump(Direction.NORTH);
                nj.jump(Direction.EAST);
                nj.jump(Direction.EAST);
                nj.jump(Direction.EAST);
                //nj.jump(Direction.EAST);
                //nj.jump(Direction.WEST);
                //nj.jump(Direction.WEST);
                //nj.jump(Direction.WEST);
                //nj.jump(Direction.NORTH);
                //nj.jump(Direction.EAST);
                //nj.jump(Direction.EAST);
                //nj.jump(Direction.NORTH);
                //nj.jump(Direction.NORTH);
                //System.out.println(nj.isInGoal()+"; "+nj.isWin());
            }   catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
            System.out.println("/\\/\\/\\---IT"+i+"---/\\/\\/\\");
        }
    }

    @Override
    public int jump(Direction dir) throws PlayerOutOfBoundsException, IllegalDirectionException, CanNotLeaveException, CanNotEnterException, AlreadyLeftStartException, AlreadyInGoalException {
        return points = NumberJumper.super.jump(dir);
    }

    @Override
    public int getCurrentPoints() {
        return points;
    }

    @Override
    public int getTargetPoints() {
        return 10;
    }

    @Override
    public Point getCurrentPlayerLoc() {
        return location;
    }

    @Override
    public NumberField[][] getPlayField() {
        return playField;
    }
}

class FieldExample implements NumberField {

    private final int value;

    public FieldExample(int value){
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

}

class SpecialExample implements SpecialField {

    private final int value;

    public SpecialExample(int value){
        this.value = value;
    }

    @Override
    public int manipulate(int points) {
        System.out.println("Special "+ getValue()+" called (RRRrrring! :) )");
        return 0;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean canEnter(Direction targetSide) {
        return switch (targetSide) {
            case NORTH, EAST, WEST -> false;
            case SOUTH -> true;
        };
    }

    @Override
    public boolean canLeave(Direction targetSide) {
        return switch (targetSide) {
            case NORTH, SOUTH, WEST -> false;
            case EAST -> true;
        };
    }
}
