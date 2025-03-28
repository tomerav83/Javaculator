package org.javaculator.antlr4.handlers;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.util.Optional;

public class LiteralExprHandler implements IExprHandler<CalcParser.LiteralExprContext, Integer> {
    public static final LiteralExprHandler INSTANCE = new LiteralExprHandler();

    @Override
    public Optional<Integer> handle(CalcParser.LiteralExprContext ctx) {
        return Optional.ofNullable(ctx.INT()).map(TerminalNode::getText).map(Integer::valueOf);
    }
}
