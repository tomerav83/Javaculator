package org.javaculator.antlr4.handlers.literals;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;
import java.util.Optional;

public class IntegerHandler implements IExprHandler<CalcParser.IntegerContext, BigDecimal> {
    public static final IntegerHandler INSTANCE = new IntegerHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.IntegerContext ctx) {
        return Optional.ofNullable(ctx.INT()).map(TerminalNode::getText).map(BigDecimal::new);
    }
}
