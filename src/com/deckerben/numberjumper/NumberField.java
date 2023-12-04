package com.deckerben.numberjumper;

public interface NumberField {

    int getRow();

    default int manipulate(int points){
        return points + getRow() +1;
    }

}
