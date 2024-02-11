package com.deckerben.numberjumper.console;

import com.deckerben.numberjumper.NumberField;
import com.deckerben.numberjumper.NumberJumper;
import com.deckerben.numberjumper.SpecialField;

import java.awt.*;

public class ConsoleJumper implements NumberJumper {

    private int target;
    private int points = 0;
    private final NumberField[][] playField;

    public static final char player = '&';
    public static final char start = 'S';
    public static final char verticalLine = '|';
    public static final char horizontalLine = '-';
    public static final char crossingLine = '+';
    public static final char fieldLeftEdge = '>';
    public static final char fieldRightEdge = '<';

    public ConsoleJumper(int nRows, int nSpecials){
        NumberField[] rows = new NumberField[nRows];
        for (int i = 0; i < nRows; i++) {
            rows[i] = new ConsoleNumber(i);//new FieldExample(i);
        }
        SpecialField[] specials = new SpecialField[nSpecials];
        for (int i = 0; i < nSpecials; i++) {
            specials[i] = new BlockerField();//new SpecialExample(i);
        }
        playField = NumberJumper.generateField(rows,4,specials);
    }

    public static void main(String[] args) {
        System.out.println("START>|S|S|S|S|<");
        printLine(6,15);
        ConsoleJumper cj = new ConsoleJumper(4,0);
        cj.printPlayField(false);
        cj.printPlayField(false);
    }

    private void printPlayField(boolean showPlayer){
        System.out.print("START"+fieldRightEdge+verticalLine);
        for (int i = 0; i < getFieldSize().width; i++) {
            printCell((showPlayer && i == getCurrentPlayerLoc().x)?player:start);
        }
        System.out.println(fieldLeftEdge);
        printLine(5,10);
    }

    private static void printLine(int from, int to){
        for (int i = 0; i < from; i++) System.out.print(' ');
        for (int i = from; i < to; i++) System.out.print((i%2 == from%2)?crossingLine:horizontalLine);
        System.out.println();
    }

    private void printCell(char symbol){
        System.out.print(symbol+verticalLine);
    }

    public int makeTargetPoints(int min, int max){
        if (max < min) throw new IllegalArgumentException("max must not be smaller than min.");
        return (int) (Math.random()*((max+1)-min))+min;
    }

    @Override
    public int getCurrentPoints() {
        return points;
    }

    @Override
    public int getTargetPoints() {
        return 0;
    }

    @Override
    public Point getCurrentPlayerLoc() {
        return null;
    }

    @Override
    public NumberField[][] getPlayField() {
        return playField;
    }
}
