package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.util.Optional;
import java.util.function.Function;

public class RootUnaryExprHandler implements IVisitorExprHandler<CalcParser.RootUnaryExprContext, CalcParser.UnaryExprContext> {
    public static final RootUnaryExprHandler INSTANCE = new RootUnaryExprHandler();

    @Override
    public Optional<Integer> handle(CalcParser.RootUnaryExprContext ctx, Function<CalcParser.UnaryExprContext, Integer> visitor) {
        if (ctx.unaryExpr() == null) {
            return Optional.empty();
        }

        Integer result = visitor.apply(ctx.unaryExpr(0));

        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            Integer rhs = visitor.apply(ctx.unaryExpr(i));
            String op = ctx.getChild(2 * i - 1).getText();


            result = switch (op) {
                case "*" -> result * rhs;
                case "/" -> result / rhs;
                case "%" -> result % rhs;
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        return Optional.ofNullable(result);
    }
}
