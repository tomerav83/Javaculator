package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.gen.CalcParser.ParenExprContext;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Handler for parenthesized expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing parenthesized expressions
 * represented by {@code ParenExprContext}. It extracts the inner expression from the parentheses and
 * applies a visitor function to evaluate it into a {@link BigDecimal}.
 * </p>
 *
 * @see IExprHandler
 * @see ParenExprContext
 * @see CalcParser.ExpressionContext
 * @see BigDecimal
 */
public class ParenExprHandler implements IExprHandler<ParenExprContext, CalcParser.ExpressionContext> {
    public static final ParenExprHandler INSTANCE = new ParenExprHandler();

    /**
     * Processes a parenthesized expression by extracting the inner expression and applying the provided visitor.
     *
     * @param ctx     the parenthesized expression context from the parse tree
     * @param visitor a function to evaluate the contained {@code CalcParser.ExpressionContext} into a {@link BigDecimal}
     * @return an {@link BigDecimal} containing the evaluated result of the inner expression,
     *         or null if the expression is null
     */
    @Override
    public BigDecimal handle(ParenExprContext ctx, Function<CalcParser.ExpressionContext, BigDecimal> visitor) {
        return Optional.ofNullable(ctx.expression()).map(visitor).orElse(null);
    }
}

