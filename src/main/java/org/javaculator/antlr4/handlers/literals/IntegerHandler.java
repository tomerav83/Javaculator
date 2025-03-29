package org.javaculator.antlr4.handlers.literals;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Handler for integer literal expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing integer literal
 * expressions represented by {@code CalcParser.IntegerContext}. It extracts the integer literal
 * token from the parse tree and converts its text representation into a {@link BigDecimal}.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.IntegerContext
 * @see BigDecimal
 */
public class IntegerHandler implements IExprHandler<CalcParser.IntegerContext, BigDecimal> {
    public static final IntegerHandler INSTANCE = new IntegerHandler();

    /**
     * Processes an integer literal expression by converting the token text into a {@link BigDecimal}.
     *
     * @param ctx the integer context from the parse tree
     * @return an {@link BigDecimal} containing the numeric value represented by the integer literal,
     *         or null if the literal is null
     */
    @Override
    public BigDecimal handle(CalcParser.IntegerContext ctx) {
        return Optional.ofNullable(ctx.INT_LITERAL())
                .map(TerminalNode::getText)
                .map(BigDecimal::new)
                .orElse(null);
    }
}

