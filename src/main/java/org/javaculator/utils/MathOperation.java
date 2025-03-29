package org.javaculator.utils;

import org.javaculator.exceptions.InvalidCalculationException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum MathOperation {
    ADD(BigDecimal::add, "%s + %s", "%s += %s"),
    SUB(BigDecimal::subtract, "%s - %s", "%s -= %s"),
    MULTIPLY(BigDecimal::multiply, "%s * %s", "%s *= %s"),
    DIVIDE((BigDecimal lhs, BigDecimal rhs) -> lhs.divide(rhs, MathContext.DECIMAL128), "%s / %s", "%s /= %s"),
    MOD(MathOperation::calculateModulo, "%s % %s", "%s %= %s"),
    NEGATE((BigDecimal lhs, BigDecimal ignored) -> lhs.negate(), "negate(%s)", null),
    INC((BigDecimal lhs, BigDecimal ignored) -> lhs.add(BigDecimal.ONE),"inc(%s)", null),
    DEC((BigDecimal lhs, BigDecimal ignored) -> lhs.subtract(BigDecimal.ONE), "dec(%s)", null);

    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> transformation;
    private final String template;
    private final String augmented;

    MathOperation(BiFunction<BigDecimal, BigDecimal, BigDecimal> transformation,
                  String template,
                  String augmented) {
        this.transformation = transformation;
        this.template = template;
        this.augmented = augmented;
    }

    public BigDecimal wrapOperation(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        try {
            return transformation.apply(lhs, rhs);
        } catch (ArithmeticException | NullPointerException e) {
            throw new InvalidCalculationException(formatTemplate(lhs, rhs, isAugmented), e);
        }
    }

    private String formatTemplate(BigDecimal lhs, BigDecimal rhs, boolean isAugmented) {
        return isAugmented ? augmented.formatted(lhs, rhs) : template.formatted(lhs, rhs);
    }

    private static BigDecimal calculateModulo(BigDecimal lhs, BigDecimal rhs) {
        if (rhs.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Cannot calculate modulo with zero divisor");
        }

        int scale = Math.max(lhs.scale(), rhs.scale());

        BigDecimal quotient = lhs.divide(rhs, scale, RoundingMode.DOWN);
        BigDecimal multiply = quotient.multiply(rhs);

        return lhs.subtract(multiply);
    }
}
