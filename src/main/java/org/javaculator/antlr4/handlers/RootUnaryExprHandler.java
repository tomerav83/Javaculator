package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;
import org.javaculator.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class RootUnaryExprHandler implements IVisitorExprHandler<CalcParser.RootUnaryExprContext, CalcParser.UnaryExprContext> {
    public static final RootUnaryExprHandler INSTANCE = new RootUnaryExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.RootUnaryExprContext ctx, Function<CalcParser.UnaryExprContext, BigDecimal> visitor) {
        if (ctx.unaryExpr() == null) {
            return Optional.empty();
        }

        BigDecimal lhs = visitor.apply(ctx.unaryExpr(0));

        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            BigDecimal rhs = visitor.apply(ctx.unaryExpr(i));
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
