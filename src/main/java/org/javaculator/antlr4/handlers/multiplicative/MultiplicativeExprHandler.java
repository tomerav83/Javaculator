package org.javaculator.antlr4.handlers.multiplicative;

import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;
import org.javaculator.antlr4.utils.BigDecimalSupport;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Handler for multiplicative expressions in the calculator's parse tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing expressions involving
 * multiplication, division, and modulus operations as represented by {@code CalcParser.MulDivModExprContext}.
 * It evaluates the expression by applying a visitor function to each {@code UnaryContext} operand and combining
 * the results according to the operator encountered.
 * </p>
 *
 * @see IExprHandler
 * @see CalcParser.MulDivModExprContext
 * @see CalcParser.UnaryContext
 * @see BigDecimal
 * @see UnknownOperatorException
 */
public class MultiplicativeExprHandler implements IExprHandler<CalcParser.MulDivModExprContext, CalcParser.UnaryContext> {
    public static final MultiplicativeExprHandler INSTANCE = new MultiplicativeExprHandler();

    /**
     * Processes a multiplicative expression by iterating over its unary subexpressions and
     * applying the appropriate arithmetic operation based on the operator token.
     * <p>
     * The method starts by evaluating the first unary expression as the left-hand side (lhs). It then
     * iterates over the remaining unary expressions, retrieves the corresponding operator from the parse tree,
     * and combines the current result with the next operand using multiplication, division, or modulus,
     * as indicated by the operator. If an unrecognized operator is encountered, an {@link UnknownOperatorException}
     * is thrown.
     * </p>
     *
     * @param ctx     the multiplicative expression context from the parse tree
     * @param visitor a function to evaluate each {@code UnaryContext} operand into a {@code BigDecimal}
     * @return an {@link BigDecimal} containing the evaluated result of the multiplicative expression,
     *         or null if the result is {@code null}
     * @throws UnknownOperatorException if an unsupported operator is encountered in the expression
     */
    @Override
    public BigDecimal handle(CalcParser.MulDivModExprContext ctx, Function<CalcParser.UnaryContext, BigDecimal> visitor) {
        BigDecimal lhs = visitor.apply(ctx.unary(0));

        for (int i = 1; i < ctx.unary().size(); i++) {
            BigDecimal rhs = visitor.apply(ctx.unary(i));
            // The operator is located at the odd positions in the parse tree children.
            String op = ParserCtxUtils.getChild(ctx, 2 * i - 1);

            lhs = switch (op) {
                case "*" -> BigDecimalSupport.multiply(lhs, rhs);
                case "/" -> BigDecimalSupport.div(lhs, rhs);
                case "^" -> BigDecimalSupport.pow(lhs, rhs);
                case "%" -> BigDecimalSupport.mod(lhs, rhs);
                default -> throw new UnknownOperatorException(op);
            };
        }

        return lhs;
    }
}

