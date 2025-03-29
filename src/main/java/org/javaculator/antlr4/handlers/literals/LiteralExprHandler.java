package org.javaculator.antlr4.handlers.literals;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Handler for literal expression nodes in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing literal expression nodes
 * represented by {@code CalcParser.LiteralsExprContext}. It extracts the inner literal context and
 * applies a visitor function to compute a {@link BigDecimal} value.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.LiteralsExprContext
 * @see CalcParser.LiteralsContext
 * @see BigDecimal
 */
public class LiteralExprHandler implements IExprHandler<CalcParser.LiteralsExprContext, CalcParser.LiteralsContext> {
    public static final LiteralExprHandler INSTANCE = new LiteralExprHandler();

    /**
     * Processes a literal expression by extracting its literal context and applying the provided visitor function.
     *
     * @param ctx     the literal expression context from the parse tree
     * @param visitor a function that converts the extracted {@code CalcParser.LiteralsContext} into a {@code BigDecimal}
     * @return an {@link BigDecimal} containing the computed numeric value, or null if the literal is null
     */
    @Override
    public BigDecimal handle(CalcParser.LiteralsExprContext ctx, Function<CalcParser.LiteralsContext, BigDecimal> visitor) {
        return Optional.ofNullable(ctx.literals()).map(visitor).orElse(null);
    }
}

