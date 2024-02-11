package com.deckerben.numberjumper.console;

import com.deckerben.numberjumper.Direction;

public class BlockerField extends TrapField {

    @Override
    public boolean canEnter(Direction targetSide) {
        return false;
    }

    @Override
    public char getChar() {
        return '#';
    }

    @Override
    public String getGenericName() {
        return "Blockerfeld";
    }
}
