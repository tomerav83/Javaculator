package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;
import org.javaculator.antlr4.cache.RollbackCache;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.utils.BigDecimalSupport;

import java.math.BigDecimal;

/**
 * Handler for post-increment and post-decrement expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing post-increment (++) and
 * post-decrement (--) expressions represented by {@code CalcParser.PostIncDecExprContext}. It retrieves
 * the current value associated with the identifier from the provided snapshot, applies the appropriate
 * arithmetic operation using {@link BigDecimalSupport}, and updates the snapshot. The handler returns
 * the value prior to the increment or decrement operation, following typical post-increment/decrement semantics.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.PostIncDecExprContext
 * @see BigDecimal
 * @see RollbackCache
 * @see UnknownOperatorException
 */
public class PostIncDecHandler implements IExprHandler<CalcParser.PostIncDecExprContext, BigDecimal> {
    public static final PostIncDecHandler INSTANCE = new PostIncDecHandler();

    /**
     * Processes a post-increment or post-decrement expression.
     * <p>
     * The method retrieves the current value of the identifier from the snapshot and determines the operator
     * from the context. It then applies the increment or decrement operation using {@link BigDecimalSupport} and
     * updates the snapshot with the new value. The method returns the original value (before the update),
     * in accordance with post-increment/decrement behavior.
     * </p>
     *
     * @param ctx      the post-increment/decrement expression context from the parse tree
     * @param rollbackCache the snapshot representing the current state of variable values
     * @return an {@link BigDecimal} containing the value prior to applying the increment/decrement,
     *         or null if the value was null
     * @throws UnknownOperatorException if an operator other than "++" or "--" is encountered
     */
    @Override
    public BigDecimal handle(CalcParser.PostIncDecExprContext ctx, RollbackCache rollbackCache) {
        BigDecimal current = getFromSnapshot(ctx, rollbackCache);
        String op = ctx.getChild(1).getText();

        return switch (op) {
            case "--" -> putAndGetPrevious(ctx, rollbackCache, BigDecimalSupport.dec(current));
            case "++" -> putAndGetPrevious(ctx, rollbackCache, BigDecimalSupport.inc(current));
            default -> throw new UnknownOperatorException(op);
        };
    }
}

