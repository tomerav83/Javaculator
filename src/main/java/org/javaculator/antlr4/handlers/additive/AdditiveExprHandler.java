package org.javaculator.antlr4.handlers.additive;

import org.javaculator.antlr4.handlers.interfaces.IVisitorExprHandler;
import org.javaculator.antlr4.exceptions.UnknownOperatorException;
import org.javaculator.antlr4.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static org.javaculator.antlr4.CalcParser.*;

public class AdditiveExprHandler implements IVisitorExprHandler<AddSubExprContext, MultiplicativeContext> {
    public static final AdditiveExprHandler INSTANCE = new AdditiveExprHandler();

    @Override
    public Optional<BigDecimal> handle(AddSubExprContext ctx, Function<MultiplicativeContext, BigDecimal> visitor) {
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
