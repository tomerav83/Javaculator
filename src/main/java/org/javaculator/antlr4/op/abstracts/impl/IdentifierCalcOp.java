package org.javaculator.antlr4.op.abstracts.impl;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.abstracts.VisitorOp;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.Map;
import java.util.Optional;

public abstract class IdentifierCalcOp<CTX extends CalcParser.ExpressionContext> extends VisitorOp<CTX> {
    protected IdentifierCalcOp(CTX ctx) {
        super(ctx);
    }

    protected boolean isIdentifierMissingOrNull(Map<String, Integer> vars) {
        return Optional.ofNullable(vars.get(getIdentifier())).isEmpty();
    }

    protected String getIdentifier() {
        return ParserCtxUtils.getIdentifier(ctx).getText();
    }

    protected abstract Integer calculate(Map<String, Integer> vars);
}
