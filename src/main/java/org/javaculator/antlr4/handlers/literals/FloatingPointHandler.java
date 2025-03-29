package org.javaculator.antlr4.handlers.literals;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Handler for floating point literal expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing floating point literal
 * expressions represented by {@code CalcParser.FloatingPointContext}. It extracts the floating point
 * literal from the parse tree and converts it into a {@link BigDecimal} value.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.FloatingPointContext
 * @see BigDecimal
 */
public class FloatingPointHandler implements IExprHandler<CalcParser.FloatingPointContext, BigDecimal> {
    public static final FloatingPointHandler INSTANCE = new FloatingPointHandler();

    /**
     * Processes a floating point literal expression by converting the token text into a {@link BigDecimal}.
     *
     * @param ctx the floating point context from the parse tree
     * @return an {@link  BigDecimal} containing the numeric value represented by the floating point literal,
     * or null if the literal is null
     */
    @Override
    public BigDecimal handle(CalcParser.FloatingPointContext ctx) {
        return Optional.ofNullable(ctx.FLOAT_LITERAL())
                .map(TerminalNode::getText)
                .map(BigDecimal::new)
                .orElse(null);
    }
}
