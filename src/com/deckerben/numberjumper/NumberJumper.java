package com.deckerben.numberjumper;

import java.awt.*;
import java.util.Random;

public interface NumberJumper {

    int getCurrentPoints();

    int getTargetPoints();

    Point getCurrentPlayerLoc();

    NumberField[][] getPlayField();

    default Dimension getFieldSize(){
        return new Dimension(getPlayField().length,getPlayField()[0].length);
    }

    default NumberField getFieldUnderPlayer(){
        return getFieldAtPoint(getCurrentPlayerLoc());
    }

    default NumberField getFieldAtPoint(Point point){
        return getPlayField()[point.y][point.x];
    }

    default NumberField getFieldInDirection(Direction dir) throws IllegalDirectionException{
        IllegalDirectionException.checkThrow(dir,getCurrentPlayerLoc(),getFieldSize());
        return getPlayField()[getCurrentPlayerLoc().y + dir.rowOffset][getCurrentPlayerLoc().x + dir.columnOffset];
    }

    default int jump(Direction dir) throws PlayerOutOfBoundsException, IllegalDirectionException, CanNotLeaveException, CanNotEnterException, AlreadyLeftStartException, AlreadyInGoalException {
        PlayerOutOfBoundsException.checkThrow(getCurrentPlayerLoc(),getFieldSize());
        if (!isInStart() && dir == Direction.SOUTH && getCurrentPlayerLoc().y == 0) throw new AlreadyLeftStartException();
        NumberField srcField = (getCurrentPlayerLoc().y < 0) ? null : getFieldUnderPlayer();
        if (!isInGoal()) {
            IllegalDirectionException.checkThrow(dir,getCurrentPlayerLoc(),getFieldSize());
            getCurrentPlayerLoc().translate(dir.columnOffset, dir.rowOffset);
        }   else throw new AlreadyInGoalException();
        if (!isInGoal() && !isInStart()) {
            if (srcField != null) {
                if (isSpecial(srcField)) {
                    SpecialField specialField = (SpecialField) srcField;
                    if (!specialField.canLeave(dir)) throw new CanNotLeaveException(dir);
                }
            }
            NumberField targetField = getFieldUnderPlayer();
            if (isSpecial(targetField)) {
                SpecialField specialField = (SpecialField) targetField;
                if (!specialField.canEnter(dir.getOpposing())) throw new CanNotEnterException(dir);
            }
            return targetField.manipulate(getCurrentPoints());
        } else {
            return getCurrentPoints();
        }
    }

    default boolean isSpecial(Point point){
        return getFieldAtPoint(point) instanceof SpecialField;
    }

    default boolean isSpecial(NumberField field){
        return field instanceof SpecialField;
    }

    default boolean isWin() throws PlayerOutOfBoundsException {
        return isInGoal() && getCurrentPoints() == getTargetPoints();
    }

    default boolean isInGoal()throws PlayerOutOfBoundsException {
        PlayerOutOfBoundsException.checkThrow(getCurrentPlayerLoc(),getFieldSize());
        return getCurrentPlayerLoc().y == getFieldSize().height;
    }

    default boolean isInStart() throws PlayerOutOfBoundsException {
        PlayerOutOfBoundsException.checkThrow(getCurrentPlayerLoc(),getFieldSize());
        return getCurrentPlayerLoc().y == -1;
    }

    static void generateField(NumberField[][] field, SpecialField[] specials){
        if (specials.length < (field.length * field[0].length)) {
            Random rng = new Random(System.currentTimeMillis()); //rng.nextInt(0, field.length);
            int x, y;
            for (SpecialField special : specials) {
                x = rng.nextInt(0, field.length);
                y = rng.nextInt(0, field[0].length);
                if (!(field[x][y] instanceof SpecialField)) {
                    field[x][y] = special;
                } else {
                    A:
                    for (int j = 0; j < field.length; j++) {
                        for (int k = 0; k < field[j].length; k++) {
                            if (!(field[(x + j) % field.length][(x + k) % field[j].length] instanceof SpecialField)) {
                                field[(x + j) % field.length][(x + k) % field[j].length] = special;
                                break A;
                            }
                        }
                    }
                }
            }
        } else {
            int n = 0;
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    field[i][j] = specials[n];
                    n++;
                }
            }
        }
    }

    static NumberField[][] generateField(int rows, NumberField[] columns, SpecialField[] specials){
        NumberField[][] rv = generateField(rows, columns);
        generateField(rv,specials);
        return rv;
    }

    static NumberField[][] generateField(NumberField[] rows, int columns, SpecialField[] specials){
        NumberField[][] rv = generateField(rows, columns);
        generateField(rv,specials);
        return rv;
    }

    static NumberField[][] generateField(int rows, NumberField[] columns){
        NumberField[][] rv = new NumberField[rows][columns.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns.length; j++) {
                rv[i][j] = columns[j];
            }
        }
        return rv;
    }

    static NumberField[][] generateField(NumberField[] rows, int columns){
        NumberField[][] rv = new NumberField[rows.length][columns];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < columns; j++) {
                rv[i][j] = rows[i];
            }
        }
        return rv;
    }

}