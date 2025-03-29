package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Handler for singleton expressions (nested non assignable expressions) in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing singleton expressions
 * represented by {@link  CalcParser.SingletonExprContext}. It extracts the additive sub-expression
 * from the singleton context and applies a visitor function to compute its value.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.SingletonExprContext
 * @see CalcParser.AdditiveContext
 */
public class SingletonExprHandler implements IExprHandler<CalcParser.SingletonExprContext, CalcParser.AdditiveContext> {
    public static final SingletonExprHandler INSTANCE = new SingletonExprHandler();

    /**
     * Processes a singleton expression by extracting its additive sub-expression
     * and applying the provided visitor function to evaluate it.
     *
     * @param ctx     the singleton expression context from the parse tree
     * @param visitor a function to evaluate the contained {@link  CalcParser.AdditiveContext},
     *                returning a {@code BigDecimal} result
     * @return an {@link BigDecimal} containing the evaluated result,
     *         or {@code Optional.empty()} if the additive sub-expression is null
     */
    @Override
    public BigDecimal handle(CalcParser.SingletonExprContext ctx, Function<CalcParser.AdditiveContext, BigDecimal> visitor) {
        return Optional.ofNullable(ctx.additive()).map(visitor).orElse(null);
    }
}
