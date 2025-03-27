package org.javaculator.antlr4.op.lr;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.function.Function;

public abstract class LRExprOp<CTX extends CalcParser.ExpressionContext> {
    protected final String op;
    protected final Integer left;
    protected final Integer right;

    public LRExprOp(CTX ctx, Token op, Function<ParseTree, Integer> visitor) {
        this.op = op.getText();
        this.left = visitor.apply(ParserCtxUtils.getCtx(ctx, 0));
        this.right = visitor.apply(ParserCtxUtils.getCtx(ctx, 1));
    }

    protected abstract Integer calculate();
}
