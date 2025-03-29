package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulVisitorExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.utils.BigDecimalSupport;

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
        return switch (op) {
            case "+=" -> BigDecimalSupport.add(lhs, rhs, true);
            case "-=" -> BigDecimalSupport.sub(lhs, rhs, true);
            case "*=" -> BigDecimalSupport.multiply(lhs, rhs, true);
            case "/=" -> BigDecimalSupport.div(lhs, rhs, true);
            case "%=" -> BigDecimalSupport.mod(lhs, rhs, true);
            default -> throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
