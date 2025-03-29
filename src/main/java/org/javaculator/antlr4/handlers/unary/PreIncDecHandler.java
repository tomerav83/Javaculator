package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;
import org.javaculator.antlr4.cache.RollbackCache;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Handler for pre-increment and pre-decrement expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing pre-increment (++)
 * and pre-decrement (--) expressions represented by {@code CalcParser.PreIncDecExprContext}.
 * It retrieves the current value associated with the identifier from the snapshot,
 * applies the corresponding arithmetic operation using {@link BigDecimalSupport},
 * updates the snapshot with the new value, and returns the updated value.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.PreIncDecExprContext
 * @see BigDecimal
 * @see RollbackCache
 * @see UnknownOperatorException
 */
public class PreIncDecHandler implements IExprHandler<CalcParser.PreIncDecExprContext, BigDecimal> {
    public static final PreIncDecHandler INSTANCE = new PreIncDecHandler();

    /**
     * Processes a pre-increment or pre-decrement expression.
     * <p>
     * The method retrieves the current value of the identifier from the snapshot using the helper
     * method {@code  #getFromSnapshot(ParserRuleContext, Snapshot)}. It then determines the operator
     * from the parse tree and applies the appropriate arithmetic operation via {@link BigDecimalSupport}.
     * The snapshot is updated with the new value, which is returned as the result.
     * </p>
     *
     * @param ctx      the pre-increment/decrement expression context from the parse tree
     * @param rollbackCache the snapshot representing the current state of variable assignments
     * @return an {@link BigDecimal} containing the updated value after applying the operation,
     *         or null if the value is {@code null}
     * @throws UnknownOperatorException if an unsupported operator is encountered
     */
    @Override
    public BigDecimal handle(CalcParser.PreIncDecExprContext ctx, RollbackCache rollbackCache) {
        BigDecimal value = getFromSnapshot(ctx, rollbackCache);
        String op = ctx.getChild(0).getText();

        return switch (op) {
            case "--" -> putAndGetCurrent(ctx, rollbackCache, BigDecimalSupport.dec(value));
            case "++" -> putAndGetCurrent(ctx, rollbackCache, BigDecimalSupport.inc(value));
            default -> throw new UnknownOperatorException(op);
        };
    }
}

