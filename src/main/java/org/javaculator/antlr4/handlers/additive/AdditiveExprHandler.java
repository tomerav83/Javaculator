package org.javaculator.antlr4.handlers.additive;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;
import org.javaculator.exceptions.UnknownOperatorException;
import org.javaculator.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class AdditiveExprHandler implements IVisitorExprHandler<CalcParser.AddSubExprContext, CalcParser.MultiplicativeContext> {
    public static final AdditiveExprHandler INSTANCE = new AdditiveExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.AddSubExprContext ctx, Function<CalcParser.MultiplicativeContext, BigDecimal> visitor) {
        if (ctx.multiplicative() == null) {
            return Optional.empty();
        }

        BigDecimal lhs = visitor.apply(ctx.multiplicative(0));

        for (int i = 1; i < ctx.multiplicative().size(); i++) {
            BigDecimal rhs = visitor.apply(ctx.multiplicative(i));
            String op = ctx.getChild(2 * i - 1).getText(); // Operator is at odd positions.

            lhs = switch (op) {
                case "+" -> BigDecimalSupport.add(lhs, rhs, false);
                case "-" -> BigDecimalSupport.sub(lhs, rhs, false);
                default -> throw new UnknownOperatorException(op);
            };
        }

        return Optional.ofNullable(lhs);
    }
}
