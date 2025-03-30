package org.javaculator.antlr4.utils;

import org.javaculator.antlr4.exceptions.impl.InvalidCalculationException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.BiFunction;

/**
 * An enumeration of arithmetic operations on {@link BigDecimal} values.
 * <p>
 * Each enum constant defines an operation along with a corresponding error message template.
 * The transformation for each operator is represented by a {@link BiFunction} that accepts two
 * {@link BigDecimal} operands and returns a {@link BigDecimal} result.
 * </p>
 *
 * @see BigDecimal
 * @see BiFunction
 */
public enum BigDecimalOperator {
    ADD(BigDecimal::add, "%s + %s"),
    SUB(BigDecimal::subtract, "%s - %s"),
    MULTIPLY(BigDecimal::multiply, "%s * %s"),
    DIVIDE(BigDecimalOperator::div, "%s / %s"),
    POW(BigDecimalOperator::pow, "%s ^ %s"),
    MOD(BigDecimalOperator::mod, "%s modulu %s"),
    NEGATE(BigDecimalOperator::negate, "negate(%s)"),
    INC(BigDecimalOperator::inc, "inc(%s)"),
    DEC(BigDecimalOperator::dec, "dec(%s)");

    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> transformation;
    private final String template;

    /**
     * Constructs a new {@code BigDecimalOperator} with the specified transformation and error template.
     *
     * @param transformation the {@link BiFunction} that performs the arithmetic operation
     * @param template       the template for formatting error messages in case of calculation errors
     */
    BigDecimalOperator(BiFunction<BigDecimal, BigDecimal, BigDecimal> transformation, String template) {
        this.transformation = transformation;
        this.template = template;
    }

    /**
     * Applies the operator's transformation to the given operands.
     * <p>
     * If the operation throws an {@link ArithmeticException} or {@link NullPointerException},
     * the exception is caught and wrapped in an {@link InvalidCalculationException} with a formatted error message.
     * </p>
     *
     * @param lhs the left-hand side operand
     * @param rhs the right-hand side operand
     * @return the result of applying the operation to the operands
     * @throws InvalidCalculationException if the operation fails due to arithmetic errors or null values
     */
    public BigDecimal wrapOperation(BigDecimal lhs, BigDecimal rhs) {
        try {
            return transformation.apply(lhs, rhs);
        } catch (ArithmeticException | NullPointerException e) {
            throw new InvalidCalculationException(template.formatted(lhs, rhs), e);
        }
    }

    private static BigDecimal negate(BigDecimal lhs, BigDecimal ignored) {
        return lhs.negate();
    }

    private static BigDecimal inc(BigDecimal lhs, BigDecimal ignored) {
        return lhs.add(BigDecimal.ONE);
    }

    private static BigDecimal dec(BigDecimal lhs, BigDecimal ignored) {
        return lhs.subtract(BigDecimal.ONE);
    }

    private static BigDecimal div(BigDecimal lhs, BigDecimal rhs) {
        return lhs.divide(rhs, MathContext.DECIMAL128);
    }

    private static BigDecimal pow(BigDecimal lhs, BigDecimal rhs) {
        return lhs.pow(rhs.intValueExact(), MathContext.DECIMAL128);
    }

    private static BigDecimal mod(BigDecimal lhs, BigDecimal rhs) {
        if (rhs.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Cannot calculate modulo with zero divisor");
        }

        int scale = Math.max(lhs.scale(), rhs.scale());
        BigDecimal quotient = lhs.divide(rhs, scale, RoundingMode.DOWN);
        BigDecimal multiply = quotient.multiply(rhs);
        return lhs.subtract(multiply);
    }
}
