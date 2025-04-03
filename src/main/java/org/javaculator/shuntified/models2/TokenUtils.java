package org.javaculator.shuntified.models2;

import org.javaculator.shuntified.models2.bracket.BracketToken;
import org.javaculator.shuntified.models2.op.impl.BinaryOp;
import org.javaculator.shuntified.models2.op.impl.UnaryOperator;
import org.javaculator.shuntified.models2.val.ValueToken;
import org.javaculator.shuntified.models2.vars.VariableToken;

public class TokenUtils {
    public static int getRpnOperationIndex(Token token) {
        if (token instanceof ValueToken || token instanceof VariableToken) {
            return 1;
        } else if (token instanceof BinaryOp binaryOp) {
            if (binaryOp.isAssignment()) {
                return 5;
            }

            return 2;
        } else if (token instanceof UnaryOperator) {
            return 3;
        } else if (token instanceof BracketToken) {
            return 4;
        } else {
            throw  new RuntimeException("unhandled token type: " + token.getSign());
        }
    }
}
