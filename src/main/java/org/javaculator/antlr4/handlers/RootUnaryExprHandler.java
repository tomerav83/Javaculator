package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.Function;

public class RootUnaryExprHandler implements IVisitorExprHandler<CalcParser.RootUnaryExprContext, CalcParser.UnaryExprContext> {
    public static final RootUnaryExprHandler INSTANCE = new RootUnaryExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.RootUnaryExprContext ctx, Function<CalcParser.UnaryExprContext, BigDecimal> visitor) {
        if (ctx.unaryExpr() == null) {
            return Optional.empty();
        }

        BigDecimal result = visitor.apply(ctx.unaryExpr(0));

        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            BigDecimal rhs = visitor.apply(ctx.unaryExpr(i));
            String op = ctx.getChild(2 * i - 1).getText();


            result = switch (op) {
                case "*" -> result.multiply(rhs);
                case "/" -> result.divide(rhs, MathContext.DECIMAL128);
                case "%" -> calculateModulo(result, rhs);
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        return Optional.ofNullable(result);
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
