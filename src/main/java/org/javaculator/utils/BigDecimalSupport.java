package org.javaculator.utils;

import java.math.BigDecimal;

public class BigDecimalSupport {
    public static BigDecimal add(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return MathOperation.ADD.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal sub(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return MathOperation.SUB.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal multiply(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return MathOperation.MULTIPLY.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal div(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return MathOperation.DIVIDE.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal mod(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return MathOperation.MOD.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal inc(BigDecimal bd) {
        return MathOperation.INC.wrapOperation(bd, null, false);
    }

    public static BigDecimal dec(BigDecimal bd) {
        return MathOperation.DEC.wrapOperation(bd, null, false);
    }

    public static BigDecimal negate(BigDecimal bd) {
        return MathOperation.NEGATE.wrapOperation(bd, null, false);
    }
}
