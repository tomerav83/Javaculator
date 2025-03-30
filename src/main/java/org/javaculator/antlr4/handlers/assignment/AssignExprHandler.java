package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.cache.RollbackCache;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

/**
 * Handler for assignment (=) expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for handling assignment expressions
 * represented by {@link  CalcParser.AssignExprContext}. It processes an assignment by validating that
 * the operator is the simple assignment operator ("=") and then evaluating the right-hand side expression
 * using the provided visitor function. The evaluated value is stored (via a helper method) in the given snapshot
 * of the calculator's state and returned.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.AssignExprContext
 * @see CalcParser.ExpressionContext
 * @see UnknownOperatorException
 */
public class AssignExprHandler implements IExprHandler<CalcParser.AssignExprContext, CalcParser.ExpressionContext> {
    public static final AssignExprHandler INSTANCE = new AssignExprHandler();

    /**
     * Processes an assignment expression by first ensuring that the operator is "=" and then
     * evaluating the expression on the right-hand side.
     * <p>
     * The operator is retrieved from the second child of the context. If it is not "=", an
     * {@link UnknownOperatorException} is thrown. Otherwise, the visitor is applied to the expression,
     * and the resulting value is stored in the snapshot using a helper method
     * </p>
     *
     * @param ctx           the assignment expression context from the parse tree
     * @param rollbackCache the current snapshot of variable states
     * @param visitor       a function to evaluate the right-hand side expression, returning a {@code BigDecimal}
     * @return an {@link  BigDecimal} containing the result of the assignment, or empty if null
     * @throws UnknownOperatorException if the operator is not "="
     */
    @Override
    public BigDecimal handle(CalcParser.AssignExprContext ctx,
                             RollbackCache rollbackCache,
                             Function<CalcParser.ExpressionContext, BigDecimal> visitor) {
        String op = ParserCtxUtils.getChild(ctx, 1);

        if (!Objects.equals(op, "=")) {
            throw new UnknownOperatorException(op);
        }

        BigDecimal value = visitor.apply(ctx.expression());

        return putAndGetCurrent(ctx, rollbackCache, value);
    }
}
