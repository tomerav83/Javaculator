package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;
import org.javaculator.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class MultiplicativeExprHandler implements IVisitorExprHandler<CalcParser.MulDivModExprContext, CalcParser.UnaryContext> {
    public static final MultiplicativeExprHandler INSTANCE = new MultiplicativeExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.MulDivModExprContext ctx, Function<CalcParser.UnaryContext, BigDecimal> visitor) {
        if (ctx.unary() == null) {
            return Optional.empty();
        }

        BigDecimal lhs = visitor.apply(ctx.unary(0));

        for (int i = 1; i < ctx.unary().size(); i++) {
            BigDecimal rhs = visitor.apply(ctx.unary(i));
            String op = ctx.getChild(2 * i - 1).getText();


            lhs = switch (op) {
                case "*" -> BigDecimalSupport.multiply(lhs, rhs, false);
                case "/" -> BigDecimalSupport.div(lhs, rhs, false);
                case "%" -> BigDecimalSupport.mod(lhs, rhs, false);
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        return Optional.ofNullable(lhs);
    }
}
