package com.deckerben.numberjumper;

public interface NumberField {

    int getValue();

    default int manipulate(int points){
        return points + getValue() +1;
    }

}
