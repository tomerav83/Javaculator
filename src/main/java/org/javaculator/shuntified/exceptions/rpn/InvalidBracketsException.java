package org.javaculator.shuntified.exceptions.rpn;

public class InvalidBracketsException extends RpnParsingException {
    public InvalidBracketsException() {
        super("Invalid brackets: encountered closing bracket without a previous opening");
    }
}
