package org.javaculator.antlr4.handlers.literals;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;
import java.util.Optional;

public class FloatingPointHandler implements IExprHandler<CalcParser.FloatingPointContext, BigDecimal> {
    public static final FloatingPointHandler INSTANCE = new FloatingPointHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.FloatingPointContext ctx) {
        return Optional.ofNullable(ctx.FLOAT_LITERAL()).map(TerminalNode::getText).map(BigDecimal::new);
    }
}
