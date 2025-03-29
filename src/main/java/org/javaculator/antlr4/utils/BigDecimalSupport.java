package org.javaculator.antlr4.utils;

import org.javaculator.antlr4.exceptions.impl.InvalidCalculationException;

import java.math.BigDecimal;

/**
 * Utility class providing arithmetic operations on {@link BigDecimal} values.
 * <p>
 * This class leverages the {@link BigDecimalOperator} enumeration to perform various arithmetic operations,
 * including addition, subtraction, multiplication, division, modulus, increment, decrement, and negation.
 * Each method delegates the operation to the corresponding operator and wraps any arithmetic errors in an
 * {@link InvalidCalculationException}.
 * </p>
 *
 * @see BigDecimal
 * @see BigDecimalOperator
 * @see InvalidCalculationException
 */
public class BigDecimalSupport {

    /**
     * Adds two {@link BigDecimal} values.
     *
     * @param lhs the left-hand side operand
     * @param rhs the right-hand side operand
     * @return the sum of {@code lhs} and {@code rhs}
     * @throws InvalidCalculationException if the addition fails due to arithmetic or null issues
     */
    public static BigDecimal add(BigDecimal lhs, BigDecimal rhs) {
        return BigDecimalOperator.ADD.wrapOperation(lhs, rhs);
    }

    /**
     * Subtracts the right-hand side {@link BigDecimal} from the left-hand side.
     *
     * @param lhs the left-hand side operand
     * @param rhs the right-hand side operand to subtract
     * @return the difference of {@code lhs} minus {@code rhs}
     * @throws InvalidCalculationException if the subtraction fails due to arithmetic or null issues
     */
    public static BigDecimal sub(BigDecimal lhs, BigDecimal rhs) {
        return BigDecimalOperator.SUB.wrapOperation(lhs, rhs);
    }

    /**
     * Multiplies two {@link BigDecimal} values.
     *
     * @param lhs the left-hand side operand
     * @param rhs the right-hand side operand
     * @return the product of {@code lhs} and {@code rhs}
     * @throws InvalidCalculationException if the multiplication fails due to arithmetic or null issues
     */
    public static BigDecimal multiply(BigDecimal lhs, BigDecimal rhs) {
        return BigDecimalOperator.MULTIPLY.wrapOperation(lhs, rhs);
    }

    /**
     * Divides the left-hand side {@link BigDecimal} by the right-hand side.
     *
     * @param lhs the dividend
     * @param rhs the divisor
     * @return the quotient of {@code lhs} divided by {@code rhs}
     * @throws InvalidCalculationException if the division fails due to arithmetic or null issues
     */
    public static BigDecimal div(BigDecimal lhs, BigDecimal rhs) {
        return BigDecimalOperator.DIVIDE.wrapOperation(lhs, rhs);
    }

    /**
     * Computes the modulus (remainder) of the left-hand side {@link BigDecimal} divided by the right-hand side.
     *
     * @param lhs the dividend
     * @param rhs the divisor
     * @return the remainder after dividing {@code lhs} by {@code rhs}
     * @throws InvalidCalculationException if the modulus operation fails due to arithmetic or null issues
     */
    public static BigDecimal mod(BigDecimal lhs, BigDecimal rhs) {
        return BigDecimalOperator.MOD.wrapOperation(lhs, rhs);
    }

    /**
     * Increments the given {@link BigDecimal} value by one.
     *
     * @param bd the value to increment
     * @return the incremented value of {@code bd}
     * @throws InvalidCalculationException if the increment operation fails due to arithmetic or null issues
     */
    public static BigDecimal inc(BigDecimal bd) {
        return BigDecimalOperator.INC.wrapOperation(bd, null);
    }

    /**
     * Decrements the given {@link BigDecimal} value by one.
     *
     * @param bd the value to decrement
     * @return the decremented value of {@code bd}
     * @throws InvalidCalculationException if the decrement operation fails due to arithmetic or null issues
     */
    public static BigDecimal dec(BigDecimal bd) {
        return BigDecimalOperator.DEC.wrapOperation(bd, null);
    }

    /**
     * Negates the given {@link BigDecimal} value.
     *
     * @param bd the value to negate
     * @return the negated value of {@code bd}
     * @throws InvalidCalculationException if the negation operation fails due to arithmetic or null issues
     */
    public static BigDecimal negate(BigDecimal bd) {
        return BigDecimalOperator.NEGATE.wrapOperation(bd, null);
    }
}

