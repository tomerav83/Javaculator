package org.javaculator.shuntified.models2.op;

import org.javaculator.shuntified.models2.Token.Association;
import org.javaculator.shuntified.models2.op.impl.BinaryOp;
import org.javaculator.shuntified.models2.op.impl.UnaryOperator;
import org.javaculator.shuntified.pattern.Patterns;

import java.util.Collection;
import java.util.Map;

public class Operators {
    private final static Map<String, Operator> OPERATORS = Map.ofEntries(
            UnaryOperator.asEntry("post_inc", Patterns.POST_INC_REPLACEABLE, Association.LEFT, 1),
            UnaryOperator.asEntry("post_dec", Patterns.POST_DEC_REPLACEABLE, Association.LEFT, 1),
            UnaryOperator.asEntry("pre_inc", Patterns.PRE_INC_REPLACEABLE, Association.RIGHT, 2),
            UnaryOperator.asEntry("pre_dec", Patterns.PRE_DEC_REPLACEABLE, Association.RIGHT, 2),
            UnaryOperator.asEntry("−", Patterns.NEGATE_REPLACEABLE, Association.RIGHT, 2),
            BinaryOp.asEntry("+", Patterns.ADD, Association.LEFT, 4),
            BinaryOp.asEntry("-", Patterns.SUB, Association.LEFT, 4),
            BinaryOp.asEntry("*", Patterns.MULTIPLY, Association.LEFT, 3),
            BinaryOp.asEntry("/", Patterns.DIV, Association.LEFT, 3),
            BinaryOp.asEntry("%", Patterns.MOD, Association.LEFT, 3),
            BinaryOp.asEntry("=", Patterns.ASSIGN, Association.RIGHT, 14)
    );

    public static Collection<Operator> get() {
        return OPERATORS.values();
    }
}
