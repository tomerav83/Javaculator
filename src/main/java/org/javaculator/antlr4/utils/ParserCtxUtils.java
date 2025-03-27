package org.javaculator.antlr4.utils;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;

public class ParserCtxUtils {
    public static CalcParser.ExpressionContext getCtx(CalcParser.ExpressionContext ctx, int index) {
        return ctx.getRuleContext(CalcParser.ExpressionContext.class, index);
    }

    public static TerminalNode getIdentifier(CalcParser.ExpressionContext ctx) {
        return ctx.getToken(CalcParser.ID, 0);
    }

    public static Integer getLiteral(CalcParser.ExpressionContext ctx) {
        return Integer.parseInt(ctx.getToken(CalcParser.INT, 0).getText());
    }
}
