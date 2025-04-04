package org.javaculator.shuntified.models;

import org.javaculator.shuntified.models.bracket.BracketToken;
import org.javaculator.shuntified.models.operator.impl.AssignOp;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.models.literal.LiteralToken;
import org.javaculator.shuntified.models.variable.VariableToken;

public class TokenUtils {
    public static int getRpnOperationIndex(Token token) {
        if (token instanceof LiteralToken || token instanceof VariableToken) {
            return 1;
        } else if (token instanceof BinaryOp) {
            return 2;
        } else if (token instanceof UnaryOp) {
            return 3;
        } else if (token instanceof BracketToken) {
            return 4;
        } else if (token instanceof AssignOp) {
            return 5;
        } else {
            throw  new RuntimeException("unhandled token type: " + token.getSign());
        }
    }
}
