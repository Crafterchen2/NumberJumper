package com.deckerben.numberjumper.console;

import com.deckerben.numberjumper.Direction;
import com.deckerben.numberjumper.SpecialField;

public class TrapField implements SpecialField, ConsoleField {

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public int manipulate(int points) {
        return getValue();
    }

    @Override
    public boolean canEnter(Direction targetSide) {
        return true;
    }

    @Override
    public boolean canLeave(Direction targetSide) {
        return false;
    }

    @Override
    public String toString() {
        return getName()+": '"+getChar()+"'";
    }

    @Override
    public char getChar() {
        return '⚠';
    }

    @Override
    public String getGenericName() {
        return "Fallenfeld";
    }
}
