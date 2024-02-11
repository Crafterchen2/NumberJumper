package com.deckerben.numberjumper.console;

import com.deckerben.numberjumper.NumberField;

public class ConsoleNumber implements NumberField, ConsoleField {

    private final int value;

    public ConsoleNumber(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getName()+": '"+getChar()+"'";
    }

    @Override
    public char getChar() {
        return Integer.toString(getValue()).charAt(0);
    }

    @Override
    public String getGenericName() {
        return "Zahlenfeld";
    }

}
