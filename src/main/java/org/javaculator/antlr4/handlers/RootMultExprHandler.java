package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.util.Optional;
import java.util.function.Function;

public class RootMultExprHandler implements IVisitorExprHandler<CalcParser.RootMultExprContext, CalcParser.MultiplicativeExprContext> {
    public static final RootMultExprHandler INSTANCE = new RootMultExprHandler();

    @Override
    public Optional<Integer> handle(CalcParser.RootMultExprContext ctx, Function<CalcParser.MultiplicativeExprContext, Integer> visitor) {
        if (ctx.multiplicativeExpr() == null) {
            return Optional.empty();
        }

        Integer result = visitor.apply(ctx.multiplicativeExpr(0));

        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            Integer rhs = visitor.apply(ctx.multiplicativeExpr(i));
            String op = ctx.getChild(2 * i - 1).getText(); // Operator is at odd positions.

            result = switch (op) {
                case "+" -> result + rhs;
                case "-" -> result - rhs;
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        return Optional.ofNullable(result);
    }
}
