package org.javaculator.antlr4.handlers.unary;

import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;
import org.javaculator.antlr4.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Handler for signed unary expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing signed unary expressions
 * represented by {@code CalcParser.SignedExprContext}. It applies a visitor function to evaluate the
 * contained unary expression and then applies the sign operator. If the operator is "-", the result is negated;
 * if it is "+", the result is returned unchanged.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.SignedExprContext
 * @see CalcParser.UnaryContext
 * @see BigDecimal
 * @see UnknownOperatorException
 */
public class SignedUnaryExprHandler implements IExprHandler<CalcParser.SignedExprContext, CalcParser.UnaryContext> {
    public static final SignedUnaryExprHandler INSTANCE = new SignedUnaryExprHandler();

    /**
     * Processes a signed unary expression by applying the visitor function to the contained unary context
     * and then applying the sign operator.
     * <p>
     * The method retrieves the operator from the context and uses a switch expression to determine whether to
     * negate the result (for "-") or leave it unchanged (for "+"). If the operator is not recognized, it throws an
     * {@link UnknownOperatorException}.
     * </p>
     *
     * @param ctx     the signed expression context containing the sign operator and the unary expression
     * @param visitor a function that evaluates the {@code CalcParser.UnaryContext} into a {@link BigDecimal}
     * @return an {@link BigDecimal} containing the evaluated result, or null if the result is null
     * @throws UnknownOperatorException if the sign operator is not "+" or "-"
     */
    @Override
    public BigDecimal handle(CalcParser.SignedExprContext ctx, Function<CalcParser.UnaryContext, BigDecimal> visitor) {
        BigDecimal current = visitor.apply(ctx.unary());
        String op = ctx.getChild(0).getText();

        return switch (op) {
            case "-" -> BigDecimalSupport.negate(current);
            case "+" -> current;
            default -> throw new UnknownOperatorException(op);
        };
    }
}

