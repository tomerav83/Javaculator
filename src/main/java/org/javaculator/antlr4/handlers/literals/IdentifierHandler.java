package org.javaculator.antlr4.handlers.literals;

import org.javaculator.antlr4.cache.RollbackCache;
import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;

import java.math.BigDecimal;

/**
 * Handler for identifier expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing identifier expressions
 * represented by {@code CalcParser.IdentifierContext}. It retrieves the corresponding {@link BigDecimal}
 * value for an identifier from a given snapshot of the variable state.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.IdentifierContext
 * @see BigDecimal
 */
public class IdentifierHandler implements IExprHandler<CalcParser.IdentifierContext, BigDecimal> {
    public static final IdentifierHandler INSTANCE = new IdentifierHandler();

    /**
     * Processes an identifier expression by retrieving its value from the snapshot.
     * <p>
     * The method extracts the identifier from the parse tree context and uses the helper method
     * {@code  IExprHandler#getFromSnapshot(ParserRuleContext, Snapshot)} to obtain the associated value.
     * </p>
     *
     * @param ctx           the identifier context from the parse tree
     * @param rollbackCache the snapshot representing the current variable state
     * @return an {@link  BigDecimal} containing the value associated with the identifier,
     * or null if the value is {@code null}
     */
    @Override
    public BigDecimal handle(CalcParser.IdentifierContext ctx, RollbackCache rollbackCache) {
        return getFromSnapshot(ctx, rollbackCache);
    }
}
