package com.deckerben.numberjumper;

public interface SpecialField extends NumberField{

    boolean canEnter(Direction targetSide);

    boolean canLeave(Direction targetSide);

}
