package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulVisitorExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.utils.BigDecimalSupport;

import java.math.BigDecimal;
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

        BigDecimal value = switch (op) {
            case "=" -> rhs;
            case "+=" -> BigDecimalSupport.add(lhs, rhs, true);
            case "-=" -> BigDecimalSupport.sub(lhs, rhs, true);
            case "*=" -> BigDecimalSupport.multiply(lhs, rhs, true);
            case "/=" -> BigDecimalSupport.div(lhs, rhs, true);
            case "%=" -> BigDecimalSupport.mod(lhs, rhs, true);
            default -> throw new RuntimeException("Unknown operator: " + op);
        };

        return Optional.ofNullable(snapshot.putAndGetCurrent(identifier, value));
    }
}
