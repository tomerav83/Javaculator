package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;
import org.javaculator.exceptions.UnknownOperatorException;
import org.javaculator.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class SignedUnaryExprHandler implements IVisitorExprHandler<CalcParser.SignedExprContext, CalcParser.UnaryContext> {
    public static final SignedUnaryExprHandler INSTANCE = new SignedUnaryExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.SignedExprContext ctx, Function<CalcParser.UnaryContext, BigDecimal> visitor) {
        if (ctx.unary() == null) {
            return Optional.empty();
        }

        BigDecimal current = visitor.apply(ctx.unary());
        String op = ctx.getChild(0).getText();

        return switch (op) {
            case "-" -> Optional.of(BigDecimalSupport.negate(current));
            case "+" -> Optional.of(current);
            default -> throw new UnknownOperatorException(op);
        };
    }
}
