package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.exceptions.MissingOrNullIdentifierException;
import org.javaculator.antlr4.handlers.interfaces.IStatefulVisitorExprHandler;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class AssignExprHandler implements IStatefulVisitorExprHandler<CalcParser.AssignExprContext, CalcParser.ExpressionContext> {
    public static final AssignExprHandler INSTANCE = new AssignExprHandler();

    @Override
    public Optional<Integer> handle(CalcParser.AssignExprContext ctx, Map<String, Integer> state, Function<CalcParser.ExpressionContext, Integer> visitor) {
        if (ctx.ID() == null) {
            return Optional.empty();
        }

        String identifier = ctx.ID().getText();
        String op = ctx.getChild(1).getText();

        Integer lhs = state.get(identifier);
        Integer rhs = visitor.apply(ctx.expression());
        Integer value = Objects.equals(op, "=") ? rhs : handleAugmentedAssign(lhs, rhs, op);

        state.put(identifier, value);
        return Optional.ofNullable(value);
    }

    private Integer handleAugmentedAssign(Integer lhs, Integer rhs, String op) {
        if (lhs == null) {
            return 0;
        }

        return switch (op) {
            case "+=" -> lhs + rhs;
            case "-=" -> lhs - rhs;
            case "*=" -> lhs * rhs;
            case "/=" -> lhs / rhs;
            case "%=" -> lhs % rhs;
            default -> throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
