package org.javaculator.antlr4.op.unary;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.Map;
import java.util.Optional;

public abstract class UnaryExprOp<CTX extends CalcParser.ExpressionContext> {
    protected final String identifier;

    public UnaryExprOp(CTX ctx) {
        this.identifier = ParserCtxUtils.getIdentifier(ctx).getText();
    }

    protected boolean isIdentifierNotSetOrNull(Map<String, Integer> vars) {
        return Optional.ofNullable(vars.get(identifier)).isEmpty();
    }

    protected abstract Integer apply(Map<String, Integer> vars) throws CalculationException;
}
