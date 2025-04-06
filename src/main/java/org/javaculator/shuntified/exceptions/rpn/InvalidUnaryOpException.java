package org.javaculator.shuntified.exceptions.rpn;

import org.javaculator.shuntified.models.operator.impl.UnaryOp;

public class InvalidUnaryOpException extends RpnParsingException {
    public InvalidUnaryOpException(UnaryOp token) {
        super("Invalid unary op: missing operators before unary operation: %s".formatted(token.getSign()));
    }
}
