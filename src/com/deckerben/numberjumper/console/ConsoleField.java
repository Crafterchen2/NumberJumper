package com.deckerben.numberjumper.console;

public interface ConsoleField {

    char getChar();
    String getGenericName();

    default String getName() {
        return getGenericName()+" ("+getChar()+")";
    }

}
