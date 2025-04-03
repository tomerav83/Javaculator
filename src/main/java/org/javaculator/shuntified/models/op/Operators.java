package org.javaculator.shuntified.models.op;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.models.Symbolizer;
import org.javaculator.shuntified.models.op.impl.AssignOp;
import org.javaculator.shuntified.models.op.impl.BinaryOp;
import org.javaculator.shuntified.models.op.impl.NegateOp;
import org.javaculator.shuntified.models.op.impl.VariableUnaryOp;

import java.util.List;
import java.util.stream.Stream;

public class Operators {
    private static final List<Op> OPERATORS = Stream.of(
                    new AssignOp("=", (key, value, cache) -> cache.putAndGetCurrent(key, value)),
                    new BinaryOp("+", 2, true, Double::sum),
                    new BinaryOp("-", 2, true, (l, r) -> l - r),
                    new BinaryOp("*", 3, true, (l, r) -> l * r),
                    new BinaryOp("/", 3, true, (l, r) -> l / r),
                    new BinaryOp("%", 3, true, (l, r) -> l % r),
                    new BinaryOp("^", 4, false, Math::pow),
                    new NegateOp(),
                    new VariableUnaryOp(VariableUnaryOp.INC_REPLACED_SYMBOL,
                            (key, value, cache) -> cache.increment(key, value)),
                    new VariableUnaryOp(VariableUnaryOp.DEC_REPLACED_SYMBOL,
                            (key, value, cache) -> cache.increment(key, value))
            )
            .toList();

    public static List<Op> get() {
        return OPERATORS;
    }
}
