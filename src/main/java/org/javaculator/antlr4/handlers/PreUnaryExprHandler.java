package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;

import java.util.Map;
import java.util.Optional;

public class PreUnaryExprHandler implements IStatefulExprHandler<CalcParser.PreUnaryExprContext, Integer> {
    public static final PreUnaryExprHandler INSTANCE = new PreUnaryExprHandler();

    @Override
    public Optional<Integer> handle(CalcParser.PreUnaryExprContext ctx, Map<String, Integer> state) {
        if (ctx.ID() == null || ctx.getChildCount() != 2) {
            return Optional.empty();
        }

        String identifier = ctx.ID().getText();
        String op = ctx.getChild(0).getText();

        if (!state.containsKey(identifier) || state.get(identifier) == null) {
            return Optional.empty();
        }

        return switch (op) {
            case "--" -> Optional.of(state.merge(identifier, -1, Integer::sum));
            case "++" -> Optional.of(state.merge(identifier, 1, Integer::sum));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
