package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class SignedUnaryExprHandler implements IVisitorExprHandler<CalcParser.SignedUnaryExprContext, CalcParser.UnaryExprContext> {
    public static final SignedUnaryExprHandler INSTANCE = new SignedUnaryExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.SignedUnaryExprContext ctx, Function<CalcParser.UnaryExprContext, BigDecimal> visitor) {
        if (ctx.unaryExpr() == null) {
            return Optional.empty();
        }

        String op = ctx.getChild(0).getText();

        return switch (op) {
            case "-" -> Optional.of(visitor.apply(ctx.unaryExpr()).negate());
            case "+" -> Optional.of(visitor.apply(ctx.unaryExpr()));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
