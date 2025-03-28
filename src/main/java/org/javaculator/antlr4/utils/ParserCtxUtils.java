package org.javaculator.antlr4.utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.javaculator.antlr4.CalcParser;

public class ParserCtxUtils {
    public static <CTX extends CalcParser.ExpressionContext> CalcParser.ExpressionContext getCtx(CTX ctx, int index) {
        return ctx.getRuleContext(CalcParser.ExpressionContext.class, index);
    }

    public static <CTX extends ParserRuleContext> TerminalNode getIdentifier(CTX ctx) {
        return ctx.getToken(CalcParser.ID, 0);
    }

    public static <CTX extends ParserRuleContext> Integer getLiteral(CTX ctx) {
        return Integer.parseInt(ctx.getToken(CalcParser.INT, 0).getText());
    }
}
