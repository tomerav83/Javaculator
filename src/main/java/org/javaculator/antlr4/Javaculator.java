package org.javaculator.antlr4;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.javaculator.antlr4.cache.RollbackCache;
import org.javaculator.antlr4.exceptions.JavaculatorException;
import org.javaculator.antlr4.exceptions.impl.UnknownOperatorException;
import org.javaculator.antlr4.gen.CalcBaseVisitor;
import org.javaculator.antlr4.gen.CalcLexer;
import org.javaculator.antlr4.gen.CalcParser;
import org.javaculator.antlr4.handlers.additive.AdditiveExprHandler;
import org.javaculator.antlr4.handlers.assignment.AssignExprHandler;
import org.javaculator.antlr4.handlers.assignment.AugmentedAssignExprHandler;
import org.javaculator.antlr4.handlers.assignment.SingletonExprHandler;
import org.javaculator.antlr4.handlers.literals.FloatingPointHandler;
import org.javaculator.antlr4.handlers.literals.IdentifierHandler;
import org.javaculator.antlr4.handlers.literals.IntegerHandler;
import org.javaculator.antlr4.handlers.literals.LiteralExprHandler;
import org.javaculator.antlr4.handlers.multiplicative.MultiplicativeExprHandler;
import org.javaculator.antlr4.handlers.unary.ParenExprHandler;
import org.javaculator.antlr4.handlers.unary.PostIncDecHandler;
import org.javaculator.antlr4.handlers.unary.PreIncDecHandler;
import org.javaculator.antlr4.handlers.unary.SignedUnaryExprHandler;
import org.javaculator.terminal.TerminalLogger;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The {@code Javaculator} is an expression evaluator that visits parsed AST nodes
 * generated by ANTLR from arithmetic expressions and performs calculations.
 *
 * <p>This class supports various types of expressions including:
 * assignments, augmented assignments, unary and binary arithmetic operations,
 * pre/post increment and decrement, literals, and identifiers.</p>
 *
 * <p>It uses a {@link RollbackCache} to track variable states and supports
 * rollback in case of evaluation errors or exceptions. This enables transactional-style
 * evaluation with compensation/rollback behavior.</p>
 */
public class Javaculator extends CalcBaseVisitor<BigDecimal> {
    private static final BailErrorStrategy ERROR_STRATEGY = new BailErrorStrategy();
    private final RollbackCache rollbackCache = new RollbackCache();
    private CalcParser parser;

    /**
     * get cash
     * @return state of the current variables
     */
    public Map<String, BigDecimal> getCache() {
        return rollbackCache.get();
    }

    /**
     * clear cash
     */
    public void clear() {
        rollbackCache.clear();
    }

    public void prepareAndInvokeCalculation(String input) {
        CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
        parser = new CalcParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(ERROR_STRATEGY);
        calculate();
    }
    /**
     * Evaluates a calculation stage by visiting the parsed expression tree.
     * On successful evaluation, logs the updated variable state.
     * If an exception occurs, the {@link RollbackCache} is rolled back to
     * its previous state and the error is logged.
     *
     */
    private void calculate() {
        try {
            rollbackCache.takeSnapshot();
            visit(parser.calculate());
            TerminalLogger.info("After calculation: %s".formatted(rollbackCache));
        } catch (JavaculatorException e) {
            rollbackCache.rollback();
            TerminalLogger.error("Encountered exception: %s".formatted(e.getMessage()));
            TerminalLogger.warn("After calculation: %s".formatted(rollbackCache));
        } catch (ParseCancellationException e) {
            TerminalLogger.fatal("Encountered exception: %s".formatted(e.getMessage()));
            TerminalLogger.warn("After calculation: %s".formatted(rollbackCache));
        }
    }

    /**
     * Entry point for visiting expressions.
     * Throws if the expression is not an assignment or augmented assignment.
     */
    @Override
    public BigDecimal visitRoot(CalcParser.RootContext ctx) {
        if (!(ctx.expression() instanceof CalcParser.AssignExprContext) &&
                !(ctx.expression() instanceof CalcParser.AugmentedAssignExprContext)) {
            throw new UnknownOperatorException(ctx.expression().getText());
        }

        return visit(ctx.expression());
    }

    /**
     * Visits and evaluates an augmented assignment expression (e.g., x += 1).
     */
    @Override
    public BigDecimal visitAugmentedAssignExpr(CalcParser.AugmentedAssignExprContext ctx) {
        return AugmentedAssignExprHandler.INSTANCE.handle(ctx, rollbackCache, this::visit);
    }

    /**
     * Visits and evaluates a basic assignment expression (e.g., x = 5).
     */
    @Override
    public BigDecimal visitAssignExpr(CalcParser.AssignExprContext ctx) {
        return AssignExprHandler.INSTANCE.handle(ctx, rollbackCache, this::visit);
    }

    /**
     * Visits singleton expressions, such as a single literal or identifier.
     */
    @Override
    public BigDecimal visitSingletonExpr(CalcParser.SingletonExprContext ctx) {
        return SingletonExprHandler.INSTANCE.handle(ctx, this::visit);
    }

    /**
     * Visits and evaluates an addition or subtraction expression.
     */
    @Override
    public BigDecimal visitAddSubExpr(CalcParser.AddSubExprContext ctx) {
        return AdditiveExprHandler.INSTANCE.handle(ctx, this::visit);
    }

    /**
     * Visits and evaluates a multiplication, division, or modulo expression.
     */
    @Override
    public BigDecimal visitMulDivModExpr(CalcParser.MulDivModExprContext ctx) {
        return MultiplicativeExprHandler.INSTANCE.handle(ctx, this::visit);
    }

    /**
     * Visits and evaluates a signed unary expression (e.g., -x or +x).
     */
    @Override
    public BigDecimal visitSignedExpr(CalcParser.SignedExprContext ctx) {
        return SignedUnaryExprHandler.INSTANCE.handle(ctx, this::visit);
    }

    /**
     * Visits and evaluates a pre-increment or pre-decrement expression (e.g., ++x or --x).
     */
    @Override
    public BigDecimal visitPreIncDecExpr(CalcParser.PreIncDecExprContext ctx) {
        return PreIncDecHandler.INSTANCE.handle(ctx, rollbackCache);
    }

    /**
     * Visits and evaluates a post-increment or post-decrement expression (e.g., x++ or x--).
     */
    @Override
    public BigDecimal visitPostIncDecExpr(CalcParser.PostIncDecExprContext ctx) {
        return PostIncDecHandler.INSTANCE.handle(ctx, rollbackCache);
    }

    /**
     * Visits and evaluates a literal expression (number).
     */
    @Override
    public BigDecimal visitLiteralsExpr(CalcParser.LiteralsExprContext ctx) {
        return LiteralExprHandler.INSTANCE.handle(ctx, this::visit);
    }

    /**
     * Visits an identifier and returns its value from the rollback cache.
     */
    @Override
    public BigDecimal visitIdentifier(CalcParser.IdentifierContext ctx) {
        return IdentifierHandler.INSTANCE.handle(ctx, rollbackCache);
    }

    /**
     * Visits and evaluates a floating point number literal.
     */
    @Override
    public BigDecimal visitFloatingPoint(CalcParser.FloatingPointContext ctx) {
        return FloatingPointHandler.INSTANCE.handle(ctx);
    }

    /**
     * Visits and evaluates an integer number literal.
     */
    @Override
    public BigDecimal visitInteger(CalcParser.IntegerContext ctx) {
        return IntegerHandler.INSTANCE.handle(ctx);
    }

    /**
     * Visits and evaluates an expression within parentheses.
     */
    @Override
    public BigDecimal visitParenExpr(CalcParser.ParenExprContext ctx) {
        return ParenExprHandler.INSTANCE.handle(ctx, this::visit);
    }

    /**
     * Handles null expressions. Returns {@code null}.
     */
    @Override
    public BigDecimal visitNull(CalcParser.NullContext ctx) {
        return null;
    }
}
