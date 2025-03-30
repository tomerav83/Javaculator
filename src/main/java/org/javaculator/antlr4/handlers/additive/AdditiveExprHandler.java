package org.javaculator.antlr4.handlers.additive;

import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.handlers.interfaces.IExprHandler;
import org.javaculator.antlr4.utils.BigDecimalSupport;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static org.javaculator.antlr4.gen.CalcParser.*;

/**
 * Handler for additive expressions (+/-) in the calculator's abstract syntax tree.
 * <p>
 * This class implements the {@link IExprHandler} interface for processing addition and subtraction
 * expressions represented by {@link AddSubExprContext}. It evaluates the expression by applying a visitor
 * function to each {@link  MultiplicativeContext} and combining the results based on the operators found.
 * </p>
 *
 * @see IExprHandler
 * @see AddSubExprContext
 * @see MultiplicativeContext
 */
public class AdditiveExprHandler implements IExprHandler<AddSubExprContext, MultiplicativeContext> {
    public static final AdditiveExprHandler INSTANCE = new AdditiveExprHandler();

    /**
     * Processes an additive expression by iterating over its multiplicative subexpressions and
     * applying addition or subtraction based on the operator.
     * <p>
     * The method applies the given visitor function to each {@link  MultiplicativeContext} in
     * the {@link AddSubExprContext}. The first multiplicative expression is used as the initial value,
     * and each subsequent value is combined with the current result using the operator located at the
     * corresponding position in the parse tree.
     * </p>
     *
     * @param ctx     the {@link AddSubExprContext} representing the additive expression from the parse tree
     * @param visitor a function that computes a {@link BigDecimal} from a {@link MultiplicativeContext}
     * @return an {@link BigDecimal} containing the computed result of the expression
     * @throws UnknownOperatorException if an operator other than '+' or '-' is encountered
     */
    @Override
    public BigDecimal handle(AddSubExprContext ctx, Function<MultiplicativeContext, BigDecimal> visitor) {
        BigDecimal lhs = visitor.apply(ctx.multiplicative(0));

        for (int i = 1; i < ctx.multiplicative().size(); i++) {
            BigDecimal rhs = visitor.apply(ctx.multiplicative(i));
            // Operator is at odd positions in the parse tree children
            String op = ParserCtxUtils.getChild(ctx, 2 * i - 1);

            lhs = switch (op) {
                case "+" -> BigDecimalSupport.add(lhs, rhs);
                case "-" -> BigDecimalSupport.sub(lhs, rhs);
                default -> throw new UnknownOperatorException(op);
            };
        }

        return lhs;
    }
}
