package org.javaculator.shuntified.exceptions.rpn;

import org.javaculator.shuntified.models.Token;

public class InvalidAssignOpException extends RpnParsingException {
    public InvalidAssignOpException(Token lhs) {
        super("Invalid assignment: lhs token of type %s".formatted(lhs.getClass()));
    }

    public InvalidAssignOpException() {
        super("Invalid assignment: empty lhs token");
    }
}
