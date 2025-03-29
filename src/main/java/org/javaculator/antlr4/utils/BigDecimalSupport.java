package org.javaculator.antlr4.utils;

import java.math.BigDecimal;

public class BigDecimalSupport {
    public static BigDecimal add(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return BigDecimalOperator.ADD.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal sub(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return BigDecimalOperator.SUB.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal multiply(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return BigDecimalOperator.MULTIPLY.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal div(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return BigDecimalOperator.DIVIDE.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal mod(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return BigDecimalOperator.MOD.wrapOperation(lhs, rhs, isAugmented);
    }

    public static BigDecimal inc(BigDecimal bd) {
        return BigDecimalOperator.INC.wrapOperation(bd, null, false);
    }

    public static BigDecimal dec(BigDecimal bd) {
        return BigDecimalOperator.DEC.wrapOperation(bd, null, false);
    }

    public static BigDecimal negate(BigDecimal bd) {
        return BigDecimalOperator.NEGATE.wrapOperation(bd, null, false);
    }
}
