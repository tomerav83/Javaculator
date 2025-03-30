package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.cache.RollbackCache;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;
import org.javaculator.antlr4.utils.BigDecimalSupport;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Handler for augmented assignment (+= | -= | *= | /= | %=) expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing augmented assignment
 * expressions represented by {@link  CalcParser.AugmentedAssignExprContext}. It evaluates the expression by:
 * <ul>
 *   <li>Retrieving the left-hand side value from the current snapshot.</li>
 *   <li>Determining the augmented operator (such as +=, -=, etc.) from the parse tree.</li>
 *   <li>Applying the corresponding arithmetic operation between the left-hand side and the evaluated right-hand side expression.</li>
 *   <li>Storing the resulting value back into the snapshot.</li>
 * </ul>
 * If an unknown operator is encountered, it throws an {@link UnknownOperatorException}.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.AugmentedAssignExprContext
 * @see CalcParser.ExpressionContext
 * @see UnknownOperatorException
 */
public class AugmentedAssignExprHandler implements IExprHandler<CalcParser.AugmentedAssignExprContext, CalcParser.ExpressionContext> {
    public static final AugmentedAssignExprHandler INSTANCE = new AugmentedAssignExprHandler();

    /**
     * Processes an augmented assignment expression.
     * <p>
     * The method retrieves the current value of the left-hand side from the snapshot, then
     * applies the arithmetic operation specified by the operator (e.g., "+=", "-=", "*=", "/=", "%=")
     * using the result of evaluating the right-hand side expression via the provided visitor function.
     * Finally, it updates the snapshot with the new computed value.
     * </p>
     *
     * @param ctx           the augmented assignment expression context from the parse tree
     * @param rollbackCache the snapshot representing the current state of variable assignments
     * @param visitor       a function to evaluate the right-hand side expression, returning a {@code BigDecimal}
     * @return an {@link  BigDecimal} containing the updated value after applying the operation, or empty if the result is null
     * @throws UnknownOperatorException if an unsupported operator is encountered
     */
    @Override
    public BigDecimal handle(CalcParser.AugmentedAssignExprContext ctx,
                             RollbackCache rollbackCache,
                             Function<CalcParser.ExpressionContext, BigDecimal> visitor) {
        BigDecimal lhs = getFromSnapshot(ctx, rollbackCache);
        String op = ParserCtxUtils.getChild(ctx, 1);

        BigDecimal value = switch (op) {
            case "+=" -> BigDecimalSupport.add(lhs, visitor.apply(ctx.expression()));
            case "-=" -> BigDecimalSupport.sub(lhs, visitor.apply(ctx.expression()));
            case "*=" -> BigDecimalSupport.multiply(lhs, visitor.apply(ctx.expression()));
            case "/=" -> BigDecimalSupport.div(lhs, visitor.apply(ctx.expression()));
            case "^=" -> BigDecimalSupport.pow(lhs, visitor.apply(ctx.expression()));
            case "%=" -> BigDecimalSupport.mod(lhs, visitor.apply(ctx.expression()));
            default -> throw new UnknownOperatorException(op);
        };

        return putAndGetCurrent(ctx, rollbackCache, value);
    }
}

