package org.javaculator.antlr4.handlers;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IStatefulExprHandler;

import java.util.Map;
import java.util.Optional;

public class PostUnaryExprHandler implements IStatefulExprHandler<CalcParser.PostUnaryExprContext, Integer> {
    public static final PostUnaryExprHandler INSTANCE = new PostUnaryExprHandler();

    @Override
    public Optional<Integer> handle(CalcParser.PostUnaryExprContext ctx, Map<String, Integer> state) {
        if (ctx.ID() == null || ctx.getChildCount() != 2) {
            return Optional.empty();
        }

        String identifier = ctx.ID().getText();
        String op = ctx.getChild(1).getText();

        if (!state.containsKey(identifier) || state.get(identifier) == null) {
            return Optional.empty();
        }

        return switch (op) {
            case "--" -> Optional.ofNullable(state.put(identifier, state.get(identifier) - 1));
            case "++" -> Optional.ofNullable(state.put(identifier, state.get(identifier) + 1));
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }
}
