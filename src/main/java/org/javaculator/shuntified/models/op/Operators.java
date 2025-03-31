package org.javaculator.shuntified.models.op;

import org.javaculator.shuntified.models.op.impl.BinaryOp;
import org.javaculator.shuntified.models.op.impl.UnaryOp;

import java.util.List;

public class Operators {
    private static final List<Op> OPERATORS = List.of(
            new BinaryOp("+", 2, true, Double::sum),
            new BinaryOp("-", 2, true, (l, r) -> l - r),
            new BinaryOp("*", 3, true, (l, r) -> l * r),
            new BinaryOp("/", 3, true, (l, r) -> l / r),
            new BinaryOp("%", 3, true, (l, r) -> l % r),
            new BinaryOp("^", 4, false, Math::pow),
            new UnaryOp("-", (l) -> -l));

    public static List<Op> get() {
        return OPERATORS;
    }
}
