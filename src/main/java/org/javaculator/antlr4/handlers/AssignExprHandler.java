package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulVisitorExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class AssignExprHandler implements IStatefulVisitorExprHandler<CalcParser.AssignExprContext, CalcParser.ExpressionContext> {
    public static final AssignExprHandler INSTANCE = new AssignExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.AssignExprContext ctx, Snapshot snapshot, Function<CalcParser.ExpressionContext, BigDecimal> visitor) {
        if (ctx.ID() == null) {
            return Optional.empty();
        }

        String identifier = ctx.ID().getText();
        String op = ctx.getChild(1).getText();

        BigDecimal lhs = snapshot.get(identifier);
        BigDecimal rhs = visitor.apply(ctx.expression());
        BigDecimal value = Objects.equals(op, "=") ? rhs : handleAugmentedAssign(lhs, rhs, op);

        return Optional.ofNullable(snapshot.putAndGetCurrent(identifier, value));
    }

    private BigDecimal handleAugmentedAssign(BigDecimal lhs, BigDecimal rhs, String op) {
        if (lhs == null) {
            return BigDecimal.ZERO;
        }

        return switch (op) {
            case "+=" -> lhs.add(rhs);
            case "-=" -> lhs.subtract(rhs);
            case "*=" -> lhs.multiply(rhs);
            case "/=" -> lhs.divide(rhs, MathContext.DECIMAL128);
            case "%=" -> calculateModulo(lhs, rhs);
            default -> throw new RuntimeException("Unknown operator: " + op);
        };
    }

    public static BigDecimal calculateModulo(BigDecimal dividend, BigDecimal divisor) {
        // Ensure non-zero divisor
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Cannot calculate modulo with zero divisor");
        }

        // Determine the scale for precision
        int scale = Math.max(dividend.scale(), divisor.scale());

        // Perform division and get remainder
        BigDecimal quotient = dividend.divide(divisor, scale, RoundingMode.DOWN);
        BigDecimal multiplicationResult = quotient.multiply(divisor);

        // Calculate remainder
        return dividend.subtract(multiplicationResult);
    }
}
